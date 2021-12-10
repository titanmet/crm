package com.ratnikov.crm.repository;

import com.ratnikov.crm.model.Purchases;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PurchasesRepository extends JpaRepository<Purchases, Long> {
}
