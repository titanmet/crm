package com.ratnikov.crm.mapper;

import com.ratnikov.crm.model.Supplier;
import com.ratnikov.crm.model.dto.SupplierDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SupplierMapper {

    @Mapping(target = "typeOfTransport", source = "modeOfTransportCode.fullName")
    SupplierDTO mapSupplierToDTO(Supplier supplier);
}
