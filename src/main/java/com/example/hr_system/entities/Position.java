package com.example.hr_system.entities;


import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Position {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH,CascadeType.DETACH, CascadeType.PERSIST})
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToMany(cascade = {CascadeType.ALL}, mappedBy = "position")
    private List<Vacancy> vacancies;

}
