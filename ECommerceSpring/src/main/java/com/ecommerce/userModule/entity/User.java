package com.ecommerce.userModule.entity;


import com.ecommerce.util.dbUtil.BaseEntity;
import lombok.*;

import jakarta.persistence.*;

import java.util.Set;


@Entity
@Data
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@AttributeOverride(
        name = "uuid",
        column = @Column(
                name = "user_uuid"
        )
)
public class User extends BaseEntity {

    @Column
    private String firstName;
    @Column
    private String lastName;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String email;


    @ManyToMany(fetch = FetchType.LAZY)
    @EqualsAndHashCode.Exclude @ToString.Exclude
    @JoinColumn(name="role_id", nullable = false)
    private Set<Role> roles;

}
