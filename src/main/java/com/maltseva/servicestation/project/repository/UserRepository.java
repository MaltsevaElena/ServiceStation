package com.maltseva.servicestation.project.repository;

import com.maltseva.servicestation.project.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

/**
 * @author Maltseva
 * @version 1.0
 * @since 27.11.2022
 */
public interface UserRepository extends JpaRepository<User, Long> {

    @Query(value = "select id from users where employee_chief_id = ?1", nativeQuery = true)
    List<Long> userIdByEmployeeChiefId(Long userId);

    @Query(value = "select * from users", nativeQuery = true)
    List<User> allUsers();

    @Query(value = "select * from users u inner join cars c on u.id = c.user_id where c.id = ?1", nativeQuery = true)
    Optional<User> userByCarId(Long carId);
}