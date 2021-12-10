package com.ratnikov.crm.service;

import com.ratnikov.crm.mapper.ProductUnitsMapper;
import com.ratnikov.crm.model.dto.ProductUnitsDTO;
import com.ratnikov.crm.repository.ProductUnitsRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProductUnitsService {

    private final ProductUnitsRepository productUnitsRepository;
    private final ProductUnitsMapper productUnitsMapper;

    @Transactional(readOnly = true)
    public List<ProductUnitsDTO> getAllProductUnits(Pageable pageable){
        return productUnitsRepository.findAll(pageable)
                .stream()
                .map(productUnitsMapper::mapProductUnitsToDTO)
                .collect(Collectors.toList());
    }
}
