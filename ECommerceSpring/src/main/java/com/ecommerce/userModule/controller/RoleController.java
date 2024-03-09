package com.ecommerce.userModule.controller;

import com.ecommerce.userModule.mapper.RoleMapper;
import com.ecommerce.userModule.service.RoleService;
import com.ecommerce.util.common.BaseCrudController;
import com.ecommerce.userModule.dto.RoleDTO;
import com.ecommerce.userModule.dto.requestDTO.RoleRequestDTO;
import com.ecommerce.userModule.entity.Role;
import com.ecommerce.userModule.repository.RoleRepository;
import com.ecommerce.userModule.specification.RoleSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController

@RequestMapping("user-api/role")
public class RoleController extends BaseCrudController<RoleDTO, RoleRequestDTO, Role, RoleRepository, RoleMapper, RoleSpecification, RoleService> {

    @Autowired
    RoleService roleService;

    @Autowired
    RoleMapper roleMapper;


    @Override
    protected RoleService getService() {
        return roleService;
    }

    @Override
    protected RoleMapper getMapper() {
        return roleMapper;
    }
}
