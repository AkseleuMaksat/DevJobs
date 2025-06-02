package kz.devjobs.service;

import jakarta.persistence.EntityNotFoundException;
import kz.devjobs.dto.request.ResumeRequest;
import kz.devjobs.dto.response.ResumeResponse;
import kz.devjobs.entity.Resume;
import kz.devjobs.entity.User;
import kz.devjobs.repository.ResumeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ResumeService {

    private final ResumeRepository resumeRepository;

    public void create(ResumeRequest request, User candidate) {
        Resume resume = Resume.builder()
                .fullName(request.getFullName())
                .summary(request.getSummary())
                .skills(request.getSkills())
                .experience(request.getExperience())
                .education(request.getEducation())
                .candidate(candidate)
                .build();
        resumeRepository.save(resume);
    }

    public List<ResumeResponse> getAll() {
        return resumeRepository.findAll().stream()
                .map(r -> ResumeResponse.builder()
                        .id(r.getId())
                        .fullName(r.getFullName())
                        .summary(r.getSummary())
                        .skills(r.getSkills())
                        .experience(r.getExperience())
                        .education(r.getEducation())
                        .candidateEmail(r.getCandidate().getEmail())
                        .build())
                .collect(Collectors.toList());
    }

    public Resume getById(Long id) {
        return resumeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Resume not found"));
    }

    public Resume updateById(ResumeRequest request, Long id, User user) {
        Resume resume = getById(id);
        if (!resume.getCandidate().getId().equals(user.getId())) {
            throw new SecurityException("You can update only your own resume");
        }
        resume.setFullName(request.getFullName());
        resume.setSummary(request.getSummary());
        resume.setSkills(request.getSkills());
        resume.setExperience(request.getExperience());
        resume.setEducation(request.getEducation());
        return resumeRepository.save(resume);
    }

    public Resume deleteById(Long id, User user) {
        Resume resume = getById(id);
        if (!resume.getCandidate().getId().equals(user.getId())) {
            throw new SecurityException("You can delete only your own resume");
        }
        resumeRepository.deleteById(id);
        return resume;
    }
}