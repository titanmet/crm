package com.ratnikov.crm.service;

import com.ratnikov.crm.exports.pdf.ExportSuppliersToPDF;
import com.ratnikov.crm.exports.xls.ExportSuppliersToXLSX;
import com.ratnikov.crm.mapper.SupplierMapper;
import com.ratnikov.crm.model.Supplier;
import com.ratnikov.crm.model.dto.SupplierDTO;
import com.ratnikov.crm.repository.SupplierRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class SupplierService implements CurrentTimeInterface{

    private final SupplierRepository supplierRepository;
    private final SupplierMapper supplierMapper;

    @Transactional(readOnly = true)
    public List<SupplierDTO> getAllSuppliers(Pageable pageable){
        return supplierRepository.findAll(pageable)
                .stream()
                .map(supplierMapper::mapSupplierToDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public SupplierDTO getSupplierById(Long id) {
        Supplier supplier = supplierRepository.findById(id)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Supplier cannot be found, the specified id does not exist"));
        return supplierMapper.mapSupplierToDTO(supplier);
    }

    public Supplier addNewSuppiler(Supplier supplier){
        return supplierRepository.save(supplier);
    }

    public void deleteSupplierById(Long id) {
        try {
            supplierRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "The specified id does not exist");
        }
    }

    public SupplierDTO editSupplier(Supplier supplier){
        Supplier editedSupplier = supplierRepository.findById(supplier.getSupplierId()).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Supplier does not exist"));
        editedSupplier.setSupplierName(supplier.getSupplierName());
        editedSupplier.setActivityStatus(supplier.getActivityStatus());
        return supplierMapper.mapSupplierToDTO(editedSupplier);
    }

    public void exportToExcel(HttpServletResponse response) throws IOException {
        response.setContentType("application/vnd.ms-excel");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=suppliers_" + getCurrentDateTime() + ".xlsx";
        response.setHeader(headerKey, headerValue);

        List<Supplier> supplierList = supplierRepository.findAll();

        ExportSuppliersToXLSX exporter = new ExportSuppliersToXLSX(supplierList);
        exporter.export(response);
    }

    public void exportToPDF(HttpServletResponse response) throws IOException {
        response.setContentType("application/pdf");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=suppliers_" + getCurrentDateTime() + ".pdf";
        response.setHeader(headerKey, headerValue);

        List<Supplier> supplierList = supplierRepository.findAll();

        ExportSuppliersToPDF exporter = new ExportSuppliersToPDF(supplierList);
        exporter.export(response);
    }
}
