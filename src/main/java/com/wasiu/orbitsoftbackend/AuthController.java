package com.wasiu.orbitsoftbackend;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    private final JwtService jwtService;

    public AuthController(AuthService authService, JwtService jwtService) {
        this.authService = authService;
        this.jwtService = jwtService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Map<String, String> request) {

        String username = request.get("username");
        String password = request.get("password");

        AppUser user = authService.register(username, password);

        return ResponseEntity.ok("User registered: " + user.getUsername());
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> request) {

        String username = request.get("username");
        String password = request.get("password");

        AppUser user = authService.authenticate(username, password);

        String token = jwtService.generateToken(username);
        return ResponseEntity.ok(Map.of("token", token));
    }
}

