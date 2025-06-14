package kz.devjobs.repository;

import kz.devjobs.entity.Application;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ApplicationRepository extends JpaRepository<Application, Long> {
    List<Application> findByVacancyEmployerId(Long employerId);
}
