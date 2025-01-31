package com.geppetto.javatestapp.dao.daoimpl;

import com.geppetto.javatestapp.repository.EmployeeRepository;
import com.geppetto.javatestapp.dao.EmployeeDao;

import com.geppetto.javatestapp.model.Employee;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import java.util.Optional;
import org.springframework.stereotype.Service;

/**
* Implementation of the {@link EmployeeDao} interface.
* Provides methods to interact with the {@link EmployeeRepository} for CRUD operations on {@link Employee } entities.
*/
@Service
public class EmployeeDaoImpl implements EmployeeDao {

    private final EmployeeRepository employeeRepository;
    /**
     * Constructs a new {@code EmployeeDaoImpl} with the specified repository.
     *
     * @param employeeRepository The repository used for accessing {@link Employee} entities. Must not be {@code null}.
     */
    public EmployeeDaoImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    /**
     * Creates new employee.
     *
     * @param employee The {@link Employee} entity to create. Must not be {@code null}.
     * @return The created {@link Employee} entity.
     */
    @Override
    public Employee createEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }


     /**
     * Retrieves all employee from the repository.
     *
     * @return A list of all {@link Employee} entities.
     */
    @Override
    public Page<Employee> getAllEmployee(Pageable pageable) {
        return employeeRepository.findAll(pageable);
    }


    /**
     * Retrieves employee by its ID for update purposes.
     *
     * @param id The ID of the employee to retrieve. Must not be {@code null}.
     * @return An {@link Optional} containing the employee if found, or an empty {@code Optional} if not.
     */
    @Override
    public Optional<Employee> updateEmployee(String id) {
        return employeeRepository.findById(id);
    }


    /**
     * Deletes employee by its ID.
     *
     * @param id The ID of the employee to delete. Must not be {@code null}.
     */
    @Override
    public void deleteEmployee(String id) {
        employeeRepository.deleteById(id);
    }


    /**
     * Retrieves employee by its ID.
     *
     * @param id The ID of the employee to retrieve. Must not be {@code null}.
     * @return An {@link Optional} containing the employee if found, or an empty {@code Optional} if not.
     */
    @Override
    public Optional<Employee> getEmployeeById(String id) {
        return employeeRepository.findById(id);
    }


}


