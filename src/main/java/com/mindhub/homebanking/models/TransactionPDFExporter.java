package com.mindhub.homebanking.models;

import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

public class TransactionPDFExporter {
    private List<Transaction> transactions;
    private Account account;

    public TransactionPDFExporter(){}

    public TransactionPDFExporter(List<Transaction> listTransactions, Account account) {
        this.account = account;
        this.transactions = listTransactions;
    }
    private void writeTableHeader(PdfPTable table) {
        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(Color.BLUE);
        cell.setPadding(5);

        Font font = FontFactory.getFont(FontFactory.HELVETICA);
        font.setColor(Color.WHITE);

        cell.setPhrase(new Phrase("Date", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Amount", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Type", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Account balance", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Description", font));
        table.addCell(cell);
    }

    private void writeTableData(PdfPTable table) {
        for (Transaction transaction : transactions) {
            table.addCell(String.valueOf(transaction.getDate()));
            table.addCell(String.valueOf(transaction.getAmount()));
            table.addCell(transaction.getType().toString());
            table.addCell(String.valueOf(transaction.getAccountBalance()));
            table.addCell(transaction.getDescription());
        }
    }

    public void export(HttpServletResponse response) throws DocumentException, IOException {
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());

        document.open();
        Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        Font font2 = FontFactory.getFont(FontFactory.HELVETICA);

        font2.setSize(15);
        font.setSize(18);
        font2.setColor(Color.BLACK);
        font.setColor(Color.BLACK);

        Paragraph p1 = new Paragraph("Account: "+account.getNumber()+"  "+"Balance: "+account.getBalance(), font2);
        Paragraph p2 = new Paragraph("Transactions", font);

        p1.setAlignment(Paragraph.ALIGN_CENTER);
        p2.setAlignment(Paragraph.ALIGN_CENTER);

        document.add(p1);
        document.add(p2);

        PdfPTable table = new PdfPTable(5);
        table.setWidthPercentage(100f);
        table.setWidths(new float[] {3.0f, 2.0f, 2.0f, 2.0f, 3.5f});
        table.setSpacingBefore(10);

        writeTableHeader(table);
        writeTableData(table);

        document.add(table);

        document.close();

    }
}
