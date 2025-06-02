package kz.devjobs.dto.request;

import lombok.Data;

@Data
public class ResumeRequest {
    private String fullName;
    private String summary;
    private String skills;
    private String experience;
    private String education;

}