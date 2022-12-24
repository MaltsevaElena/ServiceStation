package com.maltseva.servicestation.project.exception;

import java.io.Serial;

public class MailDoesNotExistException extends Exception {

    @Serial
    private static final long serialVersionUID = -1788221324786091406L;

    public MailDoesNotExistException(String message) {
        super(message);
    }
}
