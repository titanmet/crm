package com.ratnikov.crm.mapper;

import com.ratnikov.crm.model.ProductUnits;
import com.ratnikov.crm.model.dto.ProductUnitsDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductUnitsMapper {

    @Mapping(target = "unitOfMeasure", source = "unitOfMeasure.")
    @Mapping(target = "productId", source = "product.id")
    @Mapping(target = "productName", source = "product.name")
    ProductUnitsDTO mapProductUnitsToDTO(ProductUnits productUnits);
}
