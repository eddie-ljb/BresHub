package de.etiennebader.breshub_engine.service;

import de.etiennebader.breshub_engine.entities.ERole;
import de.etiennebader.breshub_engine.entities.Group;
import de.etiennebader.breshub_engine.entities.Role;
import de.etiennebader.breshub_engine.entities.User;
import de.etiennebader.breshub_engine.repositories.GroupsRepository;
import de.etiennebader.breshub_engine.repositories.RoleRepository;
import de.etiennebader.breshub_engine.repositories.UserRepository;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Hidden
@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private GroupsRepository groupsRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    GroupService groupService;

    public void registerUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
        user.setRoles(Collections.singleton(userRole));
        userRepository.save(user);
    }

    public boolean addGroupToUser(String username, String groupname) {
        try {
            User user = userRepository.findByUsername(username).get();
            List<Long> groups = user.getGroups();
            groups.add(groupService.getGroupByName(groupname).getId());
            user.setGroups(groups);
            userRepository.save(user);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean deleteGroupFromUser(String username, String groupname) {
        try {
            User user = userRepository.findByUsername(username).get();
            List<Long> groups = user.getGroups();
            groups.remove(groupService.getGroupByName(groupname).getId());
            user.setGroups(groups);
            userRepository.save(user);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public List<String> getMembersFromGroupByGroupname(String groupname) {
        List<String> members = new ArrayList<>();
        List<User> users = userRepository.findAll();
        Group group = groupService.getGroupByName(groupname);
        for (User user : users) {
            try {
                if (user.getGroups().toString().contains(group.getId().toString())) {
                    members.add(user.getUsername());
                }
            } catch (Exception e) {}
        }
        return members;
    }

    public User getUserFromUsername(String username) {
        return userRepository.findByUsername(username).get();
    }

    public List<Group> getGroupsFromUser(String username) {
        List<Group> groups = new ArrayList<>();
        groups = groupService.getAllGroups();
        List<Group> groupsOfUser = new ArrayList<>();
        for (Group group : groups) {
            if (this.getMembersFromGroupByGroupname(group.getName()).contains(username)) {
                groupsOfUser.add(group);
            }
        }
        return groupsOfUser;
    }
}
