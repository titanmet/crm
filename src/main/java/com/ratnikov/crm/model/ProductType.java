package com.ratnikov.crm.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity
@Table(name = "PRODUCT_TYPE")
public class ProductType {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "sequence_generator"
    )
    @SequenceGenerator(
            name="sequence_generator",
            sequenceName = "product_type_sequence",
            allocationSize = 1,
            initialValue = 100
    )
    @Column(name ="PRODUCT_TYPE_ID")
    private String id;

    private String fullName;
    private Double discount;
    private Character periodOfAvailability;

    public ProductType(String fullName,
                       Double discount,
                       Character periodOfAvailability) {
        this.fullName = fullName;
        this.discount = discount;
        this.periodOfAvailability = periodOfAvailability;
    }
}
