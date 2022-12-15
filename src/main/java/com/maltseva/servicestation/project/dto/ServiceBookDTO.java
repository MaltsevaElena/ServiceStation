package com.maltseva.servicestation.project.dto;

import com.maltseva.servicestation.project.model.Unit;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ServiceBookDTO extends CommonDTO {

    private Long id;
    private LocalDate repairDate;

    private Long carId;
    private Integer mileageCar;

    private String nameSparePart;
    private String codeSparePart;
    private Integer amountSparePart;
    private Unit unitSparePart;

}
