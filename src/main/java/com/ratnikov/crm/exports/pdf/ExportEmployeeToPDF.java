package com.ratnikov.crm.exports.pdf;

import com.lowagie.text.Document;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfPTable;
import com.ratnikov.crm.enums.Currency;
import com.ratnikov.crm.exports.ExportPDFRepository;
import com.ratnikov.crm.exports.PDFFileDesignRepository;
import com.ratnikov.crm.model.Employee;
import lombok.AllArgsConstructor;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static com.lowagie.text.Element.ALIGN_CENTER;

@AllArgsConstructor
public class ExportEmployeeToPDF implements ExportPDFRepository, PDFFileDesignRepository {

    private final List<Employee> employeesList;

    private static final String[] columns = {"First Name", "Last Name", "Department", "Salary"};

    @Override
    public void writeTableData(PdfPTable table) {
        for (Employee employee : employeesList) {
            table.addCell(String.valueOf(employee.getFirstName()));
            table.addCell(employee.getLastName());
            table.addCell(employee.getDepartment().getDepartmentName());
            table.addCell(employee.getSalary().toString() + " " + Currency.RUB.name());
        }
    }

    @Override
    public void export(HttpServletResponse response) throws IOException {
        Document document = new Document(PageSize.A4);
        setupFileStyle(response, document);

        Paragraph p = new Paragraph("Employees", setupFont());
        p.setAlignment(ALIGN_CENTER);

        document.add(p);

        PdfPTable table = new PdfPTable(4);
        table.setWidthPercentage(100f);
        table.setWidths(new float[] {1.5f, 3.5f, 3.0f, 3.0f});
        table.setSpacingBefore(10);

        writeTableHeader(table, columns);
        writeTableData(table);

        document.add(table);
        document.close();

    }
}
