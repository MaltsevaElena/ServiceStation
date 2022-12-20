package com.maltseva.servicestation.project.exception;


import java.io.Serial;

public class MyDeleteException
        extends Exception {
    @Serial
    private static final long serialVersionUID = -8125510535046328182L;

    public MyDeleteException(String message) {
        super(message);
    }
}

