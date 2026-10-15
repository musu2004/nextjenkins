package com.example.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.example.entity.Employee;

class EmployeeServiceTest {

    private EmployeeService employeeService;

    @BeforeEach
    void setUp() {
        employeeService = new EmployeeService();
    }

    @Test
    @DisplayName("Should add an employee successfully")
    void shouldAddEmployee() {
        Employee employee =
                new Employee(null, "Rahul", "IT", 50000);

        Employee saved = employeeService.addEmployee(employee);

        assertAll(
            () -> assertNotNull(saved.getId()),
            () -> assertEquals("Rahul", saved.getName()),
            () -> assertEquals("IT", saved.getDepartment()),
            () -> assertEquals(50000.0, saved.getSalary()),
            () -> assertEquals(1,
                    employeeService.findAllEmployees().size())
        );
    }

    @Test
    @DisplayName("Should update an existing employee")
    void shouldUpdateEmployee() {
        Employee saved = employeeService.addEmployee(
                new Employee(null, "Rahul", "IT", 50000));

        Employee updated = new Employee(
                null, "Amit", "HR", 60000);

        Optional<Employee> result =
                employeeService.updateEmployee(saved.getId(), updated);

        assertTrue(result.isPresent());

        Employee actual = result.orElseThrow();

        assertAll(
            () -> assertEquals(saved.getId(), actual.getId()),
            () -> assertEquals("Amit", actual.getName()),
            () -> assertEquals("HR", actual.getDepartment()),
            () -> assertEquals(60000.0, actual.getSalary())
        );
    }

    @Test
    @DisplayName("Should delete an existing employee")
    void shouldDeleteEmployee() {
        Employee saved = employeeService.addEmployee(
                new Employee(null, "Rahul", "IT", 50000));

        boolean deleted =
                employeeService.deleteEmployee(saved.getId());

        assertAll(
            () -> assertTrue(deleted),
            () -> assertTrue(
                    employeeService.findAllEmployees().isEmpty())
        );
    }

    @Test
    @DisplayName("Should return all employees")
    void shouldFindAllEmployees() {
        employeeService.addEmployee(
                new Employee(null, "Rahul", "IT", 50000));

        employeeService.addEmployee(
                new Employee(null, "Amit", "HR", 60000));

        List<Employee> employees =
                employeeService.findAllEmployees();

        assertAll(
            () -> assertEquals(2, employees.size()),
            () -> assertEquals("Rahul", employees.get(0).getName()),
            () -> assertEquals("Amit", employees.get(1).getName())
        );
    }

    @Test
    @DisplayName("Should find an employee by ID")
    void shouldFindEmployeeById() {
        Employee saved = employeeService.addEmployee(
                new Employee(null, "Rahul", "IT", 50000));

        Employee actual = employeeService
                .findEmployeeById(saved.getId())
                .orElseThrow();

        assertAll(
            () -> assertEquals(saved.getId(), actual.getId()),
            () -> assertEquals("Rahul", actual.getName()),
            () -> assertEquals("IT", actual.getDepartment())
        );
    }

    @Test
    @DisplayName("Should return empty when employee ID does not exist")
    void shouldReturnEmptyForMissingEmployee() {
        Optional<Employee> result =
                employeeService.findEmployeeById(999L);

        assertTrue(result.isEmpty());
    }

    @Test
    @DisplayName("Should not update a missing employee")
    void shouldNotUpdateMissingEmployee() {
        Optional<Employee> result =
                employeeService.updateEmployee(
                    999L,
                    new Employee(null, "Amit", "HR", 60000)
                );

        assertTrue(result.isEmpty());
    }

    @Test
    @DisplayName("Should not delete a missing employee")
    void shouldNotDeleteMissingEmployee() {
        boolean deleted = employeeService.deleteEmployee(999L);
        assertFalse(deleted);
    }
}