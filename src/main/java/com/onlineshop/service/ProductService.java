package com.onlineshop.service;

import com.onlineshop.dto.ProductDto;
import com.onlineshop.exception.ApiException;
import com.onlineshop.model.Category;
import com.onlineshop.model.Product;
import com.onlineshop.repository.CategoryRepository;
import com.onlineshop.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public ProductDto addProduct(ProductDto productDto) {
        Product product = mapToProduct(productDto);
        Product createdProduct = productRepository.save(product);
        return mapToProductDto(createdProduct);
    }

    public ProductDto updateProduct(long productId, ProductDto productDto) {
        Product product = findProduct(productId);
        product.setName(productDto.getName());
        product.setCategory(findCategory(productDto.getCategoryId()));
        product.setPrice(productDto.getPrice());
        product.setQuantity(productDto.getQuantity());
        Product updatedProduct = productRepository.save(product);
        return mapToProductDto(updatedProduct);
    }

    public List<ProductDto> getProducts(Long categoryId, int pageNo, int pageSize, String sortBy, String sortDir) {
        Page<Product> productPage;
        Sort sort = Sort.by(sortBy);
        sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())
                ? sort.ascending() : sort.descending();
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        if (categoryId != null) {
            productPage = productRepository.findByCategoryId(categoryId, pageable);
        } else {
            productPage = productRepository.findAll(pageable);
        }
        List<Product> products = productPage.getContent();
        return products.stream().map(this::mapToProductDto).toList();

    }

    public ProductDto getProduct(long productId) {
        Product product = findProduct(productId);
        return mapToProductDto(product);
    }

    public void deleteProduct(long productId) {
        Product product = findProduct(productId);
        productRepository.delete(product);
    }

    private Product mapToProduct(ProductDto dto) {
        return Product.builder()
                .category(findCategory(dto.getCategoryId()))
                .name(dto.getName())
                .price(dto.getPrice())
                .quantity(dto.getQuantity())
                .build();
    }

    private ProductDto mapToProductDto(Product product) {
        return ProductDto.builder()
                .productId(product.getId())
                .name(product.getName())
                .categoryId(product.getCategory().getId())
                .price(product.getPrice())
                .quantity(product.getQuantity())
                .build();
    }

    private Product findProduct(long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new ApiException(
                        HttpStatus.NOT_FOUND, "Product with id <" + productId + "> does not exist."
                ));
    }

    private Category findCategory(long categoryId) {
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ApiException(
                        HttpStatus.NOT_FOUND, "Category with id <" + categoryId + "> does not exist."
                ));
    }

}
