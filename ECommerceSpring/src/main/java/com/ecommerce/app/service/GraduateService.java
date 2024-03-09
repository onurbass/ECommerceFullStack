package com.ecommerce.app.service;



import com.ecommerce.app.dto.GraduateDTO;
import com.ecommerce.app.dto.requestDTO.GraduateRequestDTO;
import com.ecommerce.app.entity.Graduate;
import com.ecommerce.app.mapper.GraduateMapper;
import com.ecommerce.app.repository.GraduateRepository;
import com.ecommerce.app.specification.GraduateSpecification;
import com.ecommerce.util.common.BaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class GraduateService extends BaseService<GraduateDTO, GraduateRequestDTO, Graduate, GraduateRepository, GraduateMapper, GraduateSpecification> {


  private final GraduateRepository repository;
  private final GraduateMapper mapper;
  private final GraduateSpecification specification;


  @Override
  public GraduateRepository getRepository() {
	return repository;
  }

  @Override
  public GraduateMapper getMapper() {
	return mapper;
  }

  @Override
  public GraduateSpecification getSpecification() {
	return specification;
  }



    public Page<Graduate> getAllByPagination(int pageNumber,int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return repository.findAll(pageable);
    }

  public void uploadProfilePic(MultipartFile file, UUID id) throws IOException {

      Graduate graduate= findEntityById(id);

      if (graduate!=null){
          graduate.setProfilePic(file.getBytes());
          repository.save(graduate);

      }
  }

    public Graduate saveGraduate(Graduate graduate) {

        return repository.save(graduate);
    }

    public Graduate updateGraduate(Long id,Graduate updatedData) {

        Graduate existingGraduate = repository.findById(id).orElseThrow(() -> new RuntimeException("Mezun bulunamadı"));

        existingGraduate.setName(updatedData.getName());
        existingGraduate.setSurname(updatedData.getSurname());
        existingGraduate.setEmail(updatedData.getEmail());
        existingGraduate.setBranch(updatedData.getBranch());
        existingGraduate.setCourse(updatedData.getCourse());
        existingGraduate.setGroupName(updatedData.getGroupName());
        existingGraduate.setIban(updatedData.getIban());
        existingGraduate.setNote(updatedData.getNote());

        return repository.save(existingGraduate);
    }
}
