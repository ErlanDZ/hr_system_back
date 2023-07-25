package com.example.hr_system.mapper.impl;


import com.example.hr_system.dto.salary.SalaryResponse;
import com.example.hr_system.entities.Salary;
import com.example.hr_system.mapper.SalaryMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@AllArgsConstructor

public class SalaryMapperImpl implements SalaryMapper {
    @Override
    public SalaryResponse toDto(Salary salary) {
        if (salary == null) {
            return null;
        }
        SalaryResponse salaryResponse = new SalaryResponse();
        salaryResponse.setId(salary.getId());
        salaryResponse.setValute(salary.getValute());
        salaryResponse.setSalary(salary.getSalary());
        salaryResponse.setTypeOfEmployment(salary.getTypeOfEmployment());
        return salaryResponse;
    }

    @Override
    public List<SalaryResponse> toDtos(List<Salary> salaries) {
        List<SalaryResponse> salaryResponses = new ArrayList<>();
        for (Salary salary : salaries) {
            salaryResponses.add(toDto(salary));
        }
        return salaryResponses;
    }
}
