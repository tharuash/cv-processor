package com.b127.dev.cvclassifier.services;

import com.b127.dev.cvclassifier.repos.ResumeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
@RequiredArgsConstructor
public class ResumeService {

    private final String UPLOAD_DIR = "./uploads/";

    private final ResumeRepository resumeRepository;

    public boolean uploadResume(MultipartFile file, String candidateName) {

        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        try {
            Path path = Paths.get(UPLOAD_DIR + fileName);
            Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

            System.out.println("Name : " + candidateName);
            System.out.println("Path : " + path.toAbsolutePath().normalize());

            return true;
        } catch (IOException e) {
            e.printStackTrace();

            return false;
        }

    }

}
