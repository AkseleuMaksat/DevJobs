package kz.devjobs.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResumeResponse {
    private Long id;
    private String fullName;
    private String summary;
    private String skills;
    private String experience;
    private String education;
    private String candidateEmail;
}