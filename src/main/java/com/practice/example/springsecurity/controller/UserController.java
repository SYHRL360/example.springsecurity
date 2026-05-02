package com.practice.example.springsecurity.controller;

import com.practice.example.springsecurity.entity.User;
import com.practice.example.springsecurity.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/users")
@RestController
@SecurityRequirement(name = "Authorization")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @Operation(summary = "Authenticate user", description = "Authenticate user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "500", description = "Failed to authenticated user")
    })
    @GetMapping("/me")
    public ResponseEntity<User> authenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        User currentUser = (User) authentication.getPrincipal();
        return ResponseEntity.ok(currentUser);
    }


    @Operation(summary = "Get all users data", description = "Get all users data")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "500", description = "Failed to all user data")
    })
    @GetMapping("/")
    public ResponseEntity<List<User>> allUsers() {
        List<User> users = userService.allUsers();
        return ResponseEntity.ok(users);
    }

}
