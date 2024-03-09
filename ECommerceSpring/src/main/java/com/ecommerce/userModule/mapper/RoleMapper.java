package com.ecommerce.userModule.mapper;

import com.ecommerce.app.dto.AutoCompleteDTO;
import com.ecommerce.util.common.BaseMapper;
import com.ecommerce.userModule.dto.RoleDTO;
import com.ecommerce.userModule.dto.requestDTO.RoleRequestDTO;
import com.ecommerce.userModule.entity.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface RoleMapper extends BaseMapper<RoleDTO, RoleRequestDTO, Role> {
    @Mappings({@Mapping(source = "uuid", target = "value"),
            @Mapping(source = "name", target = "label")

    })
    AutoCompleteDTO entityToAutoCompleteDTO(Role entity);

}
