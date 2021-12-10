package com.ratnikov.crm.controller;

import com.ratnikov.crm.model.dto.ProductUnitsDTO;
import com.ratnikov.crm.service.ProductUnitsService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.ratnikov.crm.controller.ApiMapping.PRODUCT_UNITS_REST_URL;
import static org.springframework.http.ResponseEntity.status;

@RestController
@RequestMapping(produces="application/json", path = PRODUCT_UNITS_REST_URL)
@AllArgsConstructor
@CrossOrigin(origins="*")
public class ProductUnitsController {
    private final ProductUnitsService productUnitsService;

    @GetMapping
    @PreAuthorize("hasAuthority('EMPLOYEE') or hasAuthority('MANAGER') or hasAuthority('ADMIN')")
    public ResponseEntity<List<ProductUnitsDTO>> getAllProductUnits(Pageable pageable){
        return status(HttpStatus.OK).body(productUnitsService.getAllProductUnits(pageable));
    }
}
