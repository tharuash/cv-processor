package com.b127.dev.cvclassifier.repos;

import com.b127.dev.cvclassifier.dto.CandidateDTO;
import com.b127.dev.cvclassifier.entity.Resume;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResumeRepository extends JpaRepository<Resume, Long> {

    @Query(nativeQuery = true)
    List<CandidateDTO> getAllCandidateDetailsSorted();
    @Query(nativeQuery = true)
    List<CandidateDTO> getCandidateDetailsFilteredBySkill(String skill);

}
