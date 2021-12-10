package com.ratnikov.crm.exports.xls;

import com.ratnikov.crm.exports.ExcelColumnsHeaderWriter;
import com.ratnikov.crm.model.Departments;
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
public class ExportDepartmentsToXLSX implements ExcelColumnsHeaderWriter {

    private static final String[] columns = {"Id", "Department name", "City", "Manager"};
    private final List<Departments> departmentsList;

    private void writeCellsData(Sheet sheet) {
        int rowNum = 1;
        for (Departments department : departmentsList) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(department.getId());
            row.createCell(1).setCellValue(department.getDepartmentName());
            row.createCell(2).setCellValue(department.getCity());
            row.createCell(3).setCellValue(
                    department.getManagers().getFirstName() + " " + department.getManagers().getLastName());
            for (int i = 0; i < columns.length; i++) {
                sheet.autoSizeColumn(i);
            }
        }
    }

    public void export(HttpServletResponse response) throws IOException {
        Workbook workbook = new XSSFWorkbook();

        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
        String currentDateTime = dateFormatter.format(new Date());
        String headerValue = "departments_" + currentDateTime + ".xlsx";
        Sheet sheet = workbook.createSheet(headerValue);

        writeColumnsHeader(workbook, sheet, columns);
        writeCellsData(sheet);

        workbook.write(response.getOutputStream());
        workbook.close();
    }
}
