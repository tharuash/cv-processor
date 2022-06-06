package com.b127.dev.cvclassifier.entity.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum Skill {
    JAVA("Java"),
    C("C"),
    SPRING("Spring"),
    SQL("SQL"),
    NODE_JS("NodeJS"),
    PYTHON("Python"),
    DESIGN_PATTERNS("Design Patterns"),
    ALGORITHMS("Algorithms");
    final private String value;
    Skill(String value) {
        this.value = value;
    }

    @JsonValue
    @Override
    public String toString() {
        return this.value;
    }
}
