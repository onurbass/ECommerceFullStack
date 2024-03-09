package com.ecommerce.app.mapper;

import com.ecommerce.app.dto.AutoCompleteDTO;
import com.ecommerce.app.dto.FeignStudentDTO;
import com.ecommerce.app.dto.GraduateDTO;
import com.ecommerce.app.dto.requestDTO.GraduateRequestDTO;
import com.ecommerce.app.entity.Graduate;

import com.ecommerce.util.PageDTO;
import com.ecommerce.util.common.BaseMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Mapper(componentModel = "spring")
public interface GraduateMapper extends BaseMapper<GraduateDTO, GraduateRequestDTO, Graduate> {

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
  void update(@MappingTarget Graduate entity,Graduate updateEntity);

    @Mappings({
            @Mapping(source = "profilePic", target = "profilePic")
    })
    @Override
    GraduateDTO entityToDTO(Graduate entity);

    @Mappings({@Mapping(source = "uuid", target = "value"),
		  @Mapping(source = "name", target = "label")
  })
  AutoCompleteDTO entityToAutoCompleteDTO(Graduate entity);

  @Mappings({

		  @Mapping(source = "name", target = "name"),
		  @Mapping(source = "surname", target = "surname"),
		  @Mapping(source = "personalEmail", target = "email"),
		  @Mapping(source = "courseName", target = "course"),
  })
  Graduate toGraduate(FeignStudentDTO studentDTO);

  List<GraduateDTO> toGraduateDTOList(List<Graduate> graduates);

    default PageDTO<GraduateDTO> pageToPageDTO(Page<Graduate> page) {
        List<GraduateDTO> content = toGraduateDTOList(page.getContent());
        Pageable pageable = PageRequest.of(page.getNumber(), page.getSize());
        return new PageDTO<>(content, pageable, page.getTotalElements());
    }
}

