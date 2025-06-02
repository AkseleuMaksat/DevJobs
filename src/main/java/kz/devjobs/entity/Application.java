package kz.devjobs.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "resume_id")
    private Resume resume;

    @ManyToOne
    @JoinColumn(name = "vacancy_id")
    private JobVacancy vacancy;

    @Enumerated(EnumType.STRING)
    private Status status;

    public enum Status {
        NEW, REVIEWED, ACCEPTED, REJECTED
    }
}
