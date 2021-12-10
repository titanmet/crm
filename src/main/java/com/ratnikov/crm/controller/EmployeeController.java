package com.ratnikov.crm.controller;

import com.ratnikov.crm.model.dto.EmployeeDTO;
import com.ratnikov.crm.service.EmployeeService;
import javassist.NotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static com.ratnikov.crm.controller.ApiMapping.EMPLOYEES_REST_URL;
import static org.springframework.http.ResponseEntity.status;

@RestController
@RequestMapping(produces="application/json", path = EMPLOYEES_REST_URL)
@AllArgsConstructor
@CrossOrigin(origins="*")
public class EmployeeController {

    private final EmployeeService employeeService;

    @GetMapping
    @PreAuthorize("hasAuthority('EMPLOYEE') or hasAuthority('MANAGER') or hasAuthority('ADMIN')")
    public ResponseEntity<List<EmployeeDTO>> getAllEmployees(Pageable pageable){
        return status(HttpStatus.OK).body(employeeService.getAllEmployees(pageable));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('EMPLOYEE') or hasAuthority('MANAGER') or hasAuthority('ADMIN')")
    public ResponseEntity<EmployeeDTO> getEmployeeById(@PathVariable("id") Long id){
        return status(HttpStatus.OK).body(employeeService.getEmployeeById(id));
    }

    @GetMapping("/")
    @PreAuthorize("hasAuthority('EMPLOYEE') or hasAuthority('MANAGER') or hasAuthority('ADMIN')")
    public ResponseEntity<List<EmployeeDTO>> findEmployeesByFirstname(@RequestParam("firstname") String firstName, Pageable pageable) {
        return status(HttpStatus.OK).body(employeeService.findEmployeesByFirstname(firstName, pageable));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('ADMIN')")
    public void deleteEmployeeById(@PathVariable("id") Long id) throws NotFoundException {
        employeeService.deleteEmployeeById(id);
    }

    @GetMapping("/export/excel")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('MANAGER') or hasAuthority('ADMIN')")
    public void exportToExcel(HttpServletResponse response) throws IOException {
        employeeService.exportToExcel(response);
    }

    @GetMapping("/export/pdf")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('MANAGER') or hasAuthority('ADMIN')")
    public void exportToPDF(HttpServletResponse response) throws IOException {
        employeeService.exportToPDF(response);
    }
}
