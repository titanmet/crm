package com.ratnikov.crm.exports.xls;

import com.ratnikov.crm.exports.ExcelColumnsHeaderWriter;
import com.ratnikov.crm.model.Customer;
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
public class ExportCustomersToXLSX implements ExcelColumnsHeaderWriter {

    private static final String[] columns = {"Id", "Name", "Phone", "Email", "Description"};
    private final List<Customer> customersList;

    private void writeCellsData(Sheet sheet) {
        int rowNum = 1;
        for (Customer customer : customersList) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(customer.getId());
            row.createCell(1).setCellValue(customer.getName());
            row.createCell(2).setCellValue(customer.getPhone());
            row.createCell(3).setCellValue(customer.getEmail());
            row.createCell(4).setCellValue(customer.getDescription());
            for (int i = 0; i < columns.length; i++) {
                sheet.autoSizeColumn(i);
            }
        }
    }

    public void export(HttpServletResponse response) throws IOException {
        Workbook workbook = new XSSFWorkbook();

        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
        String currentDateTime = dateFormatter.format(new Date());
        String headerValue = "customers_" + currentDateTime + ".xlsx";
        Sheet sheet = workbook.createSheet(headerValue);

        writeColumnsHeader(workbook, sheet, columns);
        writeCellsData(sheet);

        workbook.write(response.getOutputStream());
        workbook.close();
    }
}
