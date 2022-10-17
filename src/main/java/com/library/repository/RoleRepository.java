package com.library.repository;

import com.library.domain.Role;
import com.library.domain.enums.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(RoleType name);
    boolean existsByName(RoleType name);

}
