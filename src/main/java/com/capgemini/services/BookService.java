package com.capgemini.services;


import com.capgemini.domains.Book;
import com.capgemini.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;


    public List<Book> findAll(){
        return bookRepository.findAll();
    }


    public Book findById(Long id){
        Optional<Book> returnedBook = bookRepository.findById(id);
        return returnedBook.get();
    }

    public void save(Book book){
        bookRepository.save(book);
    }

    public void deleteById(Long id){
        bookRepository.deleteById(id);
    }

    public void delete(Book book){
        bookRepository.delete(book);
    }


    /*
    return list of books under a category name
     */
    public List<Book> booksUnderCategory(String name){
        return bookRepository.findAll()
                .stream()
                .filter(book -> book.getCategory().getName().contains(name))
                .collect(Collectors.toList());

    }
}
