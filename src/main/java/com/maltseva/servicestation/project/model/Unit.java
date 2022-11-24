package com.maltseva.servicestation.project.model;

/**
 * @author Maltseva
 * @version 1.0
 * @since 25.11.2022
 */

public enum Unit {
    PIECES("Шт"),
    LITERS("Л");

    private final String unitText;

    Unit(String unitText) {
        this.unitText = unitText;
    }

    public String getUnitName() {
        return this.unitText;
    }

}
