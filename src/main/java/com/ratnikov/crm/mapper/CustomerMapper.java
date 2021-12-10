package com.ratnikov.crm.mapper;


import com.ratnikov.crm.model.Customer;
import com.ratnikov.crm.model.dto.CustomerDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CustomerMapper {

    CustomerDTO mapCustomersToDto(Customer customers);

    Customer mapCustomerDTOtoCustomers(CustomerDTO customerDTO);
}
