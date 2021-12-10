package com.ratnikov.crm.exports.xls;

import com.ratnikov.crm.exports.ExcelColumnsHeaderWriter;
import com.ratnikov.crm.model.Absenteeism;
import lombok.AllArgsConstructor;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
public class ExportAbsenteeismToXLSX implements ExcelColumnsHeaderWriter {

    private static final String[] columns = {"Id", "Employee Id", "First Name", "Last Name", "Department", "Date from", "Date to", "Reasons"};
    private final List<Absenteeism> absenteeisms;

    private void writeCellsData(Sheet sheet) {
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
        int rowNum = 1;
        for (Absenteeism absenteeism : absenteeisms) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(absenteeism.getId());
            row.createCell(1).setCellValue(absenteeism.getEmployee().getId());
            row.createCell(2).setCellValue(absenteeism.getEmployee().getFirstName());
            row.createCell(3).setCellValue(absenteeism.getEmployee().getLastName());
            row.createCell(4).setCellValue(absenteeism.getEmployee().getDepartment().getDepartmentName());
            row.createCell(5).setCellValue(dateFormatter.format(absenteeism.getDateFrom()));
            row.createCell(6).setCellValue(dateFormatter.format(absenteeism.getDateTo()));
            row.createCell(7).setCellValue(String.valueOf(absenteeism.getReasonOfAbsenteeismCode().getAbsenteeismName()));
            // Resize all columns to fit the content size
            for (int i = 0; i < columns.length; i++) {
                sheet.autoSizeColumn(i);
            }
        }
    }

    public void export(HttpServletResponse response) throws IOException {
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
        Workbook workbook = new XSSFWorkbook();
        String currentDateTime = dateFormatter.format(new Date());
        String headerValue = "absenteeisms_" + currentDateTime + ".xlsx";
        Sheet sheet = workbook.createSheet(headerValue);

        writeColumnsHeader(workbook, sheet, columns);
        writeCellsData(sheet);

        workbook.write(response.getOutputStream());
        workbook.close();
    }
}
