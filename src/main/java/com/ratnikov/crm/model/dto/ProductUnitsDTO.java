package com.ratnikov.crm.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductUnitsDTO {
    private Long id;
    private Long productId;
    private String productName;
    private String unitOfMeasure;
    private String alternativeUnitOfMeasure;
    private Double conversionFactor;
}
