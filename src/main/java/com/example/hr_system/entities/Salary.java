package com.example.hr_system.entities;

import com.example.hr_system.enums.TypeOfEmployment;
import com.example.hr_system.enums.Valute;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
@Table(name = "salaries")
public class Salary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private TypeOfEmployment typeOfEmployment;

    private Double salary;

    private Valute valute;

    @OneToOne(cascade = CascadeType.ALL,mappedBy = "salary")
    private Vacancy vacancy;
}
