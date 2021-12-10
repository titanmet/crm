package com.ratnikov.crm.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;

@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Entity
public class Customer {
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "sequence_generator"
    )
    @SequenceGenerator(
            name="sequence_generator",
            sequenceName = "customer_sequence",
            allocationSize = 1,
            initialValue = 100
    )
    @Column(name = "CUSTOMER_ID", nullable = false, unique = true)
    private Long id;
    @Column(nullable = false)
    private String name;
    private String phone;
    @Email
    private String email;
    private String description;

    public Customer(String name, String phone, @Email String email, String description) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.description = description;
    }
}