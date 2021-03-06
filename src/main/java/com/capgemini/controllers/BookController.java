package com.capgemini.controllers;

import com.capgemini.domains.Book;
import com.capgemini.domains.Publisher;
import com.capgemini.services.AuthorService;
import com.capgemini.services.BookService;
import com.capgemini.services.CategoryService;
import com.capgemini.services.PublisherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private PublisherService publisherService;

    @Autowired
    private AuthorService authorService;

    @RequestMapping({"", "/", "index", "index.html"})
    public String index(Model model) {
        model.addAttribute("title", "Books List");
        model.addAttribute("books", bookService.findAll());
        return "book/index";
    }

    @GetMapping({"/create"})
    public String renderCreateForm(Model model) {
        model.addAttribute("title", "add book");
        model.addAttribute("book", new Book());
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("publishers", publisherService.findAll());
        return "book/create";
    }

    @PostMapping({"/create"})
    public String createBook(@ModelAttribute @Valid Book book,
                             Errors errors, Model model) {
        if (!errors.hasErrors()) {
            bookService.save(book);
            Publisher publisher = book.getPublisher();
            publisher.getBooks().add(book);
            publisherService.save(publisher);
            return "redirect:";
        } else {
            model.addAttribute("title", "add book");
            model.addAttribute("categories", categoryService.findAll());
            model.addAttribute("publishers", publisherService.findAll());
            return "book/create";
        }

    }

    @GetMapping({"/update/{id}"})
    public String updateBookForm(@PathVariable("id") Long id, Model model) {
        Book book = bookService.findById(id);
        if (book != null) {
            model.addAttribute("book", book);
            model.addAttribute("title", "Update Author No: " + book.getId());
            model.addAttribute("categories", categoryService.findAll());
            model.addAttribute("currentPublisher", book.getPublisher().getName());
            System.out.println(book.getPublisher().getName());
            model.addAttribute("currentCategory", book.getCategory().getName());
            System.out.println(book.getCategory().getName());
            model.addAttribute("publishers", publisherService.findAll());
            return "book/update";
        } else {
            model.addAttribute("books", bookService.findAll());
            return "book/index";
        }
    }


    @PostMapping({"/update/{id}"})
    public String updateBook(@PathVariable("id") Long id, @ModelAttribute @Valid Book book,
                             Errors errors, Model model) {
        Book updatedBook = bookService.findById(id);
        if (!errors.hasErrors()) {
            updatedBook.setIsbn(book.getIsbn());
            updatedBook.setTitle(book.getTitle());
            updatedBook.setPublishedYear(book.getPublishedYear());
            updatedBook.setCategory(book.getCategory());
            updatedBook.setPublisher(book.getPublisher());
            bookService.save(updatedBook);
            Publisher publisher = book.getPublisher();
            publisher.getBooks().add(book);
            publisherService.save(publisher);
            model.addAttribute("books", bookService.findAll());
            return "book/index";
        } else {
            model.addAttribute("title", "update book");
            return "redirect:/" + book.getId();
        }
    }

    @GetMapping({"/delete/{id}"})
    public String deleteBook(@PathVariable("id") Long id, Model model, RedirectAttributes redirectAttributes) {
       try{
           if (id == null) {
               model.addAttribute("message", "Sorry something went wrong, Please try again");
               return "notFound";
           } else {
               Book book = bookService.findById(id);
               if (book == null) {
                   model.addAttribute("message", "Sorry This book is not found");
                   return "notFound";
               }
               else{
                   for(int i=0;i<authorService.findAll().size();i++){
                       if (authorService.findAll().get(i).getBooks().get(i).getId()==book.getId()) {
                           model.addAttribute("books", bookService.findAll());
                           redirectAttributes.addFlashAttribute("message", "This book has Authors, cannot be deleted!");
                           return "redirect:/books/index";
                       }
                   }
                   bookService.delete(book);
                   model.addAttribute("books", bookService.findAll());
                   model.addAttribute("title", "Books List");
                   redirectAttributes.addFlashAttribute("message", "This Book has been successfully deleted!");
                   return "redirect:/books/index";
               }
           }
       } catch (Exception ex){
           System.out.println("Error: " + ex.getMessage());
           model.addAttribute("books", bookService.findAll());
           model.addAttribute("title", "Books List");
           return "book/index";
       }
    }


    /*
    show details of a book
     */
    @GetMapping({"/details/{id}"})
    public String bookDetails(@PathVariable("id") Long id, Model model) {
        if (id == null) {
            model.addAttribute("title", "book not found");
            model.addAttribute("message", "Sorry somthing went wrong! Please try again");
            return "notFound";
        } else {
            Book book = bookService.findById(id);
            if (book == null) {
                model.addAttribute("title", "book not found");
                model.addAttribute("message", "The book does not found");
                return "notFound";
            }
            model.addAttribute("title", "Book details");
            model.addAttribute("book", book);
            return "book/details";
        }

    }

}
