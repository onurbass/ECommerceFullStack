package com.ecommerce.userModule.entity;


import com.ecommerce.userModule.entity.User;
import com.ecommerce.util.dbUtil.BaseEntity;
import lombok.Data;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Data
@Table
public class Role extends BaseEntity {
    @Column(unique = true)
    private String name;
    @Column
    private String description;

    @ManyToMany(mappedBy = "roles")
    private Set<User> users;



}
