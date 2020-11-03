package com.capgemini.controllers;


import com.capgemini.domains.Publisher;
import com.capgemini.services.PublisherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("/publishers")
public class PublisherController {

    @Autowired
    private PublisherService publisherService;


    @RequestMapping({"", "/", "index", "index.html"})
    public String index(Model model){
        model.addAttribute("title", "Publishers List");
        model.addAttribute("publishers", publisherService.findAll());
        return "publisher/index";
    }

    @GetMapping({"/create"})
    public String renderCreatePublisherForm(Model model){
        model.addAttribute("title", "Publisher Form");
        model.addAttribute("publisher", new Publisher());
        return "publisher/create";

    }

    @PostMapping({"/create"})
    public String createPublisher(@ModelAttribute @Valid Publisher publisher,
                                  Errors errors, Model model){
        if(errors.hasErrors()){
            model.addAttribute("title", "Add Publisher");
            return "publisher/create";
        }
        publisherService.save(publisher);
        return "redirect:";
    }
}
