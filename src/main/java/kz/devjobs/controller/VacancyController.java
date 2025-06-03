package kz.devjobs.controller;

import kz.devjobs.dto.request.JobVacancyRequest;
import kz.devjobs.dto.response.JobVacancyResponse;
import kz.devjobs.entity.JobVacancy;
import kz.devjobs.entity.User;
import kz.devjobs.service.VacancyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vacancies")
@RequiredArgsConstructor
public class VacancyController {

    private final VacancyService vacancyService;

    @PreAuthorize("hasRole('EMPLOYER')")
    @PostMapping
    public ResponseEntity<Void> createVacancy(@RequestBody JobVacancyRequest request,
                                              @AuthenticationPrincipal User user) {
        vacancyService.create(request, user);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<JobVacancyResponse>> getAll() {
        return ResponseEntity.ok(vacancyService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<JobVacancy> getById(@PathVariable Long id) {
        return ResponseEntity.ok(vacancyService.getById(id));
    }

    @PreAuthorize("hasRole('EMPLOYER')")
    @PutMapping("/{id}")
    public ResponseEntity<JobVacancy> updateVacancy(@PathVariable Long id,
                                                    @RequestBody JobVacancyRequest request,
                                                    @AuthenticationPrincipal User user) {
        JobVacancy updated = vacancyService.updateById(request, id, user);
        return ResponseEntity.ok(updated);
    }

    @PreAuthorize("hasRole('EMPLOYER')")
    @DeleteMapping("/{id}")
    public ResponseEntity<JobVacancy> deleteVacancy(@PathVariable Long id,
                                                    @AuthenticationPrincipal User user) {
        JobVacancy deleted = vacancyService.deleteById(id);
        return ResponseEntity.ok(deleted);
    }

    @GetMapping("/search")
    public ResponseEntity<List<JobVacancyResponse>> searchVacancies(
            @RequestParam(required = false) String location,
            @RequestParam(required = false) Integer minSalary,
            @RequestParam(required = false) String stack
    ) {
        return ResponseEntity.ok(vacancyService.search(location, minSalary, stack));
    }
}
