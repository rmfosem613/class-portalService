package kr.ac.jejunu.project.controller;

import kr.ac.jejunu.project.entity.Post;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("")
    public String showHomePage() {
        return "index";
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("post", new Post());
        return "create";
    }

//    @GetMapping("/update")
//    public String showUpdatePage() {
//        return "update";
//    }
}
