package com.maltseva.servicestation.project.exception;

import java.io.Serial;

public class UpdateCarMileageException extends Exception {

    @Serial
    private static final long serialVersionUID = 8431950182363237676L;

    public UpdateCarMileageException(String message) {
        super(message);
    }
}
