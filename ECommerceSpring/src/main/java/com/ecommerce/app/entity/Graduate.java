package com.ecommerce.app.entity;


import com.ecommerce.app.entity.enums.EGraduateStatus;
import com.ecommerce.util.dbUtil.BaseEntity;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Data;


import java.time.LocalDate;

@Entity
@Data
@AttributeOverride(
		name = "uuid",
		column = @Column(
				name = "graduate_uuid"
		)
)
public class Graduate extends BaseEntity {

  @Column(nullable = false)
  private String name;

  @Column(nullable = false)
  private String surname;

  @Column(nullable = false)
  private String email;

  private String branch;

  @Column(nullable = false)
  private String course;

  private String groupName;

  private LocalDate startingDate;

  private String iban;

  @Column
  private String note;

  @Column
  private EGraduateStatus graduateStatus;

  @Column(columnDefinition = "bytea")
  private byte[] profilePic;
}
