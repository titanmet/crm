package com.ratnikov.crm.repository;

import com.ratnikov.crm.model.PurchasesPositions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PurchasesPositionsRepository extends JpaRepository<PurchasesPositions, Long> {

    @Query(value = "SELECT pp FROM PurchasesPositions pp WHERE pp.id = :Id")
    List<PurchasesPositions> findAllById(@Param("Id") Long Id);
}
