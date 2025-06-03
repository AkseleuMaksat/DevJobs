package kz.devjobs.controller;

import kz.devjobs.dto.request.ApplicationRequest;
import kz.devjobs.dto.response.ApplicationResponse;
import kz.devjobs.entity.Application;
import kz.devjobs.entity.User;
import kz.devjobs.enums.ApplicationStatus;
import kz.devjobs.service.ApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/applications")
@RequiredArgsConstructor
public class ApplicationController {

    private final ApplicationService applicationService;

    @PreAuthorize("hasRole('CANDIDATE')")
    @PostMapping
    public ResponseEntity<Void> apply(@RequestBody ApplicationRequest request,
                                      @AuthenticationPrincipal User user) {
        applicationService.apply(request, user);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasRole('EMPLOYER')")
    @GetMapping
    public ResponseEntity<List<ApplicationResponse>> getAllForEmployer(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(applicationService.getApplicationsByEmployer(user.getId()));
    }

    @PreAuthorize("hasRole('EMPLOYER')")
    @PutMapping("/{id}/status")
    public ResponseEntity<Void> changeStatus(@PathVariable Long id,
                                             @RequestParam ApplicationStatus status,
                                             @AuthenticationPrincipal User user) {
        applicationService.changeStatus(id, status);
        return ResponseEntity.ok().build();
    }

}
