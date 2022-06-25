package com.b127.dev.cvclassifier.dto.mappers;

import com.b127.dev.cvclassifier.dto.ResumeDTO;
import com.b127.dev.cvclassifier.entity.Resume;
import com.b127.dev.cvclassifier.entity.enums.Skill;
import com.b127.dev.cvclassifier.util.eductionanalyzer.Analyzer;
import lombok.RequiredArgsConstructor;
import org.ahocorasick.trie.Emit;
import org.ahocorasick.trie.Trie;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ResumeDTOMapper {

    private final Analyzer educationAnalyzer;

    public ResumeDTO mapIntoResumeDTO(Resume resume) {
        return new ResumeDTO(resume.getName(),
                resume.getEmail(),
                resume.getContactNo(),
                resume.getCity().toString(),
                resume.getEducation(),
                resume.getExperience(),
                extractSkills(resume.getSkills()),
                educationAnalyzer.analyzeEducation(resume.getEducation())
        );
    }

    private List<String> extractSkills(String skillsString) {
        List<String> keywords = Arrays.stream(Skill.values()).map(Skill::toString).collect(Collectors.toList());
        Trie trie = Trie.builder().addKeywords(keywords).build();

        return trie.parseText(skillsString).stream().map(Emit::getKeyword).distinct().collect(Collectors.toList());
    }
}
