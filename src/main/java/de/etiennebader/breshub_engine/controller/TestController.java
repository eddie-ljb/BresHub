package de.etiennebader.breshub_engine.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/test")
public class TestController {

    @RequestMapping("/helloworld")
    public String helloWorld() {
        return "Hello World";
    }
}
