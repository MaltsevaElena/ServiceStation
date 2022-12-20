package com.maltseva.servicestation.project.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ExceptionTranslator {

    @ExceptionHandler(MyDeleteException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorDTO handleMyDeleteException(MyDeleteException ex) {
        return processFieldErrors(ex, "Удаление невозможно", ex.getMessage());
    }

    private ErrorDTO processFieldErrors(Exception e,
                                        String error,
                                        String description) {
        ErrorDTO errorDTO = new ErrorDTO(error, description);
        errorDTO.add(e.getClass().getName(), "", e.getMessage());
        return errorDTO;
    }

}

