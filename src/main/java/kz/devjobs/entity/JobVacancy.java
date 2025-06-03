package kz.devjobs.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "job_vacancies")
@Data
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JobVacancy {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String position;
    private String company;
    private String description;
    private String location;
    private Integer salary;
    private String stack;

    @ManyToOne
    @JoinColumn(name = "employer_id")
    private User employer;
}