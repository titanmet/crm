package com.ratnikov.crm.repository;

import com.ratnikov.crm.model.SellingInvoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SellingInvoiceRepository extends JpaRepository<SellingInvoice, Long> {
}
