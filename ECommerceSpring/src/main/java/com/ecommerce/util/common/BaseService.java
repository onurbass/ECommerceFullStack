package com.ecommerce.util.common;

import com.ecommerce.app.dto.AutoCompleteDTO;
import com.ecommerce.util.PageDTO;
import com.ecommerce.util.common.*;
import com.ecommerce.util.common.BaseMapper;
import com.ecommerce.util.common.BaseRepository;
import com.ecommerce.util.common.BaseSpecification;
import com.ecommerce.util.dbUtil.BaseEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

public abstract class BaseService<DTO extends BaseDTO, RequestDTO extends BaseDTO, Entity extends BaseEntity, Repository extends BaseRepository<Entity>, Mapper extends BaseMapper<DTO, RequestDTO, Entity>, Specification extends BaseSpecification<Entity>> {


    public abstract Repository getRepository();

    public abstract Mapper getMapper();

    public abstract Specification getSpecification();


    public PageDTO<DTO> findAll(BaseFilterRequestDTO filterRequestDTO) {
        Pageable pageable = PageRequest.of(filterRequestDTO.getPageNumber(), filterRequestDTO.getPageSize(), Sort.by("id").descending());

        getSpecification().setCriterias(filterRequestDTO.getFilters());
        //return getMapper().pageEntityToPageDTO(getRepository().findAllByLanguageEnum(pageable,languageEnum));

        Page<Entity> pageEntity = getRepository().findAll(getSpecification(), pageable);
        PageDTO<DTO> pageDTO = new PageDTO<>();
        pageDTO.setNumber(pageEntity.getNumber());
        pageDTO.setSize(pageEntity.getSize());
        pageDTO.setTotalPage(pageEntity.getTotalPages());
        pageDTO.setSort(pageEntity.getSort());
        pageDTO.setData(pageEntity.toList().stream().map(getMapper()::entityToDTO).collect(Collectors.toList()));
        pageDTO.setTotalElements(pageEntity.getTotalElements());

        return pageDTO;
    }

    public List<AutoCompleteDTO> autoComplete(BaseFilterRequestDTO filterRequestDTO) {
        AutoCompleteDTO autoCompleteDTO = new AutoCompleteDTO();
        Pageable pageable = PageRequest.of(filterRequestDTO.getPageNumber(), filterRequestDTO.getPageSize(), Sort.by("id").descending());

        getSpecification().setCriterias(filterRequestDTO.getFilters());
        //return getMapper().pageEntityToPageDTO(getRepository().findAllByLanguageEnum(pageable,languageEnum));

        Page<Entity> pageEntity = getRepository().findAll(getSpecification(), pageable);
        PageDTO<DTO> pageDTO = new PageDTO<>();
        pageDTO.setNumber(pageEntity.getNumber());
        pageDTO.setSize(pageEntity.getSize());
        pageDTO.setTotalPage(pageEntity.getTotalPages());
        pageDTO.setSort(pageEntity.getSort());
        pageDTO.setTotalElements(pageEntity.getTotalElements());
        //  pageDTO.setData(pageEntity.toList().stream().map(getMapper()::entityToDTO).collect(Collectors.toList()));

        List<AutoCompleteDTO> autoCompleteDTOList = new ArrayList<>();
        autoCompleteDTOList = pageEntity.toList().stream().map(getMapper()::entityToAutoCompleteDTO).collect(Collectors.toList());


        return autoCompleteDTOList;
    }


    public PageDTO<DTO> findAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        Page<Entity> pageEntity = getRepository().findAll(pageable);
        PageDTO<DTO> pageDTO = new PageDTO<>();
        pageDTO.setNumber(pageEntity.getNumber());
        pageDTO.setSize(pageEntity.getSize());
        pageDTO.setTotalPage(pageEntity.getTotalPages());
        pageDTO.setSort(pageEntity.getSort());
        pageDTO.setData(pageEntity.toList().stream().map(getMapper()::entityToDTO).collect(Collectors.toList()));
        pageDTO.setTotalElements(pageEntity.getTotalElements());
        return pageDTO;
    }

    public DTO findByUUID(UUID uuid) {

        Optional<Entity> optionalEntity = getRepository().findByUuid(uuid);
        return optionalEntity.map(entity -> getMapper().entityToDTO(entity)).orElse(null);

    }

    public Entity findEntityByUUID(UUID uuid) {
        Optional<Entity> optionalEntity = getRepository().findByUuid(uuid);
        return optionalEntity.orElse(null);
    }

    public Entity findEntityById(UUID id) {
        Optional<Entity> optionalEntity = getRepository().findByUuid(id);
        return optionalEntity.orElse(null);
    }

    public DTO save(RequestDTO requestDTO) {
        Entity entity = getMapper().dtoToEntity(requestDTO);
        getRepository().save(entity);
        return getMapper().entityToDTO(entity);
    }

    public void delete(UUID uuid) {
        Optional<Entity> optionalEntity = getRepository().findByUuid(uuid);
        if (optionalEntity.isPresent()) {
            Entity e = optionalEntity.get();
            //e.setDeleted(true);
            /*Entity e = optionalEntity.get();
            e.setDeleted(true);
            getRepository().save(e);*/
            //getRepository().save(e);
            getRepository().delete(e);
        }

    }

    public DTO update(UUID id, RequestDTO dto) {
        Optional<Entity> entity = getRepository().findByUuid(id);

        if (entity.isPresent()) {
            Entity entity1 = getMapper().dtoToEntity(dto);
            getMapper().update(entity.get(), entity1);
            getRepository().save(entity.get());
            return getMapper().entityToDTO(entity1);
        } else {
            return null;
        }
    }

}
