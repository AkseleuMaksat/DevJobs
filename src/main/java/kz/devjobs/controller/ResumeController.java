package kz.devjobs.controller;

import kz.devjobs.dto.request.ResumeRequest;
import kz.devjobs.dto.response.ResumeResponse;
import kz.devjobs.entity.Resume;
import kz.devjobs.entity.User;
import kz.devjobs.service.ResumeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/resumes")
@RequiredArgsConstructor
public class ResumeController {

    private final ResumeService resumeService;

    @PreAuthorize("hasRole('CANDIDATE')")
    @PostMapping
    public ResponseEntity<Void> createResume(@RequestBody ResumeRequest request,
                                             @AuthenticationPrincipal User user) {
        resumeService.create(request, user);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<ResumeResponse>> getAll() {
        return ResponseEntity.ok(resumeService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Resume> getById(@PathVariable Long id) {
        return ResponseEntity.ok(resumeService.getById(id));
    }

    @PreAuthorize("hasRole('CANDIDATE')")
    @PutMapping("/{id}")
    public ResponseEntity<Resume> updateResume(@PathVariable Long id,
                                               @RequestBody ResumeRequest request,
                                               @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(resumeService.updateById(request, id, user));
    }

    @PreAuthorize("hasRole('CANDIDATE')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Resume> deleteResume(@PathVariable Long id,
                                               @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(resumeService.deleteById(id, user));
    }
}
