package com.capgemini.services;


import com.capgemini.domains.Publisher;
import com.capgemini.repositories.PublisherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PublisherService {

    @Autowired
    private PublisherRepository publisherRepository;


    /*
    retrieve all publisher
     */
    public List<Publisher> findAll(){
        return publisherRepository.findAll();
    }

    /*
    save  a publisher object
     */
    public void save(Publisher publisher){
        publisherRepository.save(publisher);
    }


    /*
    find a publisher by id
     */
    public Publisher findPublisherById(Long id) {
       Optional<Publisher> publisher = publisherRepository.findById(id);
       return publisher.get();
    }

    /*
    delete publisher  by publisher id
     */
    public void deleteById(Long id){
        publisherRepository.deleteById(id);
    }

    /*
    delete publisher by passing an object of publisher
     */

    public void delete(Publisher publisher){

        publisherRepository.delete(publisher);
    }
}
