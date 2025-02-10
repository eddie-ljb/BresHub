package de.etiennebader.breshub_engine.service;

import de.etiennebader.breshub_engine.entities.ERole;
import de.etiennebader.breshub_engine.entities.Role;
import de.etiennebader.breshub_engine.entities.User;
import de.etiennebader.breshub_engine.repositories.RoleRepository;
import de.etiennebader.breshub_engine.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void registerUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
        user.setRoles(Collections.singleton(userRole));
        userRepository.save(user);
    }
}
