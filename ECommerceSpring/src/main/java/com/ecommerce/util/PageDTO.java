package com.ecommerce.util;

import com.ecommerce.util.common.BaseDTO;
import lombok.Data;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

@Data
public class PageDTO<DTO extends BaseDTO>{
    private int number; //page number
    private int size; // sayfadaki kayıt sayısı
    private Sort sort;
    private int totalPage; //toplam sayfa
    private Long totalElements; //toplam kayıt sayısı
    private List<DTO> data;

    public PageDTO(List<DTO> content, Pageable pageable, long totalElements) {
    }

    public PageDTO() {
    }

    public void setStart(Page page, List<DTO> list){
        this.number=page.getNumber();
        this.size=page.getSize();
        this.sort=page.getSort();
        this.totalPage=page.getTotalPages();
        this.totalElements=page.getTotalElements();
        this.data=list;


    }
}
