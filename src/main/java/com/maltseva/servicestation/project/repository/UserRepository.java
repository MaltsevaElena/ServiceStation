package com.maltseva.servicestation.project.repository;

import com.maltseva.servicestation.project.dto.UserChiefDTO;
import com.maltseva.servicestation.project.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import java.util.*;

/**
 * @author Maltseva
 * @version 1.0
 * @since 22.12.2022
 */
public interface UserRepository extends JpaRepository<User, Long> {

    @Query(value = "select * from users", nativeQuery = true)
    List<User> allUsers();

    @Query(value = "select * from users u inner join cars c on u.id = c.user_id where c.id = ?1", nativeQuery = true)
    Optional<User> userByCarId(Long carId);

    User findUserByLogin(String username);

    User findByBackUpEmail(String email);

    default List<UserChiefDTO> userEmployeeByChiefId(final Long id, NamedParameterJdbcTemplate jdbcTemplate) {
        List<UserChiefDTO> userEmployeesList = new ArrayList<>();
        SqlParameterSource namedParameters = new MapSqlParameterSource("id", id);
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(
                """
                        WITH RECURSIVE temp1 (id, last_name, first_name, middle_name, chief_id, PATH, grade) AS (
                             SELECT T1.id, T1.last_name, T1.first_name, T1.middle_name, T1.employee_chief_id AS chief_id, 
                             CAST (T1.id AS VARCHAR (50)) as PATH, 1
                             FROM users T1 WHERE T1.employee_chief_id IS NULL AND T1.position_id IS NOT NULL
                             union
                             select T2.id ,T2.last_name, T2.first_name, T2.middle_name, T2.employee_chief_id AS chief_id, 
                             CAST ( temp1.PATH ||'->'|| T2.id AS VARCHAR(50)), grade + 1
                             FROM users T2 INNER JOIN temp1 ON(temp1.id = T2.employee_chief_id))
                         select * from temp1 Where temp1.PATH LIKE concat('%', :id, '-%') ORDER BY PATH LIMIT 100;
                         """,
                namedParameters);

        for (Map<String, Object> map : rows) {
            UserChiefDTO userChiefDTO = new UserChiefDTO();

            userChiefDTO.setId((Long) map.get("id"));
            userChiefDTO.setLastName(map.get("last_name").toString());
            userChiefDTO.setFirstName(map.get("first_name").toString());
            if (map.get("middle_name") != null) {
                userChiefDTO.setMiddleName(map.get("middle_name").toString());
            }
            userChiefDTO.setChiefId((Long) map.get("chief_id"));
            userChiefDTO.setPathChief(map.get("PATH").toString());
            userChiefDTO.setGrade((Integer) map.get("grade"));

            userEmployeesList.add(userChiefDTO);
        }
        return userEmployeesList;
    }
}
