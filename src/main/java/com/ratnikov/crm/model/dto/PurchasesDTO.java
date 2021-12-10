package com.ratnikov.crm.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PurchasesDTO {
    private Long id;
    private Long customerId;
    private String customerName;
    private String purchaseDate;
    private String invoiceId;
    private String invoiceDate;
    private String invoiceCustomerId;
    private String invoiceCustomerName;
    private String invoiceNetWorth;
    private String invoiceGrossValue;
    private String invoiceCurrency;
}