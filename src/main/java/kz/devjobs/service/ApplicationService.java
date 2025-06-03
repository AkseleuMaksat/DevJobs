package kz.devjobs.service;

import jakarta.persistence.EntityNotFoundException;
import kz.devjobs.dto.request.ApplicationRequest;
import kz.devjobs.dto.response.ApplicationResponse;
import kz.devjobs.entity.Application;
import kz.devjobs.entity.JobVacancy;
import kz.devjobs.entity.Resume;
import kz.devjobs.entity.User;
import kz.devjobs.enums.ApplicationStatus;
import kz.devjobs.repository.ApplicationRepository;
import kz.devjobs.repository.JobVacancyRepository;
import kz.devjobs.repository.ResumeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ApplicationService {

    private final ApplicationRepository applicationRepository;
    private final ResumeRepository resumeRepository;
    private final JobVacancyRepository vacancyRepository;
    private final EmailService emailService;

    public void apply(ApplicationRequest request, User candidate) {
        Resume resume = resumeRepository.findById(request.getResumeId())
                .orElseThrow(() -> new EntityNotFoundException("Resume not found"));
        JobVacancy vacancy = vacancyRepository.findById(request.getVacancyId())
                .orElseThrow(() -> new EntityNotFoundException("Vacancy not found"));

        Application application = Application.builder()
                .resume(resume)
                .vacancy(vacancy)
                .status(ApplicationStatus.NEW)
                .build();

        applicationRepository.save(application);
        emailService.send(
                vacancy.getEmployer().getEmail(),
                "Новый отклик на вакансию",
                "Кандидат " + candidate.getEmail() + " откликнулся на вакансию: " + vacancy.getPosition()
        );
    }

    public List<ApplicationResponse> getApplicationsByEmployer(Long employerId) {
        return applicationRepository.findByVacancyEmployerId(employerId).stream()
                .map(a -> ApplicationResponse.builder()
                        .id(a.getId())
                        .resumeId(a.getResume().getId())
                        .vacancyId(a.getVacancy().getId())
                        .status(a.getStatus())
                        .build())
                .collect(Collectors.toList());
    }

    public void changeStatus(Long applicationId, ApplicationStatus status) {
        Application application = applicationRepository.findById(applicationId)
                .orElseThrow(() -> new EntityNotFoundException("Application not found"));
        application.setStatus(status);
        applicationRepository.save(application);
    }
}
