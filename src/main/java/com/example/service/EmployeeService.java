package com.example.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.entity.Employee;

@Service
public class EmployeeService {

    private final List<Employee> employees = new ArrayList<>();
    private long nextId = 10001;

   
    public Employee addEmployee(Employee employee) {
        employee.setId(nextId++);
        employees.add(employee);
        return employee;
    }

    
    public Optional<Employee> updateEmployee(
            Long id, Employee updatedEmployee) {

        return findEmployeeById(id).map(employee -> {
            employee.setName(updatedEmployee.getName());
            employee.setDepartment(updatedEmployee.getDepartment());
            employee.setSalary(updatedEmployee.getSalary());
            return employee;
        });
    }

  
    public boolean deleteEmployee(Long id) {
        return employees.removeIf(
                employee -> employee.getId().equals(id));
    }

   
    public List<Employee> findAllEmployees() {
        return List.copyOf(employees);
    }

   
    public Optional<Employee> findEmployeeById(Long id) {
        return employees.stream()
                .filter(employee -> employee.getId().equals(id))
                .findFirst();
    }
}