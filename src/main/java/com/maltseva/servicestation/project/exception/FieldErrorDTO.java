package com.maltseva.servicestation.project.exception;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class FieldErrorDTO
        implements Serializable {
    @Serial
    private static final long serialVersionUID = -728206791604686312L;
    private final String objectName;

    private final String field;

    private final String message;

    public FieldErrorDTO(String dto,
                         String field,
                         String message
    ) {
        this.objectName = dto;
        this.field = field;
        this.message = message;
    }
}

