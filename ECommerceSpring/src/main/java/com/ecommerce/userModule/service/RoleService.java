package com.ecommerce.userModule.service;

import com.ecommerce.util.common.BaseService;
import com.ecommerce.userModule.dto.RoleDTO;
import com.ecommerce.userModule.dto.requestDTO.RoleRequestDTO;
import com.ecommerce.userModule.entity.Role;
import com.ecommerce.userModule.mapper.RoleMapper;
import com.ecommerce.userModule.repository.RoleRepository;
import com.ecommerce.userModule.specification.RoleSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService extends BaseService<RoleDTO, RoleRequestDTO, Role, RoleRepository, RoleMapper, RoleSpecification> {


    @Autowired
    RoleRepository repository;

    @Autowired
    RoleMapper roleMapper;

    @Autowired
    RoleSpecification roleSpecification;

    @Override
    public RoleRepository getRepository() {
        return repository;
    }

    @Override
    public RoleMapper getMapper() {
        return roleMapper;
    }

    @Override
    public RoleSpecification getSpecification() {
        return roleSpecification;
    }


}
