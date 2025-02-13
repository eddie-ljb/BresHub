package de.etiennebader.breshub_engine.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.etiennebader.breshub_engine.entities.User;
import de.etiennebader.breshub_engine.filter.AuthorizationFilter;
import de.etiennebader.breshub_engine.model.UserDetailsImpl;
import de.etiennebader.breshub_engine.repositories.RoleRepository;
import de.etiennebader.breshub_engine.repositories.UserRepository;
import de.etiennebader.breshub_engine.service.UserDetailsServiceImpl;
import de.etiennebader.breshub_engine.util.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping(path = "/credentials")
public class CredentialsController {

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

    @Autowired
    private UserDetailsService userDetailsService;

    private ObjectMapper mapper = new ObjectMapper();

    @GetMapping(path = "/getUsername", produces = "application/json")
    public String getUsernameFromJwtToken(@RequestHeader("Authorization") String authorization) throws JsonProcessingException {
        return mapper.writeValueAsString(jwtUtils.getUsernameFromJwtToken(authorization.substring(7)));
    }

    @GetMapping(path = "/getMail", produces = "application/json")
    public String getMailFromJwtToken(@RequestHeader("Authorization") String authorization) throws JsonProcessingException {
        String username = jwtUtils.getUsernameFromJwtToken(authorization.substring(7));
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isPresent()) {
            return mapper.writeValueAsString(user.get().getEmail());
        }
        return  "";
    }

    @GetMapping(path = "/getUser", produces = "application/json")
    public String getUserFromJwtToken(@RequestHeader("Authorization") String authorization) throws JsonProcessingException {
        String username = jwtUtils.getUsernameFromJwtToken(authorization.substring(7));
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isPresent()) {
            return mapper.writeValueAsString(user.get());
        }
        return  "";
    }
}
