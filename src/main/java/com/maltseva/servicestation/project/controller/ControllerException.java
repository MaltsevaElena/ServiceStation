package com.maltseva.servicestation.project.controller;

import java.io.Serial;

/**
 * @author Maltseva
 * @version 1.0
 * @since 12.12.2022
 */
public class ControllerException extends Exception {

    @Serial
    private static final long serialVersionUID = 3921951471644448026L;

    public ControllerException(String message) {
        super(message);
    }

    public ControllerException(Exception e) {
        super(e);
    }

    public ControllerException(String message, Exception e) {
        super(message, e);
    }
}

