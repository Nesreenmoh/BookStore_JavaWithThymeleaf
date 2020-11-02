package com.capgemini.services;

import com.capgemini.domains.Author;
import com.capgemini.repositories.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.List;
import java.util.Optional;

@Service
public class AuthorService {

    @Autowired
    private AuthorRepository authorRepository;



    public List<Author> findAll(){
        return authorRepository.findAll();
    }


    public Author findById(Long id){
        Optional<Author> author= authorRepository.findById(id);
        return author.get();
    }

   public void update(Author author){
        authorRepository.save(author);
   }
    public void save(Author author){
        authorRepository.save(author);
    }

    public void delete(Author author){
        authorRepository.delete(author);
    }


    public void deleteById(Long id){
        authorRepository.deleteById(id);
    }
}
