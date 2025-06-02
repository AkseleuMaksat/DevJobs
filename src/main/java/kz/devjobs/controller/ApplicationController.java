package kz.devjobs.controller;

import kz.devjobs.dto.request.ApplicationRequest;
import kz.devjobs.dto.response.ApplicationResponse;
import kz.devjobs.entity.Application;
import kz.devjobs.entity.User;
import kz.devjobs.service.ApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/applications")
@RequiredArgsConstructor
public class ApplicationController {

    private final ApplicationService applicationService;

    @PostMapping
    public ResponseEntity<Void> apply(@RequestBody ApplicationRequest request,
                                      @AuthenticationPrincipal User user) {
        if (!user.getRole().name().equals("CANDIDATE")) {
            return ResponseEntity.status(403).build();
        }

        applicationService.apply(request, user);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<ApplicationResponse>> getAllForEmployer(@AuthenticationPrincipal User user) {
        if (!user.getRole().name().equals("EMPLOYER")) {
            return ResponseEntity.status(403).build();
        }

        return ResponseEntity.ok(applicationService.getApplicationsByEmployer(user.getId()));
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<Void> changeStatus(@PathVariable Long id,
                                             @RequestParam Application.Status status,
                                             @AuthenticationPrincipal User user) {
        if (!user.getRole().name().equals("EMPLOYER")) {
            return ResponseEntity.status(403).build();
        }

        applicationService.changeStatus(id, status);
        return ResponseEntity.ok().build();
    }
}
