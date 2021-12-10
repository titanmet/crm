package com.ratnikov.crm.mapper;

import com.ratnikov.crm.model.PurchasesPositions;
import com.ratnikov.crm.model.dto.PurchasesPositionsDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PurchasesPositionsMapper {

    @Mapping(target = "productId", source = "product.id")
    @Mapping(target = "productName", source = "product.name")
    @Mapping(target = "purchaseId", source = "purchases.id")
    @Mapping(target = "customerId", source = "purchases.customer.id")
    @Mapping(target = "purchaseDate", source = "purchases.purchaseDate")
    @Mapping(target = "sellingPrice", source = "product.sellingPrice")
    PurchasesPositionsDTO mapPurchasesPositionsToDTO(PurchasesPositions purchasesPositions);
}