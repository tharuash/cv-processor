package com.b127.dev.cvclassifier.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class AnalyzedEducationDTO {

    private String level;
    private int years;
}
