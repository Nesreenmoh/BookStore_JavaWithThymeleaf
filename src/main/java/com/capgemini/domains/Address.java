package com.capgemini.domains;

import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;

@Entity
public class Address extends BaseEntity {

    @NotBlank(message = "Address is required")
    private String country;
    @NotBlank(message = "Address is required")
    private String city;
    
    private String zipCode;

    public Address(){}
    public Address(String country, String city, String zipCode) {
        this.country = country;
        this.city = city;
        this.zipCode = zipCode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }
}
