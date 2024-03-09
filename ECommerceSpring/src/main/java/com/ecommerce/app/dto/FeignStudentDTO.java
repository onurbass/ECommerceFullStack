package com.ecommerce.app.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class FeignStudentDTO {

  private String name;
  private String surname;
  private String personalEmail;
  private String baEmail;
  private String baBoostEmail;
  private String phoneNumber;
  private String address;
  private String school;
  private String department;
  private LocalDate birthDate;
  private String birthPlace;
  private String courseName;
  private Long groupId;
  private Long branchId;
}
