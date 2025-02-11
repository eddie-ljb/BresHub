package de.etiennebader.breshub_engine.service;

import de.etiennebader.breshub_engine.entities.ERole;
import de.etiennebader.breshub_engine.entities.Role;
import de.etiennebader.breshub_engine.repositories.RoleRepository;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.stereotype.Service;

@Hidden
@Service
public class RoleService {

    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public void createDefaultRoles() {
        Role roleUser = new Role();
        roleUser.setName(ERole.ROLE_USER);
        roleRepository.save(roleUser);

        Role roleAdmin = new Role();
        roleAdmin.setName(ERole.ROLE_ADMIN);
        roleRepository.save(roleAdmin);
    }
}
