package com.capgemini.domains.dto;

import com.capgemini.domains.Author;
import com.capgemini.domains.Book;

import javax.validation.constraints.NotNull;

public class AuthorBookDTO {

    @NotNull
   private Author author;

    @NotNull
   private Book book;

    public AuthorBookDTO() {
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }
}
