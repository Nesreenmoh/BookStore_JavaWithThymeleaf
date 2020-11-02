package com.capgemini.domains;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
public class Category  extends  BaseEntity{

    @NotBlank(message = "Name is required")
    @Column(unique = true)
    @Size(min = 3, max = 20, message = "The name must be between 3 and 20 characters")
    private String name;

    @Size(max = 250, message = "The description must not be more than 250 characters")
    private String description;

    public Category() {
    }

    public Category(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
