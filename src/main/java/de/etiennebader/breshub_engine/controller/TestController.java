package de.etiennebader.breshub_engine.controller;

import de.etiennebader.breshub_engine.filter.AuthorizationFilter;
import de.etiennebader.breshub_engine.util.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.RequestEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestAttributes;

@RestController
@RequestMapping(path = "/test")
public class TestController {

    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private UserDetailsService userDetailsService;

    private AuthorizationFilter filter;

    @GetMapping(path = "/helloworld")
    public String helloWorld(@RequestHeader("Authorization") String authorization) {
         filter = new AuthorizationFilter(jwtUtils, userDetailsService);
        return "Hello World and "+  filter.getUsernameFromJwtToken(authorization.substring(7));
    }
}
