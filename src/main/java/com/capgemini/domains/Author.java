package com.capgemini.domains;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Author extends BaseEntity {

    @NotBlank(message = "First name is required")
    @Size(min = 3, max = 10, message = "First Name must be between 2 and 10 characters")
    private String firstName;

    @NotBlank(message = "Last name is required")
    @Size(min = 3, max = 15, message = "Last Name must be between 3 and 15 characters")
    private String lastName;

    @OneToOne(cascade = CascadeType.ALL)
    @Valid
    @NotNull
    private Address address;

    private String displayName;

    @ManyToMany
    private final List<Book> books= new ArrayList<>();

    public Author(){}

    public Author(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    // add book
    public void addBook(Book book){
        this.books.add(book);
    }
    //get book
    public List<Book> getBooks() {
        return books;
    }

    public String getDisplayName() {
        return firstName + " " +lastName;
    }
}
