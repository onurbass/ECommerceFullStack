package com.ecommerce.userModule.mapper;

import com.ecommerce.app.dto.AutoCompleteDTO;
import com.ecommerce.util.common.BaseMapper;
import com.ecommerce.userModule.dto.UserDTO;
import com.ecommerce.userModule.dto.requestDTO.UserRequestDTO;
import com.ecommerce.userModule.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface UserMapper extends BaseMapper<UserDTO, UserRequestDTO, User> {


    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "uuid", ignore = true),
            @Mapping(target = "deleted", ignore = true),
            @Mapping(target = "creationDate", ignore = true),
            @Mapping(target = "updatedDate", ignore = true),
            @Mapping(target = "createdBy", ignore = true),
            @Mapping(target = "lastModifiedBy", ignore = true)
    })
    @Override
    void update(@MappingTarget User entity,User updateEntity);

    @Mappings({@Mapping(source = "uuid", target = "value"),
            @Mapping(source = "email", target = "label")

    })
    AutoCompleteDTO entityToAutoCompleteDTO(User entity);
}
