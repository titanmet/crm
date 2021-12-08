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
public class Customers {
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
    @Column(name = "org_forma", nullable = false)
    private String orgForma;
    @Column(nullable = false)
    private String name;
    private String phone;
    @Email
    private String email;
    private String description;

    public Customers(String orgForma, String name, String phone, @Email String email, String description) {
        this.orgForma = orgForma;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.description = description;
    }
}