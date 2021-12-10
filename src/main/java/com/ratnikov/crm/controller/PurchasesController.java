package com.ratnikov.crm.controller;

import com.ratnikov.crm.model.Purchases;
import com.ratnikov.crm.model.dto.PurchasesDTO;
import com.ratnikov.crm.service.PurchasesService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.ratnikov.crm.controller.ApiMapping.PURCHASES_REST_URL;
import static org.springframework.http.ResponseEntity.status;

@RestController
@RequestMapping(produces="application/json", path = PURCHASES_REST_URL)
@AllArgsConstructor
@CrossOrigin(origins="*")
public class PurchasesController {

    private final PurchasesService purchasesService;

    @GetMapping
    @PreAuthorize("hasAuthority('EMPLOYEE') or hasAuthority('MANAGER') or hasAuthority('ADMIN')")
    public ResponseEntity<List<PurchasesDTO>> getAllPurchases(Pageable pageable){
        return status(HttpStatus.OK).body(purchasesService.getAllPurchases(pageable));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('EMPLOYEE') or hasAuthority('MANAGER') or hasAuthority('ADMIN')")
    public ResponseEntity<PurchasesDTO> getPurchaseById(@PathVariable("id") Long id){
        return status(HttpStatus.OK).body(purchasesService.getPurchaseById(id));
    }

    @PostMapping(consumes="application/json")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('EMPLOYEE') or hasAuthority('MANAGER') or hasAuthority('ADMIN')")
    public Purchases postNewPurchase(@RequestBody Purchases purchase) {
        return purchasesService.addNewPurchase(purchase);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('ADMIN')")
    public void deletePurchaseById(@PathVariable("id") Long id){
        purchasesService.deletePurchaseById(id);
    }
}
