package com.capgemini.controllers;


import com.capgemini.domains.Book;
import com.capgemini.domains.Publisher;
import com.capgemini.services.BookService;
import com.capgemini.services.PublisherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/publishers")
public class PublisherController {

    @Autowired
    private PublisherService publisherService;

    @Autowired
    private BookService bookService;


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
        model.addAttribute("publishers", publisherService.findAll());
        return "publisher/index";
    }

    @GetMapping({"/update/{id}"})
    public String updateForm(@PathVariable("id") Long id, Model model) {
        if (id == null) {
            model.addAttribute("title", "Error");
            model.addAttribute("message", "Sorry something went wrong!, Please try again!");
            return "notFound";
        } else {
            Publisher publisher = publisherService.findPublisherById(id);
            if (publisher == null) {
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
                                  Errors errors) {

        Publisher updatedPublisher = publisherService.findPublisherById(id);
        if (updatedPublisher == null) {
            model.addAttribute("title", "Not Found");
            model.addAttribute("message", "Sorry This Publisher is not found!");
            return "notFound";
        } else {
            if (errors.hasErrors()) {
                model.addAttribute("title", "update Publisher");
                return "redirect:/" + id;
            }
            updatedPublisher.setAddress(publisher.getAddress());
            updatedPublisher.setName(publisher.getName());
            publisherService.save(updatedPublisher);
            model.addAttribute("publishers", publisherService.findAll());
            return "publisher/index";
        }
    }

    /*
    show details of a specific publisher
     */

    @GetMapping({"/details/{id}"})
    public String publisherDetails(@PathVariable("id") Long id, Model model){
       if(id==null){
           model.addAttribute("message", "Sorry something went wrong, Please try again");
           return "notFound";
       }
       else {
           Publisher publisher = publisherService.findPublisherById(id);
           if(publisher==null){
               model.addAttribute("message", "Sorry This publisher is not found");
               return "notFound";
           }
           else {
               List<Book> books = publisher.getBooks();
               model.addAttribute("publisher", publisher);
               model.addAttribute("books" ,books);
               model.addAttribute("title", "Publisher Details");
               return "publisher/details";
           }
       }
    }




    /*
     delete a publisher
     */
    @GetMapping({"/delete/{id}"})
    public String deletePublisher(@PathVariable("id") Long id, Model model,
                                  RedirectAttributes redirectAttributes) {
        try {
            if (id == null) {
                model.addAttribute("message", "Sorry something went wrong, Please try again");
                return "notFound";
            } else {
                Publisher publisher = publisherService.findPublisherById(id);
                if (publisher == null) {
                    model.addAttribute("message", "Sorry This publisher is not found");
                    return "notFound";
                }
                else{
                    for(int i=0;i<bookService.findAll().size();i++){
                        if (bookService.findAll().get(i).getPublisher().getId()==publisher.getId()) {
                            model.addAttribute("publishers", publisherService.findAll());
                            redirectAttributes.addFlashAttribute("message", "This Publisher has books, cannot be deleted!");
                            return "redirect:/publishers/index";
                        }
                    }
                    publisherService.delete(publisher);
                    model.addAttribute("publishers", publisherService.findAll());
                    model.addAttribute("title", "Publishers List");
                    redirectAttributes.addFlashAttribute("message", "This Publisher has been successfully deleted!");
                    return "redirect:/publishers/index";
                }

            }
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            model.addAttribute("publishers", publisherService.findAll());
            model.addAttribute("title", "Publishers List");
            return "publisher/index";
        }
    }
}
