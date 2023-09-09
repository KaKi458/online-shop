package com.onlineshop.service;

import com.onlineshop.dto.request.CategoryRequest;
import com.onlineshop.dto.response.CategoryDto;
import com.onlineshop.exception.ApiException;
import com.onlineshop.model.Category;
import com.onlineshop.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryDto addCategory(CategoryRequest categoryRequest) {
        Category category = mapToCategory(categoryRequest);
        Category createdCategory = categoryRepository.save(category);
        return mapToCategoryDto(createdCategory);
    }

    public CategoryDto updateCategory(Long categoryId, CategoryRequest categoryRequest) {
        Category category = findCategory(categoryId);
        category.setName(categoryRequest.getName());
        Category updatedCategory = categoryRepository.save(category);
        return mapToCategoryDto(updatedCategory);
    }

    public List<CategoryDto> getCategories() {
        List<Category> categories = categoryRepository.findAll(Sort.by("name"));
        return categories.stream().map(this::mapToCategoryDto).toList();
    }

    public void deleteCategory(Long categoryId) {
        Category category = findCategory(categoryId);
        if (!category.getProducts().isEmpty()) {
            throw new ApiException(
                    HttpStatus.FORBIDDEN,
                    "Category with id <" + categoryId + "> cannot be deleted. It contains some products."
            );
        }
        categoryRepository.delete(category);
    }

    private Category mapToCategory(CategoryRequest dto) {
        Category category = new Category();
        category.setName(dto.getName());
        return category;
    }

    private CategoryDto mapToCategoryDto(Category category) {
        return new CategoryDto(category.getId(), category.getName());
    }


    private Category findCategory(Long categoryId) {
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ApiException(
                        HttpStatus.NOT_FOUND, "Category with id <" + categoryId + "> does not exist."
                ));
    }

}
