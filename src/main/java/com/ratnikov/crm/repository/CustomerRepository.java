package com.ratnikov.crm.repository;

import com.ratnikov.crm.model.Customer;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    List<Customer> findCustomersByNameContainingIgnoreCase(@RequestParam("name") String name, Pageable pageable);
}
