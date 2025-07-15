package cz.kavka.controller;

import cz.kavka.dto.UserDTO;
import cz.kavka.entity.UserEntity;
import cz.kavka.service.serviceinterface.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/user")
    public UserDTO addUser(@RequestBody @Valid UserDTO userDTO) {
        return userService.create(userDTO);
    }

    @PostMapping("/auth")
    public UserDTO login(@RequestBody @Valid UserDTO userDTO, HttpServletRequest request) throws ServletException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // If already authenticated, skip login
        if (authentication != null && authentication.isAuthenticated() &&
                !authentication.getPrincipal().equals("anonymousUser")) {
            // Optionally you can throw 409 Conflict or return the current user
            // throw new ServletException("User already logged in");
            UserEntity user = (UserEntity) authentication.getPrincipal();
            UserDTO dto = new UserDTO();
            dto.setAdmin(user.isAdmin());
            dto.setEmail(user.getEmail());
            dto.setId(user.getId());
            return dto;
        }

        // Perform login
        request.login(userDTO.getEmail(), userDTO.getPassword());

        // Get authenticated user
        UserEntity user = (UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDTO dto = new UserDTO();
        dto.setAdmin(user.isAdmin());
        dto.setEmail(user.getEmail());
        dto.setId(user.getId());
        return dto;
    }

    @DeleteMapping("/auth")
    public ResponseEntity<Map<String, String>> logout(HttpServletRequest request) throws ServletException {
        request.logout();

        Map<String, String> response = new HashMap<>();
        response.put("message", "Logged out");


        return ResponseEntity.ok(response);

    }

    @GetMapping("/auth")
    public ResponseEntity<?> getCurrentUser() throws ServletException {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated() ||
                authentication.getPrincipal().equals("anonymousUser")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Not logged in");
        }

        try {
            UserEntity user = (UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

            UserDTO userDTO = new UserDTO();
            userDTO.setEmail(user.getEmail());
            userDTO.setId(user.getId());
            userDTO.setAdmin(user.isAdmin());

            return ResponseEntity.ok(userDTO);
        } catch (ClassCastException e) {
            throw new ServletException();
        }
    }
}
