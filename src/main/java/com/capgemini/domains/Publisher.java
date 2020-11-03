package com.capgemini.domains;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Publisher extends BaseEntity {

    @NotBlank(message = "Name is required")
    @Size(min = 3, max = 20, message = "The Name must be between 2 and 20 characters")
    @Column(unique = true)
    private String name;

    @OneToOne(cascade = CascadeType.ALL)
    @Valid
    @NotNull
    private Address address;

    @OneToMany(mappedBy = "publisher")
    private List<Book> books = new ArrayList<>();

    public Publisher() {
    }

    public Publisher(@NotBlank(message = "Name is required") String name, @Valid @NotNull Address address) {
        this.name = name;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }
}
