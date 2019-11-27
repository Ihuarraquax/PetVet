package pl.zablocki.petvet.controllers;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping(path = "/")
    public String homePage() {
        return "home";
    }

    @GetMapping(path = "/login")
    public String login() {
        return "login";
    }




}


