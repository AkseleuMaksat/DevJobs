package kz.devjobs.service;

import kz.devjobs.entity.TwoFactorCode;
import kz.devjobs.entity.User;
import kz.devjobs.repository.TwoFactorCodeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class TwoFactorService {

    private final TwoFactorCodeRepository codeRepository;

    public String generateAndSendCode(User user) {
        String code = String.format("%06d", new Random().nextInt(999999));
        Instant expiry = Instant.now().plusSeconds(300); // 5 мин

        codeRepository.findByUser(user).ifPresent(codeRepository::delete);

        codeRepository.save(
                TwoFactorCode.builder()
                        .code(code)
                        .expiry(expiry)
                        .user(user)
                        .build()
        );
        return code;
    }

    public void verifyOrThrow(User user, String code) {
        TwoFactorCode tf = codeRepository.findByUserAndCode(user, code)
                .orElseThrow(() -> new SecurityException("Invalid 2FA code"));

        if (tf.getExpiry().isBefore(Instant.now())) {
            throw new SecurityException("2FA code expired");
        }
    }


    public void clearCode(User user) {
        codeRepository.findByUser(user).ifPresent(codeRepository::delete);
    }
}
