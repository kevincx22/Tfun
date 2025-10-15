package edu.demo.edubackend.api;

import edu.demo.edubackend.dto.*;
import edu.demo.edubackend.entity.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final edu.demo.edubackend.repo.UserRepo userRepo;
    private final PasswordEncoder encoder;
    private final edu.demo.edubackend.security.JwtUtil jwt;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid edu.demo.edubackend.dto.LoginRequest req) {
        var user = userRepo.findByEmail(req.email()).orElse(null);
        if (user == null || !encoder.matches(req.password(), user.getPasswordHash())) {
            return ResponseEntity.status(401).body(Map.of("error","Invalid credentials"));
        }
        String token = jwt.generateToken(user.getEmail(), user.getRole());
        return ResponseEntity.ok(new LoginResponse(token, user.getRole()));
    }
}