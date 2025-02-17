package de.etiennebader.breshub_engine.controller;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.nimbusds.oauth2.sdk.http.HTTPResponse;
import de.etiennebader.breshub_engine.dao.LoginResponse;
import de.etiennebader.breshub_engine.dao.RegisterRequest;
import de.etiennebader.breshub_engine.entities.ERole;
import de.etiennebader.breshub_engine.entities.Role;
import de.etiennebader.breshub_engine.entities.User;
import de.etiennebader.breshub_engine.model.UserDetailsImpl;
import de.etiennebader.breshub_engine.repositories.RoleRepository;
import de.etiennebader.breshub_engine.repositories.UserRepository;
import de.etiennebader.breshub_engine.util.JwtUtils;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import jakarta.validation.Validator;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/auth")
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    private JwtUtils jwtUtils;

    @GetMapping(value = "/login", produces = "application/json")
    public ResponseEntity<LoginResponse> login(@RequestParam String username, @RequestParam String password) {
        System.out.println("Try");
        try {
            System.out.println("Beginn Login");
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            if (SecurityContextHolder.getContext().getAuthentication().equals(authentication)) {
                return null;
            }
            //Aktualisiere ContextHolder
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtUtils.generateJwtToken(authentication);
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            System.out.println("ContextHolder");
            // Extrahiere die Rollen als Liste von Strings
            List<String> roles = userDetails.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toList());
            // Erstellt Gültige Response
            LoginResponse response = new LoginResponse();
            response.setAccess_token(jwt);
            response.setToken_type("Bearer");
            response.setExpires_in(7 * 24 * 60 * 60); // Gültigkeit eine Woche
            System.out.println("Check");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity register(@RequestBody RegisterRequest registerRequest) {
        if (userRepository.existsByUsername(registerRequest.getUsername())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        if (userRepository.existsByEmail(registerRequest.getEmail())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        // Create new user's account
        User user = new User();
        user.setUsername(registerRequest.getUsername());
        user.setEmail(registerRequest.getEmail());
        user.setPassword(encoder.encode(registerRequest.getPassword()));

        // Set the default role (for example: ROLE_USER)
        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
        user.getRoles().add(userRole);

        userRepository.save(user);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
