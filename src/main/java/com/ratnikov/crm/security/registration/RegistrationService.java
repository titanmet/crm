package com.ratnikov.crm.security.registration;

import com.ratnikov.crm.enums.UserRole;
import com.ratnikov.crm.model.Employee;
import com.ratnikov.crm.security.email.EmailBuilder;
import com.ratnikov.crm.security.email.EmailSender;
import com.ratnikov.crm.security.registration.token.ConfirmationToken;
import com.ratnikov.crm.security.registration.token.ConfirmationTokenService;
import com.ratnikov.crm.service.EmployeeService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class RegistrationService {

    private final EmployeeService employeeService;
    private final EmailValidator emailValidator;
    private final ConfirmationTokenService confirmationTokenService;
    private final EmailSender emailSender;

    public String register(RegistrationRequest request) {
        boolean isValidEmail = emailValidator.test(request.getEmail());

        if (!isValidEmail) {
            throw new IllegalStateException(
                    String.format("Email %s is not valid!", request.getEmail()));
        }

        String token = employeeService.signUpUser(
                new Employee(
                        request.getFirstName(),
                        request.getLastName(),
                        request.getEmail(),
                        request.getPassword(),
                        UserRole.EMPLOYEE,
                        request.getSex(),
                        request.getBirthdate(),
                        request.getSalary(),
                        request.getDepartment()

                ));
        String link = "http://localhost:8080/registration/confirm?token=" + token;
        emailSender.send(request.getEmail(),
                EmailBuilder.buildEmail(request.getFirstName(),
                        link));
        return token;
    }

    @Transactional
    public String confirmToken(String token) {
        ConfirmationToken confirmationToken = confirmationTokenService
                .getToken(token)
                .orElseThrow(() ->
                        new IllegalStateException("token not found"));

        if (confirmationToken.getConfirmedAt() != null) {
            throw new IllegalStateException("email already confirmed");
        }

        LocalDateTime expiredAt = confirmationToken.getExpiresAt();

        if (expiredAt.isBefore(LocalDateTime.now())) {
            throw new IllegalStateException("token expired");
        }

        confirmationTokenService.setConfirmedAt(token);
        employeeService.enableUser(
                confirmationToken.getEmployee().getEmail());
        return "confirmed";
    }
}
