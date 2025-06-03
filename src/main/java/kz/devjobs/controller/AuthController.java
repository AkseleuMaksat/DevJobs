package kz.devjobs.controller;


import kz.devjobs.dto.request.LoginRequest;
import kz.devjobs.dto.request.RefreshRequest;
import kz.devjobs.dto.request.RegisterRequest;
import kz.devjobs.dto.request.TwoFactorRequest;
import kz.devjobs.dto.response.AuthResponse;
import kz.devjobs.entity.User;
import kz.devjobs.service.AuthService;
import kz.devjobs.service.TwoFactorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final TwoFactorService twoFactorService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest request) {
        authService.register(request);
        return ResponseEntity.ok("User registered successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest request) {
        User user = authService.authenticateAndGetUser(request);
        String code = twoFactorService.generateAndSendCode(user);
        authService.send2FACodeEmail(user.getEmail(), code);
        return ResponseEntity.ok("2FA code sent to your email");
    }

    @PostMapping("/verify-2fa")
    public ResponseEntity<AuthResponse> verifyTwoFactorCode(@RequestBody TwoFactorRequest request) {
        User user = authService.getUserByEmail(request.getEmail());
        twoFactorService.verifyOrThrow(user, request.getCode());
        twoFactorService.clearCode(user);
        AuthResponse tokens = authService.generateTokens(user);
        return ResponseEntity.ok(tokens);
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthResponse> refresh(@RequestBody RefreshRequest request) {
        return ResponseEntity.ok(authService.refreshToken(request.getRefreshToken()));
    }

    @PostMapping("/send-2fa")
    public ResponseEntity<String> send2FA(@RequestParam String email) {
        User user = authService.getUserByEmail(email);
        String code = twoFactorService.generateAndSendCode(user);
        authService.send2FACodeEmail(email, code);
        return ResponseEntity.ok("2FA code resent");
    }
}
