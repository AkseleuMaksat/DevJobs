package kz.devjobs.dto.request;

import lombok.Data;

@Data
public class JobVacancyRequest {
    private String position;
    private String company;
    private String description;
    private String location;
    private Integer salary;
    private String stack;
}