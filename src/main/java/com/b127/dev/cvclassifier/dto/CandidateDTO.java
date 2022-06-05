package com.b127.dev.cvclassifier.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

@Getter
@Setter
@ToString
public class CandidateDTO {

    private Long resumeId;
    private String name;
    private String resumeUploadedTimestamp;

    public CandidateDTO(Long resumeId, String name, LocalDateTime dateTime) {
        this.resumeId = resumeId;
        this.name = name;
        this.resumeUploadedTimestamp = formatResumeUploadedTime(dateTime);
    }

    private String formatResumeUploadedTime(LocalDateTime dateTime) {
        return  dateTime.format(DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT))
                + " on "  + dateTime.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG));
    }

}
