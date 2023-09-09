package com.onlineshop.service;

import com.onlineshop.dto.request.CustomerRequest;
import com.onlineshop.dto.response.CustomerDto;
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

    public CustomerDto addCustomer(CustomerRequest customerRequest) {
        if (customerRepository.existsByEmail(customerRequest.getEmail())) {
            throw new ApiException(
                    HttpStatus.CONFLICT, "Customer with email <" + customerRequest.getEmail() + "> already exists."
            );
        }
        Customer customer = mapToCustomer(customerRequest);
        Customer createdCustomer = customerRepository.save(customer);
        return mapToCustomerDto(createdCustomer);
    }

    public CustomerDto updateCustomer(Long customerId, CustomerRequest customerRequest) {
        Customer customer = findCustomer(customerId);
        customer.setEmail(customerRequest.getEmail());
        customer.setLastName(customerRequest.getLastName());
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

    private Customer mapToCustomer(CustomerRequest dto) {
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
