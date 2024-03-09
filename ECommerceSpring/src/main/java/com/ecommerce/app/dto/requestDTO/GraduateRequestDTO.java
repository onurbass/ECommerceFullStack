package com.ecommerce.app.dto.requestDTO;

import com.ecommerce.app.entity.enums.EGraduateStatus;
import com.ecommerce.util.common.BaseDTO;
import lombok.Data;

import java.time.LocalDate;

@Data
public class GraduateRequestDTO extends BaseDTO {
    private String name;

    private String surname;

    private String email;

    private String branch;

    private String course;

    private String groupName;

    private LocalDate startingDate;

    private String iban;

    private String note;

    private EGraduateStatus graduateStatus;
}
