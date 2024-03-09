package com.ecommerce.app.controller;

import com.ecommerce.app.dto.GraduateDTO;
import com.ecommerce.app.dto.requestDTO.GraduateRequestDTO;

import com.ecommerce.app.entity.Graduate;
import com.ecommerce.app.mapper.GraduateMapper;
import com.ecommerce.app.repository.GraduateRepository;
import com.ecommerce.app.service.GraduateService;
import com.ecommerce.app.specification.GraduateSpecification;
import com.ecommerce.util.common.BaseCrudController;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("graduate")
@RequiredArgsConstructor
public class GraduateController extends BaseCrudController<
        GraduateDTO,
        GraduateRequestDTO,
        Graduate,
        GraduateRepository,
        GraduateMapper,
        GraduateSpecification,
        GraduateService
        >{

    private final GraduateService service;
    private final GraduateMapper mapper;


    @Override
    protected GraduateService getService() {
        return service;
    }

    @Override
    protected GraduateMapper getMapper() {
        return mapper;
    }
    @Override
    @PostMapping("/")
    public ResponseEntity<GraduateDTO> save(@RequestBody GraduateRequestDTO body) {

        return super.save(body);
    }
    @GetMapping
    public ResponseEntity<Page<Graduate>> getAllByPagination(
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "10") int pageSize
    ) {
        Page<Graduate> graduates = service.getAllByPagination(pageNumber, pageSize);
        return ResponseEntity.ok(graduates);
    }



    @PutMapping("/upload-profile-pic/{id}")
    public ResponseEntity<Void> uploadProfilePic(@RequestBody MultipartFile file, @PathVariable UUID id) throws IOException {
        service.uploadProfilePic(file,id);
        return ResponseEntity.ok().build();
    }

    @PostMapping
    public ResponseEntity<Graduate> saveGraduate(@RequestBody Graduate graduate) {
        Graduate savedGraduate = service.saveGraduate(graduate);
        return new ResponseEntity<>(savedGraduate, HttpStatus.CREATED);
    }

    @PutMapping("/{id}/update")
    public ResponseEntity<Graduate> updateGraduate(@PathVariable Long id, @RequestBody Graduate updatedData) {
        Graduate updatedGraduate = service.updateGraduate(id, updatedData);
        return new ResponseEntity<Graduate>(updatedGraduate, HttpStatus.OK);
    }
}
