package com.wasiu.orbitsoftbackend;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final AppUserRepository appUserRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public AuthService(AppUserRepository appUserRepository) {
        this.appUserRepository = appUserRepository;
    }

    public AppUser register(String username, String password) {

        if (appUserRepository.findByUsername(username).isPresent()) {
            throw new RuntimeException("Username already exists");
        }

        String encodedPassword = passwordEncoder.encode(password);

        AppUser user = new AppUser(username, encodedPassword);

        return appUserRepository.save(user);
    }

    public AppUser authenticate(String username, String password) {

        AppUser user = appUserRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        return user;
    }
}