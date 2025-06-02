package kz.devjobs.service;

import jakarta.persistence.EntityNotFoundException;
import kz.devjobs.dto.request.JobVacancyRequest;
import kz.devjobs.dto.response.JobVacancyResponse;
import kz.devjobs.entity.JobVacancy;
import kz.devjobs.entity.User;
import kz.devjobs.repository.JobVacancyRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class VacancyService {

    private final JobVacancyRepository repository;

    public void create(JobVacancyRequest request, User employer) {
        JobVacancy vacancy = JobVacancy.builder()
                .position(request.getPosition())
                .company(request.getCompany())
                .description(request.getDescription())
                .location(request.getLocation())
                .salary(request.getSalary())
                .employer(employer)
                .build();
        repository.save(vacancy);
    }

    public List<JobVacancyResponse> getAll() {
        return repository.findAll().stream()
                .map(v -> JobVacancyResponse.builder()
                        .id(v.getId())
                        .position(v.getPosition())
                        .company(v.getCompany())
                        .description(v.getDescription())
                        .location(v.getLocation())
                        .salary(v.getSalary())
                        .employerEmail(v.getEmployer().getEmail())
                        .build())
                .collect(toList());
    }
    public JobVacancy getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Vacancy not found"));
    }
    public JobVacancy updateById(JobVacancyRequest request, Long id, User employer) {
        JobVacancy vacancy = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Vacancy not found"));

        vacancy.setPosition(request.getPosition());
        vacancy.setCompany(request.getCompany());
        vacancy.setDescription(request.getDescription());
        vacancy.setLocation(request.getLocation());
        vacancy.setSalary(request.getSalary());
        vacancy.setEmployer(employer);

        return repository.save(vacancy);
    }
    public JobVacancy deleteById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Vacancy not found"));
    }
    public List<JobVacancyResponse> search(String location, Integer salary, String stack) {
        List<JobVacancy> result = repository
                .findByLocationContainingIgnoreCaseAndSalaryGreaterThanEqualAndStackContainingIgnoreCase(
                        location != null ? location : "",
                        salary != null ? salary : 0,
                        stack != null ? stack : ""
                );

        return result.stream().map(v -> JobVacancyResponse.builder()
                        .id(v.getId())
                        .position(v.getPosition())
                        .company(v.getCompany())
                        .description(v.getDescription())
                        .location(v.getLocation())
                        .salary(v.getSalary())
                        .stack(v.getStack())
                        .employerEmail(v.getEmployer().getEmail())
                        .build())
                .collect(Collectors.toList());
    }
}