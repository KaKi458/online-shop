package com.onlineshop.service;

import com.onlineshop.dto.CustomerDto;
import com.onlineshop.exception.ApiException;
import com.onlineshop.model.Customer;
import com.onlineshop.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerDto addCustomer(CustomerDto customerDto) {
        if (customerRepository.existsByEmail(customerDto.getEmail())) {
            throw new ApiException(
                    HttpStatus.CONFLICT, "Customer with email <" + customerDto.getEmail() + "> already exists."
            );
        }
        Customer customer = mapToCustomer(customerDto);
        Customer createdCustomer = customerRepository.save(customer);
        return mapToCustomerDto(createdCustomer);
    }

    public CustomerDto updateCustomer(Long customerId, CustomerDto customerDto) {
        Customer customer = findCustomer(customerId);
        customer.setEmail(customerDto.getEmail());
        customer.setLastName(customerDto.getLastName());
        Customer updatedCustomer = customerRepository.save(customer);
        return mapToCustomerDto(updatedCustomer);
    }

    public CustomerDto getCustomer(Long customerId) {
        Customer customer = findCustomer(customerId);
        return mapToCustomerDto(customer);
    }

    public void deleteCustomer(Long customerId) {
        Customer customer = findCustomer(customerId);
        if (!customer.getOrders().isEmpty()) {
            throw new ApiException(
                    HttpStatus.FORBIDDEN, "Customer with id <" + customerId + "> cannot be deleted."
            );
        }
        customerRepository.delete(customer);
    }

    private Customer mapToCustomer(CustomerDto dto) {
        return Customer.builder()
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .email(dto.getEmail())
                .build();
    }

    private CustomerDto mapToCustomerDto(Customer customer) {
        return CustomerDto.builder()
                .customerId(customer.getId())
                .firstName(customer.getFirstName())
                .lastName(customer.getLastName())
                .email(customer.getEmail())
                .build();
    }

    private Customer findCustomer(Long customerId) {
        return customerRepository.findById(customerId)
                .orElseThrow(() -> new ApiException(
                        HttpStatus.NOT_FOUND, "Customer with id <" + customerId + "> does not exist."
                ));
    }
}
