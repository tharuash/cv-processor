package com.b127.dev.cvclassifier.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class ResumeDTO {

    private String name;
    private String email;
    private String phone;
    private String city;
    private String education;
    private String experience;
    private List<String> skills;

}
