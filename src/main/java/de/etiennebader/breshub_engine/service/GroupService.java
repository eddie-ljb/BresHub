package de.etiennebader.breshub_engine.service;

import de.etiennebader.breshub_engine.entities.Group;
import de.etiennebader.breshub_engine.repositories.GroupsRepository;
import de.etiennebader.breshub_engine.repositories.RoleRepository;
import de.etiennebader.breshub_engine.repositories.UserRepository;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Hidden
@Service
public class GroupService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private GroupsRepository groupsRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<Group> getAllGroups() {
        return groupsRepository.findAllGroups();
    }

    public Group getGroupByName(String groupName) {
        if (groupsRepository.findByName(groupName).isPresent()) {
            return groupsRepository.findByName(groupName).get();
        }
        return null;
    }

    public Group getGroupById(Long id) {
        if (groupsRepository.findByID(id).isPresent()) {
            return groupsRepository.findByID(id).get();
        }
        return null;
    }

    public boolean saveGroup(Group group) {
        if (!(groupsRepository.existsByUsername(group.getName()))) {
            groupsRepository.save(group);
            return true;
        }
        return false;
    }

    public boolean deleteGroup(String groupName) {
        try {
            Long id = groupsRepository.findByName(groupName).get().getId();
            groupsRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
