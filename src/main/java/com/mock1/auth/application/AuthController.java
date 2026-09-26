package com.mock1.auth.application;

import com.mock1.auth.components.EmailAlreadyExistsException;
import com.mock1.auth.domain.AuthService;
import com.mock1.auth.dto.SignInRequest;
import com.mock1.auth.dto.SignUpRequest;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.AuthenticationException;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@RequestBody SignUpRequest request){
        try {
            return ResponseEntity.ok(authService.signUp(request));
        } catch (EmailAlreadyExistsException e) {
            return ResponseEntity.status(HttpStatusCode.valueOf(409)).body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> logIn(@RequestBody SignInRequest request){
        try {
            return ResponseEntity.ok(authService.signIn(request.getEmail(), request.getPassword()));
        } catch (AuthenticationException e){
            return ResponseEntity.status(HttpStatusCode.valueOf(401)).body("Invalid Credentials");
        }
    }
}
