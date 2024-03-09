package com.ecommerce.userModule.repository;

import com.ecommerce.userModule.entity.Role;
import com.ecommerce.util.common.BaseRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoleRepository extends BaseRepository<Role> {

    Optional<Role> findByName(String name);
    List<Role> findAllByNameContains(String name,Pageable pageable);

}
