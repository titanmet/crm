package com.ratnikov.crm.mapper;

import com.ratnikov.crm.model.Product;
import com.ratnikov.crm.model.dto.ProductDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    @Mapping(target = "productType", source = "productType.fullName")
    ProductDTO mapProductToDto(Product product);
}
