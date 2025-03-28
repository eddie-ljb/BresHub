package de.etiennebader.breshub_engine.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.etiennebader.breshub_engine.entities.User;
import de.etiennebader.breshub_engine.filter.AuthorizationFilter;
import de.etiennebader.breshub_engine.model.UserDetailsImpl;
import de.etiennebader.breshub_engine.repositories.RoleRepository;
import de.etiennebader.breshub_engine.repositories.UserRepository;
import de.etiennebader.breshub_engine.service.UserDetailsServiceImpl;
import de.etiennebader.breshub_engine.service.UserService;
import de.etiennebader.breshub_engine.util.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
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
    @Autowired
    private UserService userService;

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

    @GetMapping(path = "/getUserByUsername", produces = "application/json")
    public String getAllMembersInGroup(@RequestParam String username) throws JsonProcessingException {
        return mapper.writeValueAsString(userService.getUserFromUsername(username));
    }

    @GetMapping(path = "/getAllUser", produces = "application/json")
    public String getAllUser() throws JsonProcessingException {
        List<User> users = userRepository.findAll();
        List<String> usernames = new ArrayList<>();
        for (User user : users) {
            usernames.add(user.getUsername());
        }
        return mapper.writeValueAsString(usernames);
    }
}
