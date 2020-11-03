package com.capgemini.controllers;

import com.capgemini.domains.Book;
import com.capgemini.domains.Category;
import com.capgemini.services.BookService;
import com.capgemini.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private BookService bookService;

    @RequestMapping({"", "/", "/index", "/index.html"})
    public String index(Model model) {

        model.addAttribute("title", "Categories List");
        model.addAttribute("categories", categoryService.findAll());
        return "category/index";
    }

    @GetMapping({"/create"})
    public String renderCreateForm(Model model) {
        model.addAttribute("title", "Create Category");
        model.addAttribute("category", new Category());
        return "category/create";
    }

    @PostMapping({"/create"})
    public String createCategory(@ModelAttribute @Valid Category category,
                                 Errors errors, Model model) {
        if (!errors.hasErrors()) {
            categoryService.save(category);
            return "redirect:";
        }
        else {
            model.addAttribute("title", "add category");
            return "category/create";
        }
    }

    @GetMapping({"/update/{id}"})
    public String updateCategoryForm(@PathVariable("id") Long id, Model model) {
        if (id != null) {
            Category category = categoryService.findById(id);
            if (category != null) {
                model.addAttribute("category", category);
                model.addAttribute("title", "Update Category");
                return "category/update";
            }
        }
        model.addAttribute("categories", categoryService.findAll());
        return "category/index";
    }

    @PostMapping({"/update/{id}"})
    public String updateCategory(@PathVariable("id") Long id, Model model, Errors errors,
                                 @ModelAttribute @Valid Category category) {
        Category updatedCategory = categoryService.findById(id);
        if (updatedCategory != null) {
            if (!errors.hasErrors()) {
                updatedCategory.setName(category.getName());
                updatedCategory.setDescription(category.getDescription());
                categoryService.save(updatedCategory);
                model.addAttribute("categories", categoryService.findAll());
                return "category/index";
            }
            return "redirect:/"+id;
        }
        model.addAttribute("categories", categoryService.findAll());
        return "category/index";
    }

    @GetMapping({"/delete/{id}"})
    public String deleteCategory(@PathVariable("id") Long id, Model model){
        if(id!=null){
            Category category = categoryService.findById(id);
            if(category!=null){
                categoryService.delete(category);
            }
            else {
                model.addAttribute("message", "Sorry this category is not found");
                 return "notFound";
            }
        }
        model.addAttribute("message", "Sorry something went wrong, Please try again");
        return "notFound";
    }

    @GetMapping({"/details/{id}"})
    public String details(@PathVariable("id") Long id, Model model){
        if(id==null){
            model.addAttribute("message", "Sorry something went wrong!");
            return "notFound";
        }
        Category category = categoryService.findById(id);
        if(category==null){
            model.addAttribute("title","Category not found");
            model.addAttribute("message", "Sorry The Category is not found!");
            return "notFound";
        }
        List<Book> books = bookService.booksUnderCategory(category.getName());
        if(books.isEmpty()){
            model.addAttribute("title", "Category is empty");
            model.addAttribute("message", "Sorry This Category has no books!");
            return "notFound";
        }

        model.addAttribute("title", "Books under a Category");
        model.addAttribute("category", category);
        model.addAttribute("books", books);
        return "category/booksCategory";
    }
}
