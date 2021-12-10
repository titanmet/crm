package com.ratnikov.crm.service;

import com.ratnikov.crm.exports.pdf.ExportCustomersToPDF;
import com.ratnikov.crm.exports.xls.ExportCustomersToXLSX;
import com.ratnikov.crm.mapper.CustomerMapper;
import com.ratnikov.crm.model.Customer;
import com.ratnikov.crm.model.dto.CustomerDTO;
import com.ratnikov.crm.repository.CustomerRepository;
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
public class CustomerService implements CurrentTimeInterface{

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    @Transactional(readOnly = true)
    public List<CustomerDTO> getAllCustomers(Pageable pageable){
        return customerRepository.findAll(pageable)
                .stream()
                .map(customerMapper::mapCustomersToDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public CustomerDTO getCustomerById(Long id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(()-> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Customer cannot be found, the specified id does not exist"));
        return customerMapper.mapCustomersToDto(customer);

    }

    @Transactional(readOnly = true)
    public List<CustomerDTO> findCustomersByFirstname(String name, Pageable pageable){
        return customerRepository.findCustomersByNameContainingIgnoreCase(name, pageable)
                .stream()
                .map(customerMapper::mapCustomersToDto)
                .collect(Collectors.toList());
    }

    public Customer addNewCustomer(CustomerDTO customer) {
        return customerRepository.save(customerMapper.mapCustomerDTOtoCustomers(customer));
    }

    public void deleteCustomerById(Long id) {
        try{
            customerRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "The specified id does not exist");
        }
    }

    public void exportToExcel(HttpServletResponse response) throws IOException {
        response.setContentType("application/vnd.ms-excel");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=customers_" + getCurrentDateTime() + ".xlsx";
        response.setHeader(headerKey, headerValue);

        List<Customer> customersList = customerRepository.findAll();

        ExportCustomersToXLSX exporter = new ExportCustomersToXLSX(customersList);
        exporter.export(response);
    }

    public void exportToPDF(HttpServletResponse response) throws IOException {
        response.setContentType("application/pdf");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=customers_" + getCurrentDateTime() + ".pdf";
        response.setHeader(headerKey, headerValue);

        List<Customer> customersList = customerRepository.findAll();

        ExportCustomersToPDF exporter = new ExportCustomersToPDF(customersList);
        exporter.export(response);
    }

    @Transactional
    public CustomerDTO editCustomer(Customer customers) {
        Customer editedCustomer = customerRepository.findById(customers.getId()).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer does not exist"));
        editedCustomer.setName(customers.getName());
        editedCustomer.setPhone(customers.getPhone());
        editedCustomer.setEmail(customers.getEmail());
        editedCustomer.setDescription(customers.getDescription());
        return customerMapper.mapCustomersToDto(editedCustomer);
    }
}
