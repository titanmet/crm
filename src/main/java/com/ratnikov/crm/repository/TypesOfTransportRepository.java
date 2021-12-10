package com.ratnikov.crm.repository;

import com.ratnikov.crm.enums.TransportCode;
import com.ratnikov.crm.model.TypesOfTransport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TypesOfTransportRepository extends JpaRepository<TypesOfTransport, TransportCode> {
}
