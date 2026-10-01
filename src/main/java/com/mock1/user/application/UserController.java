package com.mock1.user.application;

import com.mock1.user.domain.UserService;
import com.mock1.user.dto.UserRequestDto;
import com.mock1.user.dto.UserResponseDto;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<UserResponseDto>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @PostMapping
    public ResponseEntity<Void> createUser(@Valid @RequestBody UserRequestDto dto) {
        userService.createUser(dto);
        return ResponseEntity.status(HttpStatusCode.valueOf(201)).build();
    }
}
