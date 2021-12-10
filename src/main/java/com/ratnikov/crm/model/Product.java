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
@Table(name = "PRODUCTS")
public class Product {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "sequence_generator"
    )
    @SequenceGenerator(
            name = "sequence_generator",
            sequenceName = "products_sequence",
            allocationSize = 1,
            initialValue = 100
    )
    @Column(name = "PRODUCT_ID")
    private Long id;

    @Column(name = "NAME_OF_PRODUCT")
    private String name;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "PRODUCT_TYPE")
    private ProductType productType;

    private Double sellingPrice;
    private Double purchasePrice;
    private Double taxRate;

    @Enumerated(EnumType.STRING)
    private UnitsOfMeasure unitsOfMeasure;

    public Product(String name,
                   ProductType productType,
                   Double sellingPrice,
                   Double purchasePrice,
                   Double taxRate,
                   UnitsOfMeasure unitsOfMeasure) {
        this.name = name;
        this.productType = productType;
        this.sellingPrice = sellingPrice;
        this.purchasePrice = purchasePrice;
        this.taxRate = taxRate;
        this.unitsOfMeasure = unitsOfMeasure;
    }
}