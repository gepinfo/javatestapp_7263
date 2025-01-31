package com.geppetto.javatestapp.repository;

import com.geppetto.javatestapp.model.Employee;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, String> , JpaSpecificationExecutor<Employee> {
    
    Page<Employee> findAll(Pageable pageable);
}