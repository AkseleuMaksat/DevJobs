package kz.devjobs.repository;

import kz.devjobs.entity.JobVacancy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobVacancyRepository extends JpaRepository<JobVacancy, Long> {
    List<JobVacancy> findByLocationContainingIgnoreCaseAndSalaryGreaterThanEqualAndStackContainingIgnoreCase(
            String location, Integer salary, String stack);
}