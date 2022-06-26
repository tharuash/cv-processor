package com.b127.dev.cvclassifier.controllers.rest;

import com.b127.dev.cvclassifier.dto.ResumeDTO;
import com.b127.dev.cvclassifier.services.ResumeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@Slf4j
@RestController
@RequestMapping("api/cv-classifier")
@RequiredArgsConstructor
public class ExpandedResumeController {

    private final ResumeService resumeService;

    @GetMapping("/{id}")
    public ResumeDTO getExpandedResume(@PathVariable("id") Long resumeId) {

        ResumeDTO resumeDTO = null;

        try {
            resumeDTO = resumeService.getExpandedResumeById(resumeId);
        } catch (RuntimeException ex) {
            log.error("Error during resume extracting : {}", ex.getMessage());
        }

        return resumeDTO;
    }

}
