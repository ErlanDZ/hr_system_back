package com.example.hr_system.dto.salary;

import com.example.hr_system.enums.SalaryType;
import com.example.hr_system.enums.TypeOfEmployment;
import com.example.hr_system.enums.Valute;
import lombok.*;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class SalaryRequest {

    private SalaryType salaryType;

    private double salarySum;

    private Valute valute;

}
