package com.b127.dev.cvclassifier.entity;

import com.b127.dev.cvclassifier.dto.CandidateDTO;
import com.b127.dev.cvclassifier.entity.enums.City;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@NamedNativeQuery(name = "Resume.getAllCandidateDetailsSorted",
                  query = "SELECT r.id as resumeId, r.name, r.uploaded_time as dateTime" +
                          " FROM resumes r ORDER BY r.uploaded_time DESC",
                  resultSetMapping = "Mapping.CandidateDTO")
@SqlResultSetMapping(name = "Mapping.CandidateDTO",
                     classes = @ConstructorResult(targetClass = CandidateDTO.class,
                                                  columns = {@ColumnResult(name = "resumeId", type = Long.class),
                                                          @ColumnResult(name = "name" , type = String.class),
                                                          @ColumnResult(name = "dateTime", type = LocalDateTime.class)
                                                  }))
@Entity
@Table(name = "resumes")
public class Resume {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @NotBlank @NotEmpty
    private String name;

    @NotEmpty
    @NotNull
    private String email;

    @NotEmpty @NotBlank
    private String documentPath;

    @Enumerated(EnumType.STRING)
    private City city;
    private String contactNo;

    @Column(length = 1048575)
    private String education;

    @Column(length = 1048575)
    private String experience;

    @Column(length = 1048575)
    private String skills;
    private LocalDateTime uploadedTime;

}
