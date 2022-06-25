package com.b127.dev.cvclassifier.util.eductionanalyzer.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum EducationLevel {
    DEGREE("Degree"),
    DIPLOMA("Diploma"),
    NOT_FOUND("Not Found");
    final private String value;
    EducationLevel(String name) {
        value = name;
    }

    @JsonValue
    @Override
    public String toString() {
        return this.value;
    }
}
