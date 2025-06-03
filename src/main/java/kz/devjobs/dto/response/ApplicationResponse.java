package kz.devjobs.dto.response;

import kz.devjobs.entity.Application;
import kz.devjobs.enums.ApplicationStatus;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ApplicationResponse {
    private Long id;
    private Long resumeId;
    private Long vacancyId;
    private ApplicationStatus status;
}