package com.example.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.entity.Employee;
import com.example.exception.EmployeeNotFoundException;
import com.example.service.EmployeeService;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

	private final EmployeeService employeeService;
	public EmployeeController(EmployeeService employeeService) {
		this.employeeService = employeeService;
	}


	@PostMapping
	public ResponseEntity<Employee> addEmployee(@RequestBody Employee employee) {
		Employee saved = employeeService.addEmployee(employee);
		return ResponseEntity.status(HttpStatus.CREATED).body(saved);
	}

	
	@PutMapping("/{id}")
	public ResponseEntity<Employee> updateEmployee(@PathVariable Long id, @RequestBody Employee employee) {
		Employee updated = employeeService.updateEmployee(id, employee)
				.orElseThrow(() -> new EmployeeNotFoundException(id));
		return ResponseEntity.ok(updated);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
		if (!employeeService.deleteEmployee(id)) {
			throw new EmployeeNotFoundException(id);
		}
		return ResponseEntity.noContent().build();
	}

	@GetMapping
	public ResponseEntity<List<Employee>> findAllEmployees() {
		return ResponseEntity.ok(employeeService.findAllEmployees());
	}

	@GetMapping("/{id}")
	public ResponseEntity<Employee> findEmployeeById(@PathVariable Long id) {
		Employee employee = employeeService.findEmployeeById(id).orElseThrow(() -> new EmployeeNotFoundException(id));
		return ResponseEntity.ok(employee);
	}
}