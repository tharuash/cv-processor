package com.b127.dev.cvclassifier.services;

import com.b127.dev.cvclassifier.entity.enums.Skill;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SkillService {

    public List<String> getAvailableSkills() {
        return Arrays.stream(Skill.values()).map(Skill::toString).collect(Collectors.toList());
    }
}
