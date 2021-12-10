package com.ratnikov.crm.model;

import com.ratnikov.crm.enums.Currency;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity
@Table(name = "SELLING_INVOICE")
public class SellingInvoice {
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "sequence_generator"
    )
    @SequenceGenerator(
            name="sequence_generator",
            sequenceName = "selling_invoice_sequence",
            allocationSize = 1,
            initialValue = 100
    )
    @Column(name = "INVOICE_ID", nullable = false)
    private Long id;
    @Column(name = "INVOICE_DATE", nullable = false)
    private Date invoiceDate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "CUSTOMER_ID", nullable = false)
    private Customer customer;

    @Column(nullable = false)
    private Double netWorth;

    @Column(nullable = false)
    private Double grossValue;

    @Column(nullable = false)
    private Double taxRate;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Currency currency;

    public SellingInvoice(Date invoiceDate,
                          Customer customer,
                          Double netWorth,
                          Double grossValue,
                          Double taxRate,
                          Currency currency) {
        this.invoiceDate = invoiceDate;
        this.customer = customer;
        this.netWorth = netWorth;
        this.grossValue = grossValue;
        this.taxRate = taxRate;
        this.currency = currency;
    }
}
