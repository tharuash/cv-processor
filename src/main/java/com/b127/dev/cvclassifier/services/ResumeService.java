package com.b127.dev.cvclassifier.services;

import com.b127.dev.cvclassifier.dto.CandidateDTO;
import com.b127.dev.cvclassifier.dto.ResumeDTO;
import com.b127.dev.cvclassifier.dto.mappers.ResumeDTOMapper;
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
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ResumeService {

    private final ResumeRepository resumeRepository;
    private final ResumeParser resumeParser;
    private final ResumeDTOMapper resumeDTOMapper;

    private final String UPLOAD_DIR = "./uploads/";

    public boolean uploadResume(MultipartFile[] files, String candidateName) {
        var wrapper = new Object(){ boolean ifAnyFileFailedToUpdate = false; };

        Arrays.asList(files).forEach(file -> {
            String[] filePathParts = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename())).split("/");
            String fileName = filePathParts[filePathParts.length - 1];

            System.out.println(fileName);
            try{
                Path path = Paths.get(UPLOAD_DIR + fileName);
                Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

                Resume resume = resumeParser.parseUsingGateAndAnnie(path.toAbsolutePath().normalize().toString());
                resume.setName(candidateName);
                resume.setDocumentPath(path.toAbsolutePath().normalize().toString());

                resumeRepository.save(resume);

            }catch (IOException | GateException e ) {
                e.printStackTrace();
                wrapper.ifAnyFileFailedToUpdate = true;
            }

        });

        return !wrapper.ifAnyFileFailedToUpdate;

    }

    public List<CandidateDTO> getCandidatesDetails() {
        return resumeRepository.getAllCandidateDetailsSorted();
    }

    public ResumeDTO getExpandedResumeById(Long id) {
        Resume resume = resumeRepository.findById(id).orElseThrow(() -> new RuntimeException("No resume for given Id"));
        return resumeDTOMapper.mapIntoResumeDTO(resume);
    }

    public List<CandidateDTO> getCandidatesDetailsBySkill(String skill) {
        return resumeRepository.getCandidateDetailsFilteredBySkill(skill);
    }



}
