package com.ratnikov.crm.service;

import com.ratnikov.crm.mapper.PurchasesPositionsMapper;
import com.ratnikov.crm.model.PurchasesPositions;
import com.ratnikov.crm.model.dto.PurchasesPositionsDTO;
import com.ratnikov.crm.repository.PurchasesPositionsRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PurchasesPositionsService {

    private final PurchasesPositionsRepository purchasesPositionsRepository;
    private final PurchasesPositionsMapper purchasesPositionsMapper;

    @Transactional(readOnly = true)
    public List<PurchasesPositionsDTO> getAllPurchasesPositions(Pageable pageable){
        return purchasesPositionsRepository.findAll(pageable)
                .stream()
                .map(purchasesPositionsMapper::mapPurchasesPositionsToDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public PurchasesPositionsDTO getpurchasePositiontById(Long id) {
        PurchasesPositions purchasesPositions = purchasesPositionsRepository.findById(id)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Purchase position cannot be found, the specified id does not exist"));
        return purchasesPositionsMapper.mapPurchasesPositionsToDTO(purchasesPositions);
    }
}
