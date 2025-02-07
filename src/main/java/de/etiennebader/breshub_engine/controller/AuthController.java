package de.etiennebader.breshub_engine.controller;

import de.etiennebader.breshub_engine.dao.LoginResponse;
import de.etiennebader.breshub_engine.dao.RegisterRequest;
import jakarta.validation.Valid;
import jakarta.validation.Validator;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/auth")
public class AuthController {


    @GetMapping(value = "/login", produces = "application/json;charset=UTF-8")
    public ResponseEntity<Object> login(@RequestParam String username, @RequestParam String password) {
        LoginResponse response;
        String token = "TestToken fuer" + username;
        String tokenType = "Bearer";
        int expiresIn = 3600;
        try {
            return new ResponseEntity<>(new LoginResponse(token, tokenType, expiresIn), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void register(@RequestBody RegisterRequest registerRequest) {
        System.out.println(registerRequest.getEmail().toString());
    }

}
