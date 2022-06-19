package com.b127.dev.cvclassifier.controllers;

import com.b127.dev.cvclassifier.services.SkillService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("cv-classifier/skills")
@AllArgsConstructor
public class SkillController {

    private final SkillService skillService;

    @GetMapping
    public List<String> getAvailableSkills() {
        return skillService.getAvailableSkills();
    }
}
