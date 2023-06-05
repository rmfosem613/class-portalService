package kr.ac.jejunu.project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("")
    public String showHomePage() {
        return "index";
    }

    @GetMapping("/create")
    public String showCreatePage() {
        return "create";
    }

    @GetMapping("/update")
    public String showUpdatePage() {
        return "update";
    }
}
