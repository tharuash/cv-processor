package com.b127.dev.cvclassifier.entity.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum City {
    KURUNEGALA("Kurunegala"),
    POLGAHAWELA("Polgahawela"),
    KANDY("Kandy"),
    GALLE("Galle"),
    NOT_FOUND("Not Found");
    final private String value;
    City(String name) {
        value = name;
    }

    @JsonValue
    @Override
    public String toString() {
        return this.value;
    }
}
