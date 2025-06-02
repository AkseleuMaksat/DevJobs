package kz.devjobs.service;

import kz.devjobs.repository.ApplicationRepository;
import kz.devjobs.repository.JobVacancyRepository;
import kz.devjobs.repository.ResumeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class StatsService {

    private final JobVacancyRepository jobVacancyRepo;
    private final ResumeRepository resumeRepo;
    private final ApplicationRepository appRepo;

    public Map<String, Long> getStats() {
        Map<String, Long> stats = new HashMap<>();
        stats.put("vacancies", jobVacancyRepo.count());
        stats.put("resumes", resumeRepo.count());
        stats.put("applications", appRepo.count());
        return stats;
    }
}
