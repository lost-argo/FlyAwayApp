package com.mock1.auth.domain;

import com.mock1.auth.components.EmailAlreadyExistsException;
import com.mock1.auth.components.JwtService;
import com.mock1.auth.dto.SignUpRequest;
import com.mock1.auth.dto.TokenResponse;
import com.mock1.user.infrastructure.UserRepository;
import com.mock1.user.domain.User;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.management.relation.Role;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    public TokenResponse signUp(SignUpRequest request){
        if (userRepository.findByEmail(request.getEmail()).isPresent()){
            throw new EmailAlreadyExistsException("Email already sgined");
        }
        User user = userRepository.save(
                new User(request.getEmail(),passwordEncoder.encode(request.getPassword()),request.getFirstName(),request.getLastName(), request.getRole()));
        return new TokenResponse(jwtService.generateToken(user));
    }

    public TokenResponse signIn(String username, String password){
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        User user = userRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return new TokenResponse(jwtService.generateToken(user));
    }
}
