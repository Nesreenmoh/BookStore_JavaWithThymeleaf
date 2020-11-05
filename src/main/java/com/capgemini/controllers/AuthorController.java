package com.capgemini.controllers;

import com.capgemini.domains.Author;
import com.capgemini.domains.Book;
import com.capgemini.domains.dto.AuthorBookDTO;
import com.capgemini.services.AuthorService;
import com.capgemini.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/authors")
public class AuthorController {

    @Autowired
    private AuthorService authorService;

    @Autowired
    private BookService bookService;

    @RequestMapping({"", "/", "index", "index.html"})
    public String index(Model model) {
        model.addAttribute("title", "Authors List");
        model.addAttribute("authors", authorService.findAll());
        return "author/index";
    }

    @GetMapping({"/create"})
    public String renderCreateForm(Model model) {
        model.addAttribute("title", "add author");
        model.addAttribute("author", new Author());
        return "author/create";
    }

    @PostMapping({"/create"})
    public String createAuthor(@ModelAttribute @Valid Author author,
                               Errors errors, Model model) {
        if (!errors.hasErrors()) {
            authorService.save(author);
            return "redirect:";
        } else {
            model.addAttribute("title", "add author");
            return "author/create";
        }

    }

    @GetMapping({"/update/{id}"})
    public String updateAuthorForm(@PathVariable("id") Long id, Model model) {
        Author author = authorService.findById(id);
        if (author != null) {
            model.addAttribute("author", author);
            model.addAttribute("title", "Update Author No: " + author.getId());
            return "author/update";
        } else {
            model.addAttribute("title", "update author");
            return "author/index";
        }
    }

    @PostMapping({"/update/{id}"})
    public String updateAuthor(@PathVariable("id") Long id, @ModelAttribute @Valid Author author,
                               Errors errors, Model model) {
        Author updatedAuthor = authorService.findById(id);
        if (!errors.hasErrors()) {
            updatedAuthor.setAddress(author.getAddress());
            updatedAuthor.setFirstName(author.getFirstName());
            updatedAuthor.setLastName(author.getLastName());
            authorService.update(updatedAuthor);
            model.addAttribute("authors", authorService.findAll());
            return "author/index";
        } else {
            model.addAttribute("title", "update author");
            return "redirect:/" + author.getId();
        }
    }

    @GetMapping({"/delete/{id}"})
    public String deleteAuthor(@PathVariable("id") Long id, Model model, RedirectAttributes redirectAttributes) {
        try{
            if (id == null) {
                model.addAttribute("message", "Sorry something went wrong, Please try again");
                return "notFound";
            } else {
                Author author = authorService.findById(id);
                if (author == null) {
                    model.addAttribute("message", "Sorry This author is not found");
                    return "notFound";
                }
                else{
                    authorService.delete(author);
                    model.addAttribute("authors", authorService.findAll());
                    model.addAttribute("title", "Authors List");
                    redirectAttributes.addFlashAttribute("message", "This Author has been successfully deleted!");
                    return "redirect:/authors/index";
                }
            }
        }catch(Exception ex){
            System.out.println("Error: " + ex.getMessage());
            model.addAttribute("authors", authorService.findAll());
            model.addAttribute("title", "Authors List");
            return "author/index";
        }
    }

    /*
    Add book to the author
     */
    @GetMapping({"/addbook/{id}"})
    public String addBooksToAuthorForm(@PathVariable("id") Long id, Model model) {

        if (id == null) {
            // validation id error
            model.addAttribute("authors", authorService.findAll());
            return "author/index";
        }
        Author author = authorService.findById(id);
        if (author == null) {
            // error page showed
            model.addAttribute("authors", authorService.findAll());
            return "author/index";
        } else {
            model.addAttribute("title", "Add Books to : " + author.getFirstName() + " " + author.getLastName());
            model.addAttribute("books", bookService.findAll());
            model.addAttribute("author", author);
            model.addAttribute("authorbooks", author.getBooks());
            AuthorBookDTO dto = new AuthorBookDTO();
            dto.setAuthor(author);
            model.addAttribute("authorBookDTO", dto);
            return "author/addbook";
        }
    }

    @PostMapping("/addbook/{id}")
    public String addBooksToAuthor(@PathVariable("id") Long id, Model model, @ModelAttribute @Valid AuthorBookDTO authorBookDTO, Errors errors) {
        if (!errors.hasErrors()) {
            Author author = authorBookDTO.getAuthor();
            Book book = authorBookDTO.getBook();
            if (!author.getBooks().contains(book)) {
                author.addBook(book);
                authorService.save(author);
                model.addAttribute("authors", authorService.findAll());
                return "author/index";
            }
            model.addAttribute("books", bookService.findAll());
            model.addAttribute("author", author);
            model.addAttribute("authorbooks", author.getBooks());
            AuthorBookDTO dto = new AuthorBookDTO();
            dto.setAuthor(author);
            model.addAttribute("authorBookDTO", dto);

            return "author/addbook";
        }
        model.addAttribute("authors", authorService.findAll());
        return "author/index";

    }

    @GetMapping({"/details/{id}"})
    public String authorDetails(@PathVariable("id") Long id, Model model) {
        if (id == null) {
            model.addAttribute("title", "NotFound page");
            model.addAttribute("message", "Sorry there is something went wrong!");
            return "notFound";
        } else {
            Author author = authorService.findById(id);
            if (author == null) {
                model.addAttribute("message","No details for this author");
                return "notFound";
            }
            if(author.getBooks().size()<=0){
                model.addAttribute("message","No Books Available");
            }
            model.addAttribute("author", author);
            model.addAttribute("title", "Author Details");
            model.addAttribute("NoOfBooks", author.getBooks().size());
            return "author/details";
        }
    }
}
