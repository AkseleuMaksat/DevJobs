package kz.devjobs.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "resume")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Resume {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String fullName;
    private String summary;
    private String skills;
    private String experience;
    private String education;

    @ManyToOne
    @JoinColumn(name = "candidate_id")
    private User candidate;
}