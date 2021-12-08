package com.ratnikov.crm.model;

import com.ratnikov.crm.enums.UnitsOfMeasure;
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
@Table(name = "PRODUCT_UNITS")
public class ProductUnits {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "sequence_generator"
    )
    @SequenceGenerator(
            name="sequence_generator",
            sequenceName = "product_units_sequence",
            allocationSize = 1,
            initialValue = 100
    )
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "PRODUCT_ID", nullable = false)
    private Products products;

    @Enumerated(EnumType.STRING)
    @Column(name = "UOM")
    private UnitsOfMeasure unitOfMeasure;

    @Column(name = "UOM_ALT")
    private String alternativeUnitOfMeasure;

    private Double conversionFactor;

    public ProductUnits(Products products,
                        UnitsOfMeasure unitOfMeasure,
                        String alternativeUnitOfMeasure,
                        Double conversionFactor) {
        this.products = products;
        this.unitOfMeasure = unitOfMeasure;
        this.alternativeUnitOfMeasure = alternativeUnitOfMeasure;
        this.conversionFactor = conversionFactor;
    }
}
