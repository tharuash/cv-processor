package com.b127.dev.cvclassifier.controllers.templates;

import com.b127.dev.cvclassifier.services.ResumeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = {"", "cv-classifier"})
public class DashboardViewController {

    private final ResumeService resumeService;

    @GetMapping
    public ModelAndView index(@RequestParam(value = "id", required = false) Long resumeId, @RequestParam(value = "keyword", required = false) String skill) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("ui/index");


        if(resumeId != null) {
            try {
                modelAndView.addObject("resume", resumeService.getExpandedResumeById(resumeId));
            } catch (RuntimeException ex) {
                modelAndView.addObject("error" ,"No resume presented for selected candidate");
            }
        }

        if(skill != null ) {
            modelAndView.addObject("candidates", resumeService.getCandidatesDetailsBySkill(skill));
        } else {
            modelAndView.addObject("candidates", resumeService.getCandidatesDetails());
        }
        return modelAndView;
    }

}
