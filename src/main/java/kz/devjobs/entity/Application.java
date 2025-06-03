package kz.devjobs.entity;

import jakarta.persistence.*;
import kz.devjobs.enums.ApplicationStatus;
import lombok.*;

@Entity
@Table(name = "application")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "resume_id")
    private Resume resume;

    @ManyToOne
    @JoinColumn(name = "vacancy_id")
    private JobVacancy vacancy;

    @Enumerated(EnumType.STRING)
    private ApplicationStatus status;
}
