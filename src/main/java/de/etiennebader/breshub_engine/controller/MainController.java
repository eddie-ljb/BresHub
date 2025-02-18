package de.etiennebader.breshub_engine.controller;

import de.etiennebader.breshub_engine.filter.AuthorizationFilter;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class MainController {

    @GetMapping(path = "/")
    public String mainEndpoint() {
        return "redirect:/swagger-ui/index.html";
    }

    @GetMapping(path = "/swagger")
    public String swaggerEndpoint() {
        return "redirect:/swagger-ui/index.html";
    }

}
