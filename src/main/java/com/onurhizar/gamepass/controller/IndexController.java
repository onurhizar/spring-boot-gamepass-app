package com.onurhizar.gamepass.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class IndexController {

    @GetMapping("/")
    public RedirectView redirectToSwaggerUI() {
        return new RedirectView("/swagger-ui/index.html");
    }

}
