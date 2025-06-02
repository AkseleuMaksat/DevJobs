package kz.devjobs.repository;

import kz.devjobs.entity.TwoFactorCode;
import kz.devjobs.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TwoFactorCodeRepository extends JpaRepository<TwoFactorCode, Long> {
    Optional<TwoFactorCode> findByUser(User user);
    Optional<TwoFactorCode> findByUserAndCode(User user, String code);
}
