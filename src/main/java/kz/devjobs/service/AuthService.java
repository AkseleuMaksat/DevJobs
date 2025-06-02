package kz.devjobs.service;

import jakarta.transaction.Transactional;

import kz.devjobs.dto.request.LoginRequest;
import kz.devjobs.dto.request.RegisterRequest;
import kz.devjobs.dto.response.AuthResponse;
import kz.devjobs.entity.RefreshToken;
import kz.devjobs.entity.User;
import kz.devjobs.repository.UserRepository;
import kz.devjobs.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final RefreshTokenService refreshTokenService;
    private final AuthenticationManager authenticationManager;
    private final EmailService emailService;

    public void register(RegisterRequest request) {
        User user = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .build();
        userRepository.save(user);
    }

    public User authenticateAndGetUser(LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return (User) authentication.getPrincipal();
    }

    public AuthResponse generateTokens(User user) {
        String accessToken = jwtTokenProvider.generateToken(user.getEmail());
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(user.getId());
        return new AuthResponse(accessToken, refreshToken.getToken());
    }

    public AuthResponse refreshToken(String token) {
        RefreshToken refreshToken = refreshTokenService.findByToken(token)
                .filter(rt -> !refreshTokenService.isExpired(rt))
                .orElseThrow(() -> new RuntimeException("Refresh token is invalid or expired"));
        String newAccessToken = jwtTokenProvider.generateToken(refreshToken.getUser().getEmail());
        return new AuthResponse(newAccessToken, token);
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public void send2FACodeEmail(String to, String code) {
        emailService.send(to, "2FA Code", "Your 2FA verification code is: " + code);
    }
}
