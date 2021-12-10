package com.ratnikov.crm.mapper;

import com.ratnikov.crm.model.SellingInvoice;
import com.ratnikov.crm.model.dto.SellingInvoiceDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SellingInvoiceMapper {

    @Mapping(target = "customerId", source = "customer.id")
    @Mapping(target = "customerFirstName", source = "customer.firstname")
    @Mapping(target = "customerLastName", source = "customer.lastname")
    SellingInvoiceDTO mapSellingInvoiceToDTO(SellingInvoice sellingInvoice);
}
