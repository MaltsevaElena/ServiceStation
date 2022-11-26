package com.maltseva.servicestation.project.dto;

import lombok.*;

import java.time.LocalDate;
/**
 *
 * @author Maltseva
 * @version 1.0
 * @since 27.11.2022
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TariffDTO extends CommonDTO {

    private Long id;
    private Integer price;
    private LocalDate startDate;
    private LocalDate endDate;
}
