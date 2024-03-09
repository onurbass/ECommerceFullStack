package com.ecommerce.userModule.service;

import com.ecommerce.userModule.service.interfaces.IAuthService;
import com.ecommerce.userModule.entity.Role;
import com.ecommerce.userModule.entity.User;
import com.ecommerce.userModule.model.requestModel.auth.RegisterRequest;
import com.ecommerce.userModule.repository.RoleRepository;
import com.ecommerce.userModule.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AuthService implements IAuthService {

  @Autowired
  UserRepository userRepository;

  @Autowired
  RoleRepository roleRepository;

  @Autowired
  PasswordEncoder passwordEncoder;

  @Override
  public boolean registerUser(RegisterRequest request) {

	Optional<User> user = userRepository.findByEmail(request.getEmail());

	//Gender gender = genderService.findEntityByUUID(request.getGenderUUID());

	if (user.isEmpty()) {
	  User newUser = new User();
	  newUser.setEmail(request.getEmail());
	  newUser.setPassword(passwordEncoder.encode(request.getPassword()));
	  newUser.setFirstName(request.getFirstName());
	  newUser.setLastName(request.getLastName());

	  Set<Role> roles = new HashSet<>();
	  roles.add(roleRepository.findByName("user").get());
	  newUser.setRoles(roles);

	  userRepository.save(newUser);

	  return true;
	} else {

	  throw new ResourceNotFoundException("email is exist = " + request.getEmail());
	}
  }
}
