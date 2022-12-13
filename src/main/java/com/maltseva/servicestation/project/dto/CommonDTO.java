package com.maltseva.servicestation.project.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 *
 * @author Maltseva
 * @version 1.0
 * @since 27.11.2022
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CommonDTO {

    private String createdBy;
    private LocalDateTime createdWhen;

}
