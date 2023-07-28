package com.example.hr_system.mapper.impl;

import com.example.hr_system.dto.employer.EmployerResponses;
import com.example.hr_system.entities.Employer;
import com.example.hr_system.mapper.EmployerMapper;
import org.springframework.stereotype.Component;
import org.webjars.NotFoundException;

import java.util.ArrayList;
import java.util.List;

@Component
public class EmployerMapperImpl implements EmployerMapper {
    @Override
    public EmployerResponses toDto(Employer employer) {
        if (employer == null) {
            new NotFoundException("employer is null!");
        }
        EmployerResponses employerResponses = new EmployerResponses();
        if (employer.getId() != null) {
            employerResponses.setId(employer.getId());
        }
        employerResponses.setId(employer.getId());
        if (employer.getImage()!=null)
            employerResponses.setImageId(employer.getImage().getId());
        employerResponses.setCompanyName(employer.getCompanyName());
        employerResponses.setAboutCompany(employer.getAboutCompany());
        employerResponses.setCountry(employer.getCountry());
        employerResponses.setCity(employer.getCity());
        employerResponses.setAddress(employer.getAddress());
        employerResponses.setEmail(employer.getEmail());
        employerResponses.setPhoneNumber(employer.getPhoneNumber());

        return employerResponses;
    }

    @Override
    public List<EmployerResponses> toDtos(List<Employer> employerList) {
        List<EmployerResponses>vacancyResponses=new ArrayList<>();
        for (Employer vacancy:employerList) {
            vacancyResponses.add(toDto(vacancy));
        }

        return vacancyResponses;
    }
}
