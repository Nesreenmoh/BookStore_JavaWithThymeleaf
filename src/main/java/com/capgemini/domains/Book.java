package com.capgemini.domains;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Book extends BaseEntity {

    @NotBlank(message = "first name is required")
    @Size(min = 2, max = 80, message = "The book title must be between 2 and 80 characters")
    private String title;

    @NotBlank(message = "Published Year is required")
    private String publishedYear;
    @NotBlank(message = "ISBN is required")
    private String isbn;

    @ManyToMany(mappedBy = "books")
    private final List<Author> authors= new ArrayList<>();

    @ManyToOne
    @NotNull(message = "Category is required")
    private Category category;

    @ManyToOne
    @NotNull(message = "Publisher is required")
    private Publisher publisher;


    public Book(){}

    public Book(String title, String publishedYear, String isbn, Category category) {
        this.title = title;
        this.publishedYear = publishedYear;
        this.isbn = isbn;
        this.category=category;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPublishedYear() {
        return publishedYear;
    }

    public void setPublishedYear(String publishedYear) {
        this.publishedYear = publishedYear;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public List<Author> getAuthors() {
        return authors;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }


    public Publisher getPublisher() {
        return publisher;
    }

    public void setPublisher(Publisher publisher) {
        this.publisher = publisher;
    }
}
