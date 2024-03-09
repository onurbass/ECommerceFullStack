package com.ecommerce.util.common;

import com.ecommerce.app.dto.AutoCompleteDTO;
import com.ecommerce.util.PageDTO;
import com.ecommerce.util.dbUtil.BaseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public abstract class BaseCrudController<DTO extends BaseDTO, RequestDTO extends BaseDTO, Entity extends BaseEntity,
        Repository extends BaseRepository<Entity>, Mapper extends BaseMapper<DTO, RequestDTO, Entity>,
        Specifitation extends BaseSpecification<Entity>,
        Service extends BaseService<DTO, RequestDTO, Entity, Repository, Mapper, Specifitation>> {

    @Autowired
	HelperService helperService;

    protected abstract Service getService();

    protected abstract Mapper getMapper();

    protected void validate(BindingResult bindingResult) throws Exception {
        if (bindingResult.hasErrors()) {
            String errors = bindingResult.getAllErrors().stream().map(ObjectError::toString)
                    .collect(Collectors.joining(","));
            throw new Exception(errors);
        }
    }


    @PostMapping("get-all-by-filter")
    public ResponseEntity<PageDTO<DTO>> getAll(@RequestBody BaseFilterRequestDTO filter) {

        PageDTO<DTO> dtoList = getService().findAll(filter);

        return new ResponseEntity<>(dtoList, HttpStatus.OK);
    }




    @PostMapping("search-autocomplete-filter")
    public ResponseEntity<List<AutoCompleteDTO>> searchAutoCompleteByFilter(@RequestBody BaseFilterRequestDTO filter) {

        List<AutoCompleteDTO> dtoList = getService().autoComplete(filter);

        return new ResponseEntity<>(dtoList, HttpStatus.OK);
    }


    @GetMapping("/")
    public ResponseEntity<PageDTO<DTO>> getAll(@RequestParam int pageNumber,@RequestParam int pageSize) {

        PageDTO<DTO> dtoList = getService().findAll(pageNumber,pageSize);

        return new ResponseEntity<>(dtoList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DTO> getById(@PathVariable UUID id) {
        DTO optionalT = getService().findByUUID(id);
        if (optionalT == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(optionalT, HttpStatus.OK);
        }
    }

    @PostMapping("/")
    public ResponseEntity<DTO> save(@RequestBody RequestDTO body) {

        return new ResponseEntity<>(getService().save(body), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable UUID id) {
        DTO optional = getService().findByUUID(id);

        if (optional == null) {

            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        } else {
                   }


        getService().delete(optional.getUuid());
        return new ResponseEntity<>("Object with the id " + id + " was deleted.", HttpStatus.NO_CONTENT);


    }

    @PutMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable UUID id, @RequestBody RequestDTO body) {
        DTO updated = getService().update(id, body);

        if (updated == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND.getReasonPhrase(), HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>("Object with the id " + id + " was updated.", HttpStatus.OK);
        }

    }


}
