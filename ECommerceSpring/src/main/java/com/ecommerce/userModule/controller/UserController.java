package com.ecommerce.userModule.controller;

import com.ecommerce.userModule.mapper.UserMapper;
import com.ecommerce.userModule.service.RoleService;
import com.ecommerce.userModule.service.UserService;
import com.ecommerce.util.PageDTO;
import com.ecommerce.util.common.BaseCrudController;
import com.ecommerce.util.common.HelperService;
import com.ecommerce.userModule.dto.UserDTO;
import com.ecommerce.userModule.dto.requestDTO.UserRequestDTO;
import com.ecommerce.userModule.entity.User;
import com.ecommerce.userModule.repository.UserRepository;
import com.ecommerce.userModule.specification.UserSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("user-api")
public class UserController extends BaseCrudController<UserDTO, UserRequestDTO, User, UserRepository, UserMapper, UserSpecification, UserService> {

    @Autowired
    UserService userService;

    @Autowired
    UserMapper userMapper;
    @Autowired
    RoleService roleService;

    @Autowired
    HelperService helperService;

    @Override
    protected UserService getService() {
        return userService;
    }

    @Override
    protected UserMapper getMapper() {
        return userMapper;
    }

    @GetMapping("/get-by-email/{email}")
    public ResponseEntity<UserDTO> getByEmail(@PathVariable String email){
        return new ResponseEntity<>(
                getMapper().entityToDTO(userService.getUserByEmail(email)),
                HttpStatus.OK);
    }
    @GetMapping("/get-color-by-email/{email}")
    public ResponseEntity<String> getUserColorByEmail(@PathVariable String email){
        return new ResponseEntity<>(
                userService.getUserByEmail(email).getColor(),
                HttpStatus.OK);
    }

    @PostMapping("/{roleId}")
    public ResponseEntity<UserRequestDTO> saveByRole(@RequestBody UserRequestDTO userRequestDTO, @PathVariable UUID roleId) {
        return new ResponseEntity<>(userService.saveUserByRole(roleId, userRequestDTO), HttpStatus.OK);
    }


    @GetMapping("get-all-by-role/{roleId}")
    public ResponseEntity<PageDTO<UserDTO>> getByRoleId(
            @PathVariable UUID roleId, @RequestParam int pageNumber, @RequestParam int pageSize) {
        PageDTO<UserDTO> pageDTO = userService.getUserByRole(roleId, pageNumber, pageSize);

        return new ResponseEntity<>(pageDTO, HttpStatus.OK);
    }

    @PostMapping("/user-api")
    public ResponseEntity<UserRequestDTO> saveByUser(@RequestHeader(value = "accept-language", required = true) String acceptLanguage,
                                                     @RequestBody UserRequestDTO userRequestDTO) {
        User user = helperService.getUserFromSession();
        if (user != null) {
            {

                return new ResponseEntity<>(userService.saveUserByUser(user.getUuid(), userRequestDTO), HttpStatus.OK);
            }
        } else return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }


}
