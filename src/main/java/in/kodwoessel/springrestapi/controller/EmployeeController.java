package in.kodwoessel.springrestapi.controller;
import java.util.List;
import java.util.Optional;

import in.kodwoessel.springrestapi.model.Department;
import in.kodwoessel.springrestapi.repository.DepartmentRepository;
import in.kodwoessel.springrestapi.repository.EmployeeRepository;
import in.kodwoessel.springrestapi.request.EmployeeRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import in.kodwoessel.springrestapi.model.Employee;
import in.kodwoessel.springrestapi.service.EmployeeService;



@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*", maxAge = 3600)
public class EmployeeController {

	// localhost:8080/api/v1/employee
	
	@Autowired
	private EmployeeService eService;

	@Autowired
	private EmployeeRepository eRepo;

	@Autowired
	private DepartmentRepository dRepo;
	
	@GetMapping("/employees")
	public ResponseEntity<List<Employee>> getEmployees (@RequestParam int pageNumber, @RequestParam int pageSize) {

		return new ResponseEntity<List<Employee>>(eService.getEmployees(pageNumber, pageSize), HttpStatus.OK);
	}
	
	@GetMapping("/employees/{id}")
	public ResponseEntity<Employee> getEmployee (@PathVariable Long id) {

		return new ResponseEntity<Employee>(eService.getSingleEmployee(id), HttpStatus.OK);
	}
	
	@DeleteMapping("/employees")
	public ResponseEntity<HttpStatus> deleteEmployee (@RequestParam Long id) {
		eService.deleteEmployee(id);
		return new ResponseEntity<HttpStatus>(HttpStatus.NO_CONTENT);
	}
	
	@PostMapping("/employees")
	public ResponseEntity<Employee> saveEmployee(@Valid @RequestBody EmployeeRequest eRequest) {
		Optional<Department> existingDept = dRepo.findByName(eRequest.getDepartment());

		Department dept;
		if (existingDept.isPresent()) {
			dept = existingDept.get();
		} else {
			dept = new Department();
			dept.setName(eRequest.getDepartment());
			dept = dRepo.save(dept);
		};

		Employee employee = new Employee(eRequest);
		employee.setDepartment(dept);

		employee = eRepo.save(employee);
		return new ResponseEntity<Employee>(employee, HttpStatus.CREATED);
		
	}
	
	@PutMapping("/employees/{id}")
	public ResponseEntity<Employee> updateEmployee(@PathVariable Long id, @Valid @RequestBody Employee employee) {
		employee.setId(id);
        return new ResponseEntity<Employee>(eService.updateEmployee(employee), HttpStatus.OK);
    }

	@GetMapping("/employees/filterByName")
	public ResponseEntity<List<Employee>> getEmployeesByName(@RequestParam String name){
		return new ResponseEntity<List<Employee>>(eService.getEmployeesByName(name), HttpStatus.OK);
	}

	@GetMapping("/employees/filterByNameAndLocation")
	public ResponseEntity<List<Employee>> getEmployeesByNameAndLocation(@RequestParam String name, @RequestParam String location){
		return new ResponseEntity<List<Employee>>(eService.getEmployeesByNameAndLocation(name, location), HttpStatus.OK);
	}

	@GetMapping("/employees/filterByKeyword")
	public ResponseEntity<List<Employee>> getEmployeesByKeyword(@RequestParam String keyword){
		return new ResponseEntity<List<Employee>>(eService.getEmployeesByKeyword(keyword), HttpStatus.OK);
	}
}
