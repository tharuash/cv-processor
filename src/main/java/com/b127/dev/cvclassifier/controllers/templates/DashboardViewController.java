package com.b127.dev.cvclassifier.controllers.templates;

import com.b127.dev.cvclassifier.services.ResumeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = {"", "cv-classifier"})
public class DashboardViewController {

    private final ResumeService resumeService;

    @GetMapping
    public ModelAndView index(@RequestParam(value = "id", required = false) Long resumeId) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("ui/index");
        modelAndView.addObject("candidates", resumeService.getCandidatesDetails());

        if(resumeId != null) {
            modelAndView.addObject("resume", resumeService.getExpandedResumeById(resumeId));
        }
        return modelAndView;
    }

}
