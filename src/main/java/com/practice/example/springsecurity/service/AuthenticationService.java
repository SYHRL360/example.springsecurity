package com.practice.example.springsecurity.service;

import com.practice.example.springsecurity.dto.LoginUserDto;
import com.practice.example.springsecurity.dto.RegisterUserDto;
import com.practice.example.springsecurity.entity.User;
import com.practice.example.springsecurity.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    public AuthenticationService(UserRepository userRepository,
                                 PasswordEncoder passwordEncoder,
                                 AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

    public User signup(RegisterUserDto input) {
        User user = new User();
        user.setFullName(input.getFullName());
        user.setEmail(input.getEmail());
        user.setPassword(passwordEncoder.encode(input.getPassword()));

        return userRepository.save(user);
    }

    public User authenticate(LoginUserDto input) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(input.getEmail(), input.getPassword())
        );
        return userRepository.findByEmail(input.getEmail()).orElseThrow();
    }
}
