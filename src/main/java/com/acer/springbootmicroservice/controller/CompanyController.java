package com.acer.springbootmicroservice.controller;

import com.acer.springbootmicroservice.service.ICompanyService;
import com.acer.springbootmicroservice.vo.CompanyVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
public class CompanyController {
    @Autowired
    private ICompanyService companyService;

    @GetMapping(value = "/company/{aid}")
    public ResponseEntity<Object> getCompanyByAid(@PathVariable("aid") String aid) {
        CompanyVo companyVo = companyService.getCompanyByAid(aid);
        return new ResponseEntity<>(companyVo, HttpStatus.OK);
    }

    @GetMapping(value = "/companys")
    public ResponseEntity<Object> getAllCompany() {
        List<CompanyVo> companyVolist = companyService.getAllCompany();
        return new ResponseEntity<>(companyVolist, HttpStatus.OK);
    }

    @PostMapping(value = "companys")
    public ResponseEntity<Void> addCompany(@RequestBody CompanyVo company, UriComponentsBuilder builder) {
        Boolean flag = companyService.addCompany(company);
        if (!flag) {
            return new ResponseEntity<Void>(HttpStatus.CONFLICT);
        }
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(builder.path("/company/{aid}").buildAndExpand(company.getAid()).toUri());
        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
    }

    @PutMapping(value = "companys")
    public ResponseEntity<Object> updateCompany(@RequestBody CompanyVo company) {
        companyService.updateCompany(company);
        return new ResponseEntity<Object>(company, HttpStatus.OK);
    }

    @DeleteMapping(value = "/company/{aid}")
    public ResponseEntity<Void> deleteCompany(@PathVariable("aid") String aid) {
        companyService.deleteCompany(aid);
        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }
}
