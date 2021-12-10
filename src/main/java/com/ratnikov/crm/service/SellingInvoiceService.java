package com.ratnikov.crm.service;

import com.ratnikov.crm.mapper.SellingInvoiceMapper;
import com.ratnikov.crm.model.SellingInvoice;
import com.ratnikov.crm.model.dto.SellingInvoiceDTO;
import com.ratnikov.crm.repository.SellingInvoiceRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class SellingInvoiceService {

    private final SellingInvoiceRepository sellingInvoiceRepository;
    private final SellingInvoiceMapper sellingInvoiceMapper;

    @Transactional(readOnly = true)
    public List<SellingInvoiceDTO> getAllSellingInvoices(Pageable pageable){
        return sellingInvoiceRepository.findAll(pageable)
                .stream()
                .map(sellingInvoiceMapper::mapSellingInvoiceToDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public SellingInvoiceDTO getInvoiceById(Long id) {
        SellingInvoice sellingInvoice = sellingInvoiceRepository.findById(id)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Selling invoice cannot be found, the specified id does not exist"));
        return sellingInvoiceMapper.mapSellingInvoiceToDTO(sellingInvoice);
    }

    public SellingInvoice addNewInvoice(SellingInvoice sellingInvoice) {
        return sellingInvoiceRepository.save(sellingInvoice);
    }

    public void deleteInvoiceById(Long id) {
        try{
            sellingInvoiceRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "The specified id does not exist");
        }
    }

    public SellingInvoiceDTO editSellingInvoice(SellingInvoice sellingInvoice) {
        SellingInvoice editedSellingInvoice = sellingInvoiceRepository.findById(sellingInvoice.getId()).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Invoice does not exist"));
        editedSellingInvoice.setInvoiceDate(sellingInvoice.getInvoiceDate());
        editedSellingInvoice.setGrossValue(sellingInvoice.getGrossValue());
        editedSellingInvoice.setTaxRate(sellingInvoice.getTaxRate());
        editedSellingInvoice.setNetWorth(sellingInvoice.getNetWorth());
        editedSellingInvoice.setCurrency(sellingInvoice.getCurrency());
        editedSellingInvoice.setCustomer(sellingInvoice.getCustomer());
        return sellingInvoiceMapper.mapSellingInvoiceToDTO(sellingInvoice);
    }
}
