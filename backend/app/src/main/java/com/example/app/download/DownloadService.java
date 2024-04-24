package com.example.app.download;

import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.Style;
import com.itextpdf.layout.borders.SolidBorder;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.TextAlignment;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Service
public class DownloadService {
    public ByteArrayResource downloadSimpleFile() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfWriter writer = new PdfWriter(baos);
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);
        PdfFont font = PdfFontFactory.createFont("Helvetica");
        document.setFont(font);
        document.add(new Paragraph("Hello, World!"));
        document.close();

        // Tworzenie zasobu ByteArrayResource z plikiem PDF
        ByteArrayResource resource = new ByteArrayResource(baos.toByteArray());

        return resource;
    }

    public ByteArrayResource downloadFile() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfWriter writer = new PdfWriter(baos);
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf, PageSize.A4);

        // Ustawienia stylów
        PdfFont font = PdfFontFactory.createFont("Helvetica");
        Style headingStyle = new Style()
                .setFont(font)
                .setBold()
                .setFontSize(20)
                .setTextAlignment(TextAlignment.CENTER)
                .setMarginBottom(10);
        Style cellStyle = new Style()
                .setFont(font)
                .setFontSize(12)
                .setPadding(5)
                .setBorder(new SolidBorder(new DeviceRgb(169, 169, 169), 1));

        // Dodanie tytułu
        Paragraph title = new Paragraph("Raport")
                .addStyle(headingStyle);
        document.add(title);

        // Dodanie tabeli
        Table table = new Table(new float[]{1, 1, 1});
        table.addCell(new Cell().add(new Paragraph("ID")).addStyle(cellStyle));
        table.addCell(new Cell().add(new Paragraph("Name")).addStyle(cellStyle));
        table.addCell(new Cell().add(new Paragraph("Age")).addStyle(cellStyle));
        table.addCell(new Cell().add(new Paragraph("1")).addStyle(cellStyle));
        table.addCell(new Cell().add(new Paragraph("Adrian")).addStyle(cellStyle));
        table.addCell(new Cell().add(new Paragraph("30")).addStyle(cellStyle));
        document.add(table);

        // Zakończenie dokumentu
        document.close();

        // Tworzenie zasobu ByteArrayResource z plikiem PDF
        ByteArrayResource resource = new ByteArrayResource(baos.toByteArray());

        return resource;
    }

    public ByteArrayResource downloadZipFile() throws IOException {
        ByteArrayOutputStream zipBaos = new ByteArrayOutputStream();
        try (ZipOutputStream zipOutputStream = new ZipOutputStream(zipBaos)) {
            // Dodawanie pierwszego pliku PDF do archiwum ZIP
            addPdfToZip(zipOutputStream, "first_pdf.pdf", "Hello, World from First PDF!");

            // Dodawanie drugiego pliku PDF do archiwum ZIP
            addPdfToZip(zipOutputStream, "second_pdf.pdf", "Hello, World from Second PDF!");
        }

        // Tworzenie zasobu ByteArrayResource z archiwum ZIP
        ByteArrayResource resource = new ByteArrayResource(zipBaos.toByteArray());

        return resource;
    }

    private void addPdfToZip(ZipOutputStream zipOutputStream, String entryName, String content) throws IOException {
        // Tworzenie pliku PDF w pamięci
        ByteArrayOutputStream pdfBaos = new ByteArrayOutputStream();
        try (PdfWriter writer = new PdfWriter(pdfBaos)) {
            try (PdfDocument pdf = new PdfDocument(writer)) {
                try (Document document = new Document(pdf)) {
                    // Dodawanie zawartości do pliku PDF
                    document.add(new Paragraph(content));
                }
            }
        }

        // Dodawanie pliku PDF do archiwum ZIP
        zipOutputStream.putNextEntry(new ZipEntry(entryName));
        zipOutputStream.write(pdfBaos.toByteArray());
        zipOutputStream.closeEntry();
    }
}
