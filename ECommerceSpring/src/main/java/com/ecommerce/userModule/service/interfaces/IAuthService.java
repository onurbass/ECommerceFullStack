package com.ecommerce.userModule.service.interfaces;


import com.ecommerce.userModule.model.requestModel.auth.RegisterRequest;
import org.springframework.stereotype.Service;

@Service
public interface IAuthService {


    boolean registerUser(RegisterRequest request);

}
