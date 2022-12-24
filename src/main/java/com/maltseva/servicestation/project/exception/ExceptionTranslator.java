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

    @ExceptionHandler(UpdateRoleException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorDTO handleUpdateRoleException(UpdateRoleException ex) {
        return processFieldErrors(ex, "Не возможно изменить роль пользователя", ex.getMessage());
    }

    @ExceptionHandler(UpdateCarMileageException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorDTO handleUpdateCarMileageException(UpdateCarMileageException ex) {
        return processFieldErrors(ex, "Нельзя уменьшить пробег автомобиля", ex.getMessage());
    }


    private ErrorDTO processFieldErrors(Exception e,
                                        String error,
                                        String description) {
        ErrorDTO errorDTO = new ErrorDTO(error, description);
        errorDTO.add(e.getClass().getName(), "", e.getMessage());
        return errorDTO;
    }

}

