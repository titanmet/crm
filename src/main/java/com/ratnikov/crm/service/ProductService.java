package com.ratnikov.crm.service;

import com.ratnikov.crm.exports.pdf.ExportProductsToPDF;
import com.ratnikov.crm.exports.xls.ExportProductsToXLSX;
import com.ratnikov.crm.mapper.ProductMapper;
import com.ratnikov.crm.model.Product;
import com.ratnikov.crm.model.dto.ProductDTO;
import com.ratnikov.crm.repository.ProductRepository;
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
public class ProductService implements CurrentTimeInterface{

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Transactional(readOnly = true)
    public List<ProductDTO> getAllProducts(Pageable pageable){
        return productRepository.findAll(pageable)
                .stream()
                .map(productMapper::mapProductToDto)
                .collect(Collectors.toList());
    }


    @Transactional(readOnly = true)
    public ProductDTO getProductById(Long id){
        Product product = productRepository.findById(id)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product cannot be found, the specified id does not exist"));
        return productMapper.mapProductToDto(product);
    }

    @Transactional
    public ProductDTO editProduct(Product product){
        Product editedProduct = productRepository.findById(product.getId())
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product cannot be found"));
        editedProduct.setName(product.getName());
        editedProduct.setSellingPrice(product.getSellingPrice());
        editedProduct.setPurchasePrice(product.getPurchasePrice());
        editedProduct.setTaxRate(product.getTaxRate());
        return productMapper.mapProductToDto(editedProduct);
    }

    public Product addNewProduct(Product product) {
        return productRepository.save(product);
    }

    public void deleteProductById(Long id) {
        try{
            productRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "The specified id does not exist");
        }
    }

    public void exportToExcel(HttpServletResponse response) throws IOException {
        response.setContentType("application/vnd.ms-excel");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=products_" + getCurrentDateTime() + ".xlsx";
        response.setHeader(headerKey, headerValue);

        List<Product> productList = productRepository.findAll();

        ExportProductsToXLSX exporter = new ExportProductsToXLSX(productList);
        exporter.export(response);
    }

    public void exportToPDF(HttpServletResponse response) throws IOException {
        response.setContentType("application/pdf");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=products_" + getCurrentDateTime() + ".pdf";
        response.setHeader(headerKey, headerValue);

        List<Product> productList = productRepository.findAll();

        ExportProductsToPDF exporter = new ExportProductsToPDF(productList);
        exporter.export(response);
    }

    @Transactional(readOnly = true)
    public List<ProductDTO> findAllByName(String name, Pageable pageable) {
        return productRepository.findProductsByNameContaining(name, pageable)
                .stream()
                .map(productMapper::mapProductToDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ProductDTO> findAllByProductType(String productType, Pageable pageable){
        return productRepository.findProductsByProductTypeFullNameContaining(productType, pageable)
                .stream()
                .map(productMapper::mapProductToDto)
                .collect(Collectors.toList());
    }
}
