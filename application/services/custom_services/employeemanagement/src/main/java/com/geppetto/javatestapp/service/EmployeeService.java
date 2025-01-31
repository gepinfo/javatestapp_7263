package com.geppetto.javatestapp.service;

import com.geppetto.javatestapp.dto.EmployeeDto;
import java.util.List;
import java.util.Map;
import org.springframework.data.domain.Page;

public interface EmployeeService {

    EmployeeDto  createEmployee(EmployeeDto employeeDto);

    Page<EmployeeDto>  getAllEmployee(int page, int size);

    EmployeeDto  updateEmployee(EmployeeDto employeeDto);

    String  deleteEmployee(String id);

    List<EmployeeDto>  searchEmployee(Map<String, String> allParams);

    EmployeeDto  getEmployeeById(String id);

}
