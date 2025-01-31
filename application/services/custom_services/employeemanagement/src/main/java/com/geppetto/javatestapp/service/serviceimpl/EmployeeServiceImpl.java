package com.geppetto.javatestapp.service.serviceimpl;

import com.geppetto.javatestapp.dao.EmployeeDao;
import com.geppetto.javatestapp.dto.EmployeeDto;
import com.geppetto.javatestapp.exception.EntityNotFoundException;
import com.geppetto.javatestapp.model.Employee;
import com.geppetto.javatestapp.service.EmployeeService;
import com.geppetto.javaproj044.repository.PersonsRepository;
import org.springframework.data.domain.Page;
import com.geppetto.javaproj04.repository.PersonsRepository;
import com.geppetto.javatestapp.util.ConstructQuery;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import com.geppetto.javaproj03.repository.PersonsRepository;
import java.util.List;
import com.geppetto.javaproj044.util.ConstructQuery;
import org.springframework.data.domain.PageRequest;
import java.util.Map;
import com.geppetto.javaproj02.repository.PersonsRepository;
import com.geppetto.javaproj04.util.ConstructQuery;
import java.util.stream.Collectors;
import com.geppetto.javatestapp.repository.EmployeeRepository;
import com.geppetto.javaproj03.util.ConstructQuery;
import com.geppetto.javaproj02.util.ConstructQuery;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;


/**
* Implementation of the {@link EmployeeService} interface.
* Provides services related to Employee, including CRUD operations and file uploads/downloads.
*/

@Service
@Slf4j
public class EmployeeServiceImpl implements EmployeeService {


    /**
     * Constructs a {@code EmployeeServiceImpl} with the specified DAO.
     *
     * @param employeeDao The DAO for accessing the data.
     */
  private final EmployeeDao employeeDao;
  private final EmployeeRepository employeeRepository;
  private final ConstructQuery constructQuery;

  public EmployeeServiceImpl(EmployeeDao  employeeDao, EmployeeRepository  employeeRepository,ConstructQuery constructQuery) {
    this.employeeDao =  employeeDao;
    this.employeeRepository =  employeeRepository;
    this.constructQuery = constructQuery;
  }
    
    /**
     * Creates new employee.
     *
     * @param employeeDto The {@link EmployeeDto} to be created.
     * @return The created {@link EmployeeDto}.
     */
  @Override
  public EmployeeDto  createEmployee(EmployeeDto employeeDto) {
    log.info("Enter into createEmployee method");
    Employee employee = new Employee();
  BeanUtils.copyProperties(employeeDto, employee);
  Employee createdEmployee= employeeDao.createEmployee(employee);
  BeanUtils.copyProperties(createdEmployee, employeeDto);
  log.info("Exit from createEmployee method");
  return employeeDto;
  }
    
    /**
     * Retrieves all employee.
     *
     * @return A list of {@link EmployeeDto} representing all employee.
     */
  @Override
  public Page<EmployeeDto>  getAllEmployee(int page, int size) {
    log.info("Enter into getAllEmployee method");
    Pageable pageable = (Pageable) PageRequest.of(page, size);
    Page<Employee> employeePage =employeeDao.getAllEmployee(pageable);
    Page<EmployeeDto>employeeDtoPage = employeePage.map(employee -> {
    EmployeeDto employeeDto = EmployeeDto.builder().build();
    BeanUtils.copyProperties(employee, employeeDto);
    return employeeDto;
    });
    log.info("Exit from getAllemployeemethod");
    return employeeDtoPage;
  }
    
    /**
     * Updates existing employee.
     *
     * @param employeeDto The {@link EmployeeDto} containing updated information.
     * @return The updated {@link EmployeeDto}.
     * @throws EntityNotFoundException If no employee with the specified ID is found.
     */
  @Override
  public EmployeeDto  updateEmployee(EmployeeDto employeeDto) {
    log.info("Enter into updateEmployee method");
    return employeeDao.getEmployeeById(employeeDto.getId())
    .map(existingEmployee -> {
      BeanUtils.copyProperties(employeeDto, existingEmployee);
      employeeDao.createEmployee(existingEmployee);
      log.info("Exit from updateEmployee method");
      return employeeDto;
  })
  .orElseThrow(() -> new EntityNotFoundException("Data not found for update with ID: " + employeeDto.getId()));
  }
    
    /**
     * Deletes employee by ID.
     *
     * @param id The ID of the employee to delete.
     * @return A message indicating the result of the deletion.
     * @throws EntityNotFoundException If no employee with the specified ID is found.
     */
  @Override
  public String  deleteEmployee(String id) {
    log.info("Enter into deleteEmployee method");
    return employeeDao.getEmployeeById(id)
     .map(employee -> {
     employeeDao.deleteEmployee(id);
  log.info("Exit from deleteEmployee method");
  return "Data Deleted Successfully";
  })
  .orElseThrow(() -> new EntityNotFoundException("No entry found with ID: " + id + ". Unable to delete."));

  }
    
    /**
     * Searches for employee based on provided parameters.
     *
     * @param allParams A map of search parameters.
     * @return A list of {@link EmployeeDto} matching the search parameters.
     */
  @Override
  public List<EmployeeDto>  searchEmployee(Map<String, String> allParams) {
    log.info("Enter into searchEmployee method");
    Specification<Employee> specification = constructQuery.constructSearchQuery(allParams);
  List<Employee> results = usersRepository.findAll(specification);
  List<EmployeeDto> employeeDtos = results.stream()
    .map(employee -> {
      EmployeeDto dto = new EmployeeDto();
      BeanUtils.copyProperties(employee, dto);
      return dto;
    })
    .collect(Collectors.toList());
  log.info("Exit from searchEmployee method");
  return employeeDtos;
  }
    
    /**
     * Retrieves employee by its ID.
     *
     * @param id The ID of the employee to retrieve. Must not be {@code null}.
     * @return The employee data transfer object associated with the specified ID.
     * @throws EntityNotFoundException If no employee with the specified ID is found.
     */
  @Override
  public EmployeeDto  getEmployeeById(String id) {
    log.info("Enter into getEmployeeById method");
    return employeeDao.getEmployeeById(id)
    .map(employee -> {
      EmployeeDto employeeDto = new EmployeeDto();
      BeanUtils.copyProperties(employee, employeeDto);
      log.info("Exit from getEmployeeById method");
      return employeeDto;
    })
        .orElseThrow(() -> new EntityNotFoundException("Data not found for ID: " + id));
  }
    

}
