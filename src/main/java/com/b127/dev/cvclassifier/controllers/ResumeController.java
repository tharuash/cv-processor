package com.b127.dev.cvclassifier.controllers;

import com.b127.dev.cvclassifier.dto.ResumeDTO;
import com.b127.dev.cvclassifier.services.ResumeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Controller
@RequestMapping("cv-classifier")
@RequiredArgsConstructor
public class ResumeController {

    private final ResumeService resumeService;

    @PostMapping("/upload")
    public String uploadFile(@RequestParam("file") MultipartFile file, @RequestParam("name") String name,
                             RedirectAttributes attributes, HttpSession httpSession) {
        if (file.isEmpty()) {
            attributes.addFlashAttribute("error", "Please select a file to upload.");
            return "redirect:/cv-classifier";
        }

        if(resumeService.uploadResume(file, name)) {
            attributes.addFlashAttribute("message", "You have successfully uploaded !");
        } else {
            attributes.addFlashAttribute("error", "File upload failed. Retry !");
        }

        return "redirect:/cv-classifier";
    }

}
