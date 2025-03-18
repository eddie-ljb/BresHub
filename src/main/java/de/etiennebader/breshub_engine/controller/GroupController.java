package de.etiennebader.breshub_engine.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.etiennebader.breshub_engine.dao.GroupRequest;
import de.etiennebader.breshub_engine.entities.Group;
import de.etiennebader.breshub_engine.repositories.RoleRepository;
import de.etiennebader.breshub_engine.repositories.UserRepository;
import de.etiennebader.breshub_engine.service.GroupService;
import de.etiennebader.breshub_engine.service.UserService;
import de.etiennebader.breshub_engine.util.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "/groups")
public class GroupController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    GroupService groupService;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserDetailsService userDetailsService;

    private ObjectMapper mapper = new ObjectMapper();
    @Autowired
    private UserService userService;

    @GetMapping(path = "/getAllGroups", produces = "application/json")
    public String getAllGroupsSaved() throws JsonProcessingException {
        return mapper.writeValueAsString(groupService.getAllGroups());
    }

    @GetMapping(path = "/getGroupByName", produces = "application/json")
    public String getGroupByName(@RequestParam String groupName) throws JsonProcessingException {
        return mapper.writeValueAsString(groupService.getGroupByName(groupName));
    }

    @PostMapping(value = "/updateGroup", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity createGroup(@RequestBody GroupRequest groupRequest) {
        try {
            if (!(groupService.getGroupByName(groupRequest.getName()).getName().isEmpty())) {
                try {
                    List<String> members = groupRequest.getMembers();
                    for (String member : members) {
                        userService.addGroupToUser(member, groupRequest.getName());
                    }
                    return ResponseEntity.status(HttpStatus.OK).build();
                } catch (Exception e) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
                }
            }
        } catch (Exception e) {}

        try {
            Group group = new Group();
            group.setName(groupRequest.getName());
            groupService.saveGroup(group);
            List<String> members = groupRequest.getMembers();
            for (String member : members) {
                userService.addGroupToUser(member, group.getName());
            }
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping(value = "/deleteGroup", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity createGroup(@RequestBody String groupName) throws JsonProcessingException {
        try {
            groupService.deleteGroup(groupName);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping(value = "/removeGroupMember", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity removeMemberFromGroup(@RequestBody GroupRequest removeGroupMemberRequest) throws JsonProcessingException {
        try {
            for (String member : removeGroupMemberRequest.getMembers()) {
                userService.deleteGroupFromUser(member, removeGroupMemberRequest.getName());
            }
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping(path = "/getAllMembersInGroup", produces = "application/json")
    public String getAllMembersInGroup(@RequestParam String groupName) throws JsonProcessingException {
        List<String> members = new ArrayList<>();
        return mapper.writeValueAsString(userService.getMembersFromGroupByGroupname(groupName));
    }

    @GetMapping(path = "/getGroupsOfUser", produces = "application/json")
    public String getAllGroupsInfosOfUser(@RequestParam String username) throws JsonProcessingException {
        Map<String, Object> response = new HashMap<>();
        Map<String, List<String>> members = new HashMap();
        Map<String, Number> membersCount = new HashMap<>();
        List<Group> groups = userService.getGroupsFromUser(username);
        for (Group group : groups) {
            members.put(group.getName(), userService.getMembersFromGroupByGroupname(group.getName()));
            membersCount.put(group.getName(), userService.getMembersFromGroupByGroupname(group.getName()).size());
        }
        response.put("groups", groups);
        response.put("counter", userService.getGroupsFromUser(username).size());
        response.put("members", members);
        response.put("membersCount", membersCount);
        return mapper.writeValueAsString(response);
    }

    @GetMapping(path = "/getGroupsCounterOfUser", produces = "application/json")
    public String getGroupsCounterOfUser(@RequestParam String username) throws JsonProcessingException {
        return mapper.writeValueAsString(userService.getGroupsFromUser(username).size());
    }

}
