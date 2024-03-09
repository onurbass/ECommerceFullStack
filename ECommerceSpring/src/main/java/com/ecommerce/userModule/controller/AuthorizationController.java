package com.ecommerce.userModule.controller;



import com.ecommerce.userModule.service.UserService;
import com.ecommerce.userModule.service.interfaces.IAuthService;
import com.ecommerce.util.common.HelperService;
import com.ecommerce.util.security.JWTUtil;
import com.ecommerce.userModule.entity.Role;
import com.ecommerce.userModule.entity.User;
import com.ecommerce.userModule.model.requestModel.auth.LoginRequest;
import com.ecommerce.userModule.model.requestModel.auth.RegisterRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@RequestMapping("auth")
public class AuthorizationController {

    @Autowired
    IAuthService authService;


    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private JWTUtil jwtUtil;

    @Autowired
    HelperService helperService;

    @Autowired
    UserService userService;



    @PostMapping("/register")
    public ResponseEntity<String> registerHandler(@RequestBody RegisterRequest user) {


        authService.registerUser(user);

        return new ResponseEntity<String>("Kullanıcı Eklendi", HttpStatus.OK);

    }



    private boolean isValidLoginDomain(String email, String[] allowedDomains) {
        String userDomain = email.substring(email.lastIndexOf("@") + 1);
        return Arrays.asList(allowedDomains).contains(userDomain);
    }
}
