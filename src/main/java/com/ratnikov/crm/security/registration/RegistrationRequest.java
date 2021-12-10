package com.ratnikov.crm.security.registration;

import com.ratnikov.crm.model.Departments;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;

@AllArgsConstructor
@Getter
@EqualsAndHashCode
@ToString
public class RegistrationRequest {
    private final String firstName;
    private final String lastName;
    private final String email;
    private final String password;
    private final String pesel;
    private final String sex;
    private final LocalDate birthdate;
    private final Double salary;
    private final Departments department;
}