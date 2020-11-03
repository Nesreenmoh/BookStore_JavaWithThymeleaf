package com.capgemini.controllers;


import com.capgemini.domains.Publisher;
import com.capgemini.services.PublisherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/publishers")
public class PublisherController {

    @Autowired
    private PublisherService publisherService;


    @RequestMapping({"", "/", "index", "index.html"})
    public String index(Model model) {
        model.addAttribute("title", "Publishers List");
        model.addAttribute("publishers", publisherService.findAll());
        return "publisher/index";
    }

    @GetMapping({"/create"})
    public String renderCreatePublisherForm(Model model) {
        model.addAttribute("title", "Publisher Form");
        model.addAttribute("publisher", new Publisher());
        return "publisher/create";

    }

    @PostMapping({"/create"})
    public String createPublisher(@ModelAttribute @Valid Publisher publisher,
                                  Errors errors, Model model) {
        if (errors.hasErrors()) {
            model.addAttribute("title", "Add Publisher");
            return "publisher/create";
        }
        publisherService.save(publisher);
        return "redirect:";
    }

    @GetMapping({"/update/{id}"})
    public String updateForm(@PathVariable("id") Long id,Model model){
        if(id==null){
            model.addAttribute("title", "Error");
            model.addAttribute("message", "Sorry something went wrong!, Please try again!");
            return "notFound";
        }
        else{
            Publisher publisher= publisherService.findPublisherById(id);
            if(publisher==null){
                model.addAttribute("title", "Not Found");
                model.addAttribute("message", "Sorry Publisher is not found!");
                return "notFound";
            }
            model.addAttribute("title", "Update Publisher");
            model.addAttribute("publisher", publisher);
            return "publisher/update";
        }
    }

    @PostMapping({"/update/{id}"})
    public String updatePublisher(@PathVariable("id") Long id,
                                  @ModelAttribute @Valid Publisher publisher, Model model,
                                  Errors errors){

        Publisher updatedPublisher= publisherService.findPublisherById(id);
        if(updatedPublisher==null){
            model.addAttribute("title", "Not Found");
            model.addAttribute("message", "Sorry This Publisher is not found!");
            return "notFound";
        }
        else{
            if(errors.hasErrors()){
                model.addAttribute("title", "update Publisher");
                return "redirect:/"+id;
            }
            updatedPublisher.setAddress(publisher.getAddress());
            updatedPublisher.setName(publisher.getName());
            publisherService.save(updatedPublisher);
            model.addAttribute("publishers", publisherService.findAll());
            return "publisher/index";
        }
    }
}
