package com.ratnikov.crm.exports.pdf;

import com.lowagie.text.Document;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfPTable;
import com.ratnikov.crm.enums.Currency;
import com.ratnikov.crm.exports.ExportPDFRepository;
import com.ratnikov.crm.exports.PDFFileDesignRepository;
import com.ratnikov.crm.model.Product;
import lombok.AllArgsConstructor;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static com.lowagie.text.Element.ALIGN_CENTER;

@AllArgsConstructor
public class ExportProductsToPDF implements ExportPDFRepository, PDFFileDesignRepository {

    private final List<Product> productList;

    private static final String[] columns = {"Id", "Name of product", "Product type", "Selling price", "Purchase price", "Tax rate"};

    @Override
    public void writeTableData(PdfPTable table) {
        for (Product product : productList) {
            table.addCell(String.valueOf(product.getId()));
            table.addCell(product.getName());
            table.addCell(product.getProductType().getFullName());
            table.addCell(product.getSellingPrice().toString() + " " + Currency.RUB.name());
            table.addCell(product.getPurchasePrice().toString() + " " + Currency.RUB.name());
            table.addCell(product.getTaxRate().toString() + "%");
        }
    }

    @Override
    public void export(HttpServletResponse response) throws IOException {
        Document document = new Document(PageSize.A4);
        setupFileStyle(response, document);

        Paragraph p = new Paragraph("Products", setupFont());
        p.setAlignment(ALIGN_CENTER);

        document.add(p);

        PdfPTable table = new PdfPTable(6);
        table.setWidthPercentage(100f);
        table.setWidths(new float[] {1.0f, 4.0f, 3.0f, 3.0f, 3.0f, 3.0f});
        table.setSpacingBefore(10);

        writeTableHeader(table, columns);
        writeTableData(table);

        document.add(table);
        document.close();

    }
}