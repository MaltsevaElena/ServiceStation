package com.maltseva.servicestation.project.exception;

import java.io.Serial;

public class UpdateRoleException extends Exception {

    @Serial
    private static final long serialVersionUID = -4896920276995353280L;

    public UpdateRoleException(String message) {
        super(message);
    }
}

