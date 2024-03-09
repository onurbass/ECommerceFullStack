package com.ecommerce.util.common;

import com.ecommerce.app.dto.AutoCompleteDTO;
import com.ecommerce.util.dbUtil.BaseEntity;
import org.mapstruct.MappingTarget;

import java.util.List;

public interface BaseMapper<DTO extends BaseDTO, RequestDTO extends BaseDTO, Entity extends BaseEntity> {

    DTO entityToDTO(Entity entity);

    Entity entitytoEntity(Entity entity);

    Entity dtoToEntity(RequestDTO dto);

    Entity requestDTOToEntity(RequestDTO dto);

    //Entity requestDTOToExistEntity(Entity entity, RequestDTO dto);*/

    List<Entity> dtoListToEntityList(List<DTO> dtoList);

    List<DTO> entityListToDTOList(List<Entity> entityList);


    void update(@MappingTarget Entity entity, Entity updateEntity);



    AutoCompleteDTO entityToAutoCompleteDTO(Entity entity);

    //PageDTO<DTO> pageEntityToPageDTO(Page<Entity> pageEntity);


}
