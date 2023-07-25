package com.example.hr_system.dto.salary;

import com.example.hr_system.enums.TypeOfEmployment;
import com.example.hr_system.enums.Valute;
import lombok.*;

@Data
public class SalaryRequest {

    private TypeOfEmployment typeOfEmployment;

    private Double salary;

    private Valute valute;

}
