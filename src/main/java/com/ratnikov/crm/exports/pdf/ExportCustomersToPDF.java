package com.ratnikov.crm.exports.pdf;

import com.lowagie.text.Document;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfPTable;
import com.ratnikov.crm.exports.ExportPDFRepository;
import com.ratnikov.crm.exports.PDFFileDesignRepository;
import com.ratnikov.crm.model.Customer;
import lombok.AllArgsConstructor;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static com.lowagie.text.Element.ALIGN_CENTER;

@AllArgsConstructor
public class ExportCustomersToPDF implements ExportPDFRepository, PDFFileDesignRepository {

    private final List<Customer> customersList;

    private static final String[] columns = {"Id", "Name", "Phone", "Email", "Description"};

    @Override
    public void writeTableData(PdfPTable table) {
        for (Customer customers : customersList) {
            table.addCell(String.valueOf(customers.getId()));
            table.addCell(String.valueOf(customers.getName()));
            table.addCell(String.valueOf(customers.getPhone()));
            table.addCell(String.valueOf(customers.getEmail()));
            table.addCell(String.valueOf(customers.getDescription()));
        }
    }

    @Override
    public void export(HttpServletResponse response) throws IOException {
        Document document = new Document(PageSize.A4);
        setupFileStyle(response, document);

        Paragraph p = new Paragraph("Customers", setupFont());
        p.setAlignment(ALIGN_CENTER);

        document.add(p);

        PdfPTable table = new PdfPTable(5);
        table.setWidthPercentage(100f);
        table.setWidths(new float[] {1.5f, 3.5f, 3.0f, 3.0f, 3.0f});
        table.setSpacingBefore(10);

        writeTableHeader(table, columns);
        writeTableData(table);

        document.add(table);
        document.close();

    }
}
