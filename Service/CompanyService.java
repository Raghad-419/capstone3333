package com.example.capston3.Service;

import com.example.capston3.ApiResponse.ApiException;
import com.example.capston3.DTO.CompanyDTO;
import com.example.capston3.Model.Admin;
import com.example.capston3.Model.Company;
import com.example.capston3.Repository.AdminRepository;
import com.example.capston3.Repository.CompanyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CompanyService {
    private final CompanyRepository companyRepository;
    private final AdminRepository adminRepository;

    public List<CompanyDTO> getCompanies(){
        List<Company> companies = companyRepository.findAll();
        List<CompanyDTO> companyDTOS= new ArrayList<>();

        for(Company company : companies){
            CompanyDTO companyDTO = new CompanyDTO(company.getName(),company.getEmail(),company.getContactInfo(),company.getIsApproved());
            companyDTOS.add(companyDTO);
        }
        return companyDTOS;
    }



    public void addCompany(Company company){
        companyRepository.save(company);
    }


    public void updateCompany(Integer id,Company company){
        Company oldCompany=companyRepository.findCompanyById(id);
        if(oldCompany==null){
            throw new ApiException("Company not found");
        }
        oldCompany.setName(company.getName());
        oldCompany.setEmail(company.getEmail());
        oldCompany.setPassword(company.getPassword());
        oldCompany.setContactInfo(company.getContactInfo());
        oldCompany.setIsApproved(company.getIsApproved());

        companyRepository.save(oldCompany);

    }


    public void deleteCompany(Integer id){
        Company company=companyRepository.findCompanyById(id);
        if(company==null){
            throw new ApiException("Company not found");
        }
        companyRepository.delete(company);
    }



}
