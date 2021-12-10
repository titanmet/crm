package com.ratnikov.crm.service;

import com.ratnikov.crm.exports.pdf.ExportDepartmentsToPDF;
import com.ratnikov.crm.exports.xls.ExportDepartmentsToXLSX;
import com.ratnikov.crm.mapper.DepartmentMapper;
import com.ratnikov.crm.model.Departments;
import com.ratnikov.crm.model.dto.DepartmentDTO;
import com.ratnikov.crm.repository.DepartmentsRepository;
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
@NoArgsConstructor
@AllArgsConstructor
public class DepartmentService implements CurrentTimeInterface{

    private DepartmentsRepository departmentsRepository;
    private DepartmentMapper departmentMapper;

    @Transactional(readOnly = true)
    public List<DepartmentDTO> getAllDepartments(Pageable pageable){
        return departmentsRepository.findAllBy(pageable)
                .stream()
                .map(departmentMapper::mapDepartmentToDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public DepartmentDTO getDepartmentById(Long id) {
        Departments departments = departmentsRepository.findById(id)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Department cannot be found, the specified id does not exist"));
        return departmentMapper.mapDepartmentToDto(departments);
    }

    public Departments addNewDepartment(Departments department) {
        return departmentsRepository.save(department);
    }

    public void deleteDepartmentById(Long id) {
        try{
            departmentsRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "The specified id does not exist");
        }
    }

    public void exportToExcel(HttpServletResponse response) throws IOException{
        response.setContentType("application/vnd.ms-excel");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=departments_" + getCurrentDateTime() + ".xlsx";
        response.setHeader(headerKey, headerValue);

        List<Departments> departmentsList = departmentsRepository.findAll();

        ExportDepartmentsToXLSX exporter = new ExportDepartmentsToXLSX(departmentsList);
        exporter.export(response);
    }

    public void exportToPDF(HttpServletResponse response) throws IOException {
        response.setContentType("application/pdf");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=departments_" + getCurrentDateTime() + ".pdf";
        response.setHeader(headerKey, headerValue);

        List<Departments> departmentsList = departmentsRepository.findAll();

        ExportDepartmentsToPDF exporter = new ExportDepartmentsToPDF(departmentsList);
        exporter.export(response);
    }

    public List<DepartmentDTO> getAllDepartmentsByName(String name, Pageable pageable) {
        return departmentsRepository.getDepartmentsByDepartmentNameContainingIgnoreCase(name, pageable)
                .stream()
                .map(departmentMapper::mapDepartmentToDto)
                .collect(Collectors.toList());
    }
}
