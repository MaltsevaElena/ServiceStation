package com.maltseva.servicestation.project.model;

/**
 * The position class is associated with the user by a one-to-many relationship
 *
 * @author Maltseva
 * @version 1.0
 * @since 25.11.2022
 */


public enum Position {
    MECHANIC("Механик"),
    WARRANTY_ENGINEER("Инженер по гарантии"),
    FOREMAN("Начальник смены"),
    DIAGNOSTICIAN("Диагност"),
    SERVICE_CONSULTANT("Сервисный консультант"),
    HEAD_OF_DEPARTMENT("Начальник отдела"),
    DIRECTOR("Директор");

    private final String positionText;

    Position(String positionText) {
        this.positionText = positionText;
    }

    public String getPositionName() {
        return this.positionText;
    }

}
