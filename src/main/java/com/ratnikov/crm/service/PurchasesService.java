package com.ratnikov.crm.service;

import com.ratnikov.crm.mapper.PurchasesMapper;
import com.ratnikov.crm.model.Purchases;
import com.ratnikov.crm.model.dto.PurchasesDTO;
import com.ratnikov.crm.repository.PurchasesRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PurchasesService {

    private final PurchasesRepository purchasesRepository;
    private final PurchasesMapper purchasesMapper;

    @Transactional(readOnly = true)
    public List<PurchasesDTO> getAllPurchases(Pageable pageable){
        return purchasesRepository.findAll(pageable)
                .stream()
                .map(purchasesMapper::mapPurchasesToDTO)
                .collect(Collectors.toList());
    }


    @Transactional(readOnly = true)
    public PurchasesDTO getPurchaseById(Long id) {
        Purchases purchases = purchasesRepository.findById(id)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Purchase cannot be found, the specified id does not exist"));
        return purchasesMapper.mapPurchasesToDTO(purchases);
    }

    public Purchases addNewPurchase(Purchases purchase) {
        return purchasesRepository.save(purchase);
    }

    public void deletePurchaseById(Long id) {
        try{
            purchasesRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "The specified id does not exist");
        }
    }
}
