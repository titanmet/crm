package com.ratnikov.crm.repository;

import com.ratnikov.crm.model.ProductUnits;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductUnitsRepository extends JpaRepository<ProductUnits, Long> {
}
