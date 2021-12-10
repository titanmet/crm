package com.ratnikov.crm.repository;

import com.ratnikov.crm.model.Product;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findProductsByNameContaining(String name, Pageable pageable);

    List<Product> findProductsByProductTypeFullNameContaining(String productType, Pageable pageable);
}
