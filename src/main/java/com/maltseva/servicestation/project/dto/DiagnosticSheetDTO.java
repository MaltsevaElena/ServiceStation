package com.maltseva.servicestation.project.dto;

import com.maltseva.servicestation.project.model.DiagnosticSheet;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

/**
 * @author Maltseva
 * @version 1.0
 * @since 15.12.2022
 */

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class DiagnosticSheetDTO extends CommonDTO {

    private Long id;
    private LocalDate repairDate;

    private Long carId;
    private String registrationNumber;
    private String vin;
    private Integer mileage;

    private String lastNameOwnerCar;
    private String firstNameOwnerCar;
    private String middleNameOwnerCar;
    private String phoneOwnerCar;
    private String emailOwnerCar;

    private Long employerId;
    private String lastNameEmployer;
    private String firstNameEmployer;
    private String middleNameEmployer;
    private String namePosition;

    private String nameServiceStation;
    private String addressServiceStation;

    private String result;


    public DiagnosticSheetDTO(DiagnosticSheet diagnosticSheet) {
        this.id = diagnosticSheet.getId();
        this.repairDate = diagnosticSheet.getRepairDate();
        this.employerId = diagnosticSheet.getEmployer().getId();
        this.carId = diagnosticSheet.getCar().getId();

    }

}
