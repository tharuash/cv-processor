package com.b127.dev.cvclassifier.services;

import com.b127.dev.cvclassifier.dto.CandidateDTO;
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
import java.util.List;

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

            Resume resume = resumeParser.parseUsingGateAndAnnie(path.toAbsolutePath().normalize().toString());
            resume.setName(candidateName);
            resume.setDocumentPath(path.toAbsolutePath().normalize().toString());

            resumeRepository.save(resume);

            return true;
        } catch (IOException | GateException e ) {
            e.printStackTrace();

            return false;
        }
    }

    public List<CandidateDTO> getCandidatesDetails() {
        return resumeRepository.getAllCandidateDetailsSorted();
    }

    public Resume getExpandedResumeById(Long id) {
        return resumeRepository.findById(id).orElseThrow(() -> new RuntimeException("No resume for given Id"));
    }



}
