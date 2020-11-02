package com.capgemini.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

    @RequestMapping({"","/","/index","/index.html"})
    public String index(Model model){
        model.addAttribute("title", "Home Page");
        return "index";
    }
}
