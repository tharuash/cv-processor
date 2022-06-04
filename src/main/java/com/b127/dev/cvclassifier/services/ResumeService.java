package com.b127.dev.cvclassifier.services;

import com.b127.dev.cvclassifier.entity.Resume;
import com.b127.dev.cvclassifier.repos.ResumeRepository;
import com.b127.dev.cvclassifier.util.resumeparser.ResumeParser;
import gate.util.GateException;
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

    private final ResumeRepository resumeRepository;
    private final ResumeParser resumeParser;

    private final String UPLOAD_DIR = "./uploads/";

    public boolean uploadResume(MultipartFile file, String candidateName) {

        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        try {
            Path path = Paths.get(UPLOAD_DIR + fileName);
            Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

            System.out.println("Name : " + candidateName);
            System.out.println("Path : " + path.toAbsolutePath().normalize());

            Resume resume = resumeParser.parseUsingGateAndAnnie(path.toAbsolutePath().normalize().toString());

            System.out.println("Resume : " + resume.toString());

            return true;
        } catch (IOException | GateException e ) {
            e.printStackTrace();

            return false;
        }

    }


}
