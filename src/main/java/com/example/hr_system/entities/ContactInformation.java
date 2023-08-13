package com.example.hr_system.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class ContactInformation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String country;
    private String city;
    private String street_house;

    @OneToOne(cascade = CascadeType.ALL,mappedBy = "contactInformation")
    private Vacancy vacancy;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
