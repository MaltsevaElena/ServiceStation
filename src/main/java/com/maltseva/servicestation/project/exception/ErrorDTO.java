package com.maltseva.servicestation.project.exception;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class ErrorDTO
        implements Serializable {
    @Serial
    private static final long serialVersionUID = -2209578461216262505L;
    private final String message;
    private final String description;

    private List<FieldErrorDTO> fieldErrors;

    public ErrorDTO(String message) {
        this(message, null);
    }

    public ErrorDTO(String message,
                    String description
    ) {
        this.message = message;
        this.description = description;
    }

    public ErrorDTO(String message,
                    String description,
                    List<FieldErrorDTO> fieldErrors
    ) {
        this.message = message;
        this.description = description;
        this.fieldErrors = fieldErrors;
    }

    public void add(String objectName,
                    String field,
                    String message
    ) {
        if (fieldErrors == null) {
            fieldErrors = new ArrayList<>();
        }
        fieldErrors.add(new FieldErrorDTO(objectName, field, message));
    }

    public String getMessage() {
        return message;
    }

    public String getDescription() {
        return description;
    }

    public List<FieldErrorDTO> getFieldErrors() {
        return fieldErrors;
    }

}

