package com.ratnikov.crm.exports.pdf;

import com.lowagie.text.Document;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfPTable;
import com.ratnikov.crm.exports.ExportPDFRepository;
import com.ratnikov.crm.exports.PDFFileDesignRepository;
import com.ratnikov.crm.model.Supplier;
import lombok.AllArgsConstructor;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static com.lowagie.text.Element.ALIGN_CENTER;

@AllArgsConstructor
public class ExportSuppliersToPDF implements ExportPDFRepository, PDFFileDesignRepository {

    private final List<Supplier> supplierList;

    private static final String[] columns =
            {"Id", "Name of Supplier", "Mode of transport", "Max length", "Max weight", "Min length", "Min weight", "Transport capacity", "Activity status"};

    @Override
    public void writeTableData(PdfPTable table) {
        for (Supplier supplier : supplierList) {
            table.addCell(String.valueOf(supplier.getSupplierId()));
            table.addCell(String.valueOf(supplier.getSupplierName()));
            table.addCell(String.valueOf(supplier.getModeOfTransportCode().getFullName()));
            table.addCell(String.valueOf(supplier.getModeOfTransportCode().getMaxLength()));
            table.addCell(String.valueOf(supplier.getModeOfTransportCode().getMaxWeight()));
            table.addCell(String.valueOf(supplier.getModeOfTransportCode().getMinLength()));
            table.addCell(String.valueOf(supplier.getModeOfTransportCode().getMinWeight()));
            table.addCell(String.valueOf(supplier.getModeOfTransportCode().getTransportCapacity()));
            table.addCell(String.valueOf(supplier.getActivityStatus()));
        }
    }

    @Override
    public void export(HttpServletResponse response) throws IOException {
        Document document = new Document(PageSize.A4);
        setupFileStyle(response, document);

        Paragraph p = new Paragraph("Suppliers", setupFont());
        p.setAlignment(ALIGN_CENTER);

        document.add(p);

        PdfPTable table = new PdfPTable(9);
        table.setWidthPercentage(100f);
        table.setWidths(new float[] {1.0f, 2.0f, 2.0f, 2.0f, 2.0f, 2.0f, 2.0f, 2.0f, 2.0f});
        table.setSpacingBefore(10);

        writeTableHeader(table, columns);
        writeTableData(table);

        document.add(table);
        document.close();

    }
}
