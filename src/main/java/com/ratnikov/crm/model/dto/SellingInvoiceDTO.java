package com.ratnikov.crm.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SellingInvoiceDTO {
    private Long id;
    private String invoiceDate;
    private Long customerId;
    private String customerName;
    private Double netWorth;
    private Double grossValue;
    private Double taxRate;
    private String currency;

}
