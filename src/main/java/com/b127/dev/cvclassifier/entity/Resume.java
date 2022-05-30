package com.b127.dev.cvclassifier.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
@Entity
@Table(name = "resumes")
public class Resume {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @NotBlank @NotEmpty
    private String name;

    @Email
    @NotNull
    private String email;

    @NotEmpty @NotBlank
    private String documentPath;

    private String education;
    private String experience;
    private String skills;

}
