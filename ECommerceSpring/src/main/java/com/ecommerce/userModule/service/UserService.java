package com.ecommerce.userModule.service;

import com.ecommerce.userModule.mapper.UserMapper;
import com.ecommerce.util.PageDTO;
import com.ecommerce.util.common.BaseService;
import com.ecommerce.userModule.dto.UserDTO;
import com.ecommerce.userModule.dto.requestDTO.UserRequestDTO;
import com.ecommerce.userModule.entity.Role;
import com.ecommerce.userModule.entity.User;
import com.ecommerce.userModule.repository.RoleRepository;
import com.ecommerce.userModule.repository.UserRepository;
import com.ecommerce.userModule.specification.UserSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService extends BaseService<UserDTO, UserRequestDTO, User, UserRepository, UserMapper, UserSpecification> {

    @Autowired
    UserSpecification userSpecification;

    @Autowired
    UserMapper userMapper;

    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    PasswordEncoder passwordEncoder;


    @Override
    public UserRepository getRepository() {
        return userRepository;
    }

    @Override
    public UserMapper getMapper() {
        return userMapper;
    }

    @Override
    public UserSpecification getSpecification() {
        return userSpecification;
    }

    public User getUserByEmail(String email) {
        Optional<User> userOptional = userRepository.findByEmail(email);

        if (userOptional.isPresent()) {
            return userOptional.get();
        } else {

            throw new NoSuchElementException("User not found for email: " + email);
        }
    }


    public UserRequestDTO saveUserByRole(UUID id, UserRequestDTO dto) {
        Optional<Role> role = roleRepository.findByUuid(id);
        if (role.isPresent()) {
            User user = getMapper().requestDTOToEntity(dto);
            user.setPassword(passwordEncoder.encode(dto.getPassword()));
            Set<Role> roles = new HashSet<>();
            roles.add(role.get());
            user.setRoles(roles);
            userRepository.save(user);

            return dto;
        } else {
            return null;
        }
    }

    public PageDTO<UserDTO> getUserByRole(UUID id, int page, int size) {
        Optional<Role> role = roleRepository.findByUuid(id);
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Order.desc("id")));
        if (role.isPresent()) {
            Set<Role> roleSet = new HashSet<>();
            roleSet.add(role.get());

            Page<User> pageEntity = userRepository.getUserByRole(pageable,id);
            PageDTO<UserDTO> pageDTO = new PageDTO<>();
            pageDTO.setNumber(pageEntity.getNumber());
            pageDTO.setSize(pageEntity.getSize());
            pageDTO.setTotalPage(pageEntity.getTotalPages());
            pageDTO.setSort(pageEntity.getSort());
            pageDTO.setData(pageEntity.toList().stream().map(getMapper()::entityToDTO).collect(Collectors.toList()));






            return pageDTO;
        } else {
            return null;
        }
    }
    public UserRequestDTO saveUserByUser(UUID id, UserRequestDTO dto) {
        Optional<User> user = userRepository.findByUuid(id);
        if (user.isPresent()) {
            User user1 = getMapper().requestDTOToEntity(dto);
            userRepository.save(user1);
            return dto;
        } else {
            return null;
        }
    }


}
