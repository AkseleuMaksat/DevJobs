package kz.devjobs.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class JobVacancyResponse {
    private Long id;
    private String position;
    private String company;
    private String description;
    private String location;
    private Integer salary;
    private String employerEmail;
    private String stack;
}