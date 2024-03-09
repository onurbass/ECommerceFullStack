package com.ecommerce.userModule.dto.requestDTO;

import com.ecommerce.util.common.BaseDTO;
import lombok.Data;

@Data
public class UserRequestDTO extends BaseDTO {

    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String color;
}
