package com.ecommerce.userModule.model.requestModel.auth;

import lombok.Data;

@Data
public class RegisterRequest {

    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String confirmPassword;




}
