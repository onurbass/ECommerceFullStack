package com.ecommerce.userModule.dto;

import com.ecommerce.util.common.BaseDTO;
import lombok.Data;

@Data
public class UserDTO extends BaseDTO {

    private String firstName;
    private String lastName;
    private String email;
    private String color;

}
