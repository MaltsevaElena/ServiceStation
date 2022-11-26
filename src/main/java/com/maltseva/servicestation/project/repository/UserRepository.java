package com.maltseva.servicestation.project.repository;

import com.maltseva.servicestation.project.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
/**
 *
 * @author Maltseva
 * @version 1.0
 * @since 27.11.2022
 */
public interface UserRepository extends JpaRepository<User, Long> {
}