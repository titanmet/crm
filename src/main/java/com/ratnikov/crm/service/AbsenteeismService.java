package com.ratnikov.crm.service;

import com.ratnikov.crm.exports.pdf.ExportAbsenteeismToPDF;
import com.ratnikov.crm.exports.xls.ExportAbsenteeismToXLSX;
import com.ratnikov.crm.mapper.AbsenteeismMapper;
import com.ratnikov.crm.model.Absenteeism;
import com.ratnikov.crm.model.dto.AbsenteeismDTO;
import com.ratnikov.crm.repository.AbsenteeismRepository;
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
public class AbsenteeismService implements CurrentTimeInterface{

    private final AbsenteeismRepository absenteeismRepository;
    private final AbsenteeismMapper absenteeismMapper;

    @Transactional(readOnly = true)
    public List<AbsenteeismDTO> getAllAbsenteeisms(Pageable pageable){
        return absenteeismRepository.findAllBy(pageable)
                .stream()
                .map(absenteeismMapper::mapAbsenteeismToDto)
                .collect(Collectors.toList());
    }


    @Transactional(readOnly = true)
    public AbsenteeismDTO getAbsenteeismById(Long id){
        Absenteeism absenteeism = absenteeismRepository.findById(id)
                .orElseThrow(()-> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Absenteeism cannot be found, the specified id does not exist"));
        return absenteeismMapper.mapAbsenteeismToDto(absenteeism);
    }

    public Absenteeism addNewAbsenteeism(Absenteeism absenteeism) {
        return absenteeismRepository.save(absenteeism);
    }

    public void deleteAbsenteeismById(Long id) {
        try{
            absenteeismRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Absenteeism cannot be found, the specified id does not exist");
        }
    }

    public void exportToExcel(HttpServletResponse response) throws IOException {
        response.setContentType("application/vnd.ms-excel");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=absenteeisms_" + getCurrentDateTime() + ".xlsx";
        response.setHeader(headerKey, headerValue);

        List<Absenteeism> absenteeismList = absenteeismRepository.findAll();

        ExportAbsenteeismToXLSX exporter = new ExportAbsenteeismToXLSX(absenteeismList);
        exporter.export(response);
    }

    public void exportToPDF(HttpServletResponse response) throws IOException {
        response.setContentType("application/pdf");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=absenteeisms_" + getCurrentDateTime() + ".pdf";
        response.setHeader(headerKey, headerValue);

        List<Absenteeism> absenteeismList = absenteeismRepository.findAll();

        ExportAbsenteeismToPDF exporter = new ExportAbsenteeismToPDF(absenteeismList);
        exporter.export(response);
    }
}
