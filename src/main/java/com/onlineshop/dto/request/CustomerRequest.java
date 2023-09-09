package com.onlineshop.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerRequest {

    private String firstName;
    private String lastName;
    private String email;
    private String password;
}
