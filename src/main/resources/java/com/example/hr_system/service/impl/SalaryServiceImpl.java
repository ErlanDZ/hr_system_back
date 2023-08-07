package com.example.hr_system.service.impl;

import com.example.hr_system.dto.salary.SalaryRequest;
import com.example.hr_system.dto.salary.SalaryResponse;
import com.example.hr_system.entities.Salary;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class SalaryServiceImpl {



    public Salary convertToEntity(SalaryRequest salaryRequest){
        if (salaryRequest == null) {
            return null;
        }
        Salary salary = new Salary();
        salary.setTypeOfEmployment(salaryRequest.getTypeOfEmployment());
        salary.setValute(salaryRequest.getValute());
        salary.setSalary(salaryRequest.getSalary());
        return salary;
    }


}
