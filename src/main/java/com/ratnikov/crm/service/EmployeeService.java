package com.ratnikov.crm.service;

import com.ratnikov.crm.exports.pdf.ExportEmployeeToPDF;
import com.ratnikov.crm.exports.xls.ExportEmployeeToXLSX;
import com.ratnikov.crm.mapper.EmployeeMapper;
import com.ratnikov.crm.model.Employee;
import com.ratnikov.crm.model.dto.EmployeeDTO;
import com.ratnikov.crm.repository.EmployeeRepository;
import com.ratnikov.crm.security.registration.token.ConfirmationToken;
import com.ratnikov.crm.security.registration.token.ConfirmationTokenService;
import javassist.NotFoundException;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class EmployeeService implements UserDetailsService, CurrentTimeInterface {

    private final EmployeeRepository employeeRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final ConfirmationTokenService confirmationTokenService;
    private final EmployeeMapper employeeMapper;

    private static final String USER_NOT_FOUND_MSG =
            "user with email %s not found";

    @Override
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {
        return employeeRepository.findByEmail(email)
                .orElseThrow(()-> new UsernameNotFoundException(
                        String.format(USER_NOT_FOUND_MSG, email)));
    }

    public String signUpUser(Employee employee){
        boolean userExists = employeeRepository.findByEmail(employee.getEmail()).isPresent();
        if (userExists){
            //TODO: IF USER NOT CONFIRMED, SEND EMAIL AGAIN
            throw new IllegalStateException(
                    String.format("Email %s already taken", employee.getEmail()));
        }

        String encodedPassword = passwordEncoder.encode(employee.getPassword());
        employee.setPassword(encodedPassword);
        employeeRepository.save(employee);

        String token = UUID.randomUUID().toString();
        ConfirmationToken confirmationToken = new ConfirmationToken(
                token,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(15),
                employee);

        confirmationTokenService.saveConfirmationToken(confirmationToken);

        return token;
    }

    public int enableUser(String email) {
        return employeeRepository.enableUser(email);
    }

    @Transactional(readOnly = true)
    public List<EmployeeDTO> getAllEmployees(Pageable pageable){
        return employeeRepository.findAllBy(pageable)
                .stream()
                .map(employeeMapper::mapEmployeeToDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public EmployeeDTO getEmployeeById(Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee cannot be found, the specified id does not exist"));
        return employeeMapper.mapEmployeeToDto(employee);
    }

    @Transactional(readOnly = true)
    public List<EmployeeDTO> findEmployeesByFirstname(String firstName, Pageable pageable) {
        return employeeRepository.findUserByFirstNameContainingIgnoreCase(firstName, pageable)
                .stream()
                .map(employeeMapper::mapEmployeeToDto)
                .collect(Collectors.toList());
    }

    public void deleteEmployeeById(Long id) throws NotFoundException {
        try{
            employeeRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new NotFoundException("The specified id does not exist");
        }
    }

    public void exportToExcel(HttpServletResponse response) throws IOException {
        response.setContentType("application/vnd.ms-excel");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=employees_" + getCurrentDateTime() + ".xlsx";
        response.setHeader(headerKey, headerValue);

        List<Employee> employeeList = employeeRepository.findAll();

        ExportEmployeeToXLSX exporter = new ExportEmployeeToXLSX(employeeList);
        exporter.export(response);
    }

    public void exportToPDF(HttpServletResponse response) throws IOException {
        response.setContentType("application/pdf");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=employees_" + getCurrentDateTime() + ".pdf";
        response.setHeader(headerKey, headerValue);

        List<Employee> listEmployees = employeeRepository.findAll();

        ExportEmployeeToPDF exporter = new ExportEmployeeToPDF(listEmployees);
        exporter.export(response);
    }
}
