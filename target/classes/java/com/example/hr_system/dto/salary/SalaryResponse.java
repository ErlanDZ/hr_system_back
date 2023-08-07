package com.example.hr_system.dto.salary;

import com.example.hr_system.enums.TypeOfEmployment;
import com.example.hr_system.enums.Valute;
import lombok.*;
import org.springframework.stereotype.Component;

@Data
public class SalaryResponse {

    private Long id;

    private TypeOfEmployment typeOfEmployment;

    private Double salary;

    private Valute valute;

}
