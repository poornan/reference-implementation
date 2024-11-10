package lk.anan.ri.dataviewer.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "redirect:/files";
    }

    @GetMapping("/error")
    public String error() {
        return "redirect:/files";
    }
}