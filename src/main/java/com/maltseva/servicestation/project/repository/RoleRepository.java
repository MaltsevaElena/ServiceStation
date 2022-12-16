package com.maltseva.servicestation.project.repository;

import com.maltseva.servicestation.project.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Maltseva
 * @version 1.0
 * @since 16.12.2022
 */
public interface RoleRepository extends JpaRepository<Role, Long> {
}
