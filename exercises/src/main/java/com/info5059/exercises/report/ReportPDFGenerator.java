package com.info5059.exercises.report;

import com.info5059.exercises.employee.Employee;
import com.info5059.exercises.employee.EmployeeRepository;
import com.info5059.exercises.expense.Expense;
import com.info5059.exercises.expense.ExpenseRepository;

import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.UnitValue;

import org.springframework.web.servlet.view.document.AbstractPdfView;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.net.URL;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * ReportPDFGenerator - a class for creating dynamic expense report output in
 * PDF format using the iText 7 library
 *
 */
public abstract class ReportPDFGenerator extends AbstractPdfView {

        public static ByteArrayInputStream generateReport(String repid, ReportDAO repDAO,
                        EmployeeRepository employeeRepository, ExpenseRepository expenseRepository) throws IOException {

                URL imageUrl = ReportPDFGenerator.class.getResource("/static/images/Expenses.png");
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                PdfWriter writer = new PdfWriter(baos);
                // Initialize PDF document to be written to a stream not a file
                PdfDocument pdf = new PdfDocument(writer);
                // Document is the main object
                Document document = new Document(pdf);
                PdfFont font = PdfFontFactory.createFont(StandardFonts.HELVETICA);
                // add the image to the document
                Image img = new Image(ImageDataFactory.create(imageUrl)).scaleAbsolute(120, 40).setFixedPosition(80,
                                710);

                document.add(img);
                // now let's add a big heading
                document.add(new Paragraph("\n\n"));
                Locale locale = new Locale("en", "US");
                NumberFormat formatter = NumberFormat.getCurrencyInstance(locale);
                BigDecimal tot = new BigDecimal(0.0);

                try {
                        // Expenses Heading and Report # Sub-heading
                        Report report = repDAO.findOne(Long.parseLong(repid));
                        document.add(new Paragraph(String.format("Expenses")).setFont(font).setFontSize(24)
                                        .setMarginRight(75).setTextAlignment(TextAlignment.RIGHT).setBold());
                        document.add(new Paragraph("Report# " + repid).setFont(font).setFontSize(16).setBold()
                                        .setMarginRight(90).setMarginTop(-10).setTextAlignment(TextAlignment.RIGHT));
                        document.add(new Paragraph("\n\n"));

                        // Employee Info
                        Optional<Employee> opt = employeeRepository.findById(report.getEmployeeid());
                        if (opt.isPresent()) {
                                Employee employee = opt.get();
                                // Employee table (2 column table)
                                Table empTable = new Table(2);
                                empTable.setWidth(new UnitValue(UnitValue.PERCENT, 40));
                                // employee table details
                                Cell cell = new Cell().add(new Paragraph("Employee: ").setFont(font).setFontSize(12))
                                                .setTextAlignment(TextAlignment.RIGHT).setBorder(Border.NO_BORDER);
                                empTable.addCell(cell);
                                cell = new Cell()
                                                .add(new Paragraph(employee.getFirstname()).setFont(font)
                                                                .setFontSize(12))
                                                .setTextAlignment(TextAlignment.LEFT)
                                                .setBackgroundColor(ColorConstants.LIGHT_GRAY)
                                                .setBorder(Border.NO_BORDER);
                                empTable.addCell(cell);
                                cell = new Cell().add(new Paragraph("").setFont(font).setFontSize(12))
                                                .setBorder(Border.NO_BORDER);
                                empTable.addCell(cell);
                                cell = new Cell()
                                                .add(new Paragraph(employee.getLastname()).setFont(font)
                                                                .setFontSize(12))
                                                .setTextAlignment(TextAlignment.LEFT)
                                                .setBackgroundColor(ColorConstants.LIGHT_GRAY)
                                                .setBorder(Border.NO_BORDER);
                                empTable.addCell(cell);
                                cell = new Cell().add(new Paragraph("").setFont(font).setFontSize(12))
                                                .setBorder(Border.NO_BORDER);
                                empTable.addCell(cell);
                                cell = new Cell().add(new Paragraph(employee.getEmail()).setFont(font).setFontSize(12))
                                                .setTextAlignment(TextAlignment.LEFT)
                                                .setBackgroundColor(ColorConstants.LIGHT_GRAY)
                                                .setBorder(Border.NO_BORDER);
                                empTable.addCell(cell);
                                document.add(empTable);
                                document.add(new Paragraph("\n\n"));
                        }

                        // Expense table (4 column table)
                        Table expTable = new Table(4);
                        expTable.setWidth(new UnitValue(UnitValue.PERCENT, 100));
                        // table headings
                        Cell cell = new Cell().add(new Paragraph("Id").setFont(font).setFontSize(12).setBold())
                                        .setTextAlignment(TextAlignment.CENTER);
                        expTable.addCell(cell);
                        cell = new Cell().add(new Paragraph("Date Incurred").setFont(font).setFontSize(12).setBold())
                                        .setTextAlignment(TextAlignment.CENTER);
                        expTable.addCell(cell);
                        cell = new Cell().add(new Paragraph("Description").setFont(font).setFontSize(12).setBold())
                                        .setTextAlignment(TextAlignment.CENTER);
                        expTable.addCell(cell);
                        cell = new Cell().add(new Paragraph("Amount").setFont(font).setFontSize(12).setBold())
                                        .setTextAlignment(TextAlignment.CENTER);
                        expTable.addCell(cell);
                        // dump out the line items
                        for (ReportItem line : report.getItems()) {
                                Optional<Expense> optx = expenseRepository.findById(line.getExpenseid());
                                if (optx.isPresent()) {
                                        Expense expense = optx.get();
                                        cell = new Cell().add(new Paragraph(String.valueOf(expense.getId()))
                                                        .setFont(font).setFontSize(12)
                                                        .setTextAlignment(TextAlignment.CENTER));
                                        expTable.addCell(cell);
                                        cell = new Cell().add(new Paragraph(expense.getDateincurred()).setFont(font)
                                                        .setFontSize(12).setTextAlignment(TextAlignment.CENTER));
                                        expTable.addCell(cell);
                                        cell = new Cell().add(new Paragraph(expense.getDescription()).setFont(font)
                                                        .setFontSize(12).setTextAlignment(TextAlignment.LEFT));
                                        expTable.addCell(cell);
                                        cell = new Cell().add(new Paragraph(formatter.format(expense.getAmount()))
                                                        .setFont(font).setFontSize(12)
                                                        .setTextAlignment(TextAlignment.RIGHT));
                                        expTable.addCell(cell);
                                        tot = tot.add(expense.getAmount(), new MathContext(8, RoundingMode.UP));
                                }
                        }

                        // report total
                        cell = new Cell(1, 3)
                                        .add(new Paragraph("Report Total: ").setFont(font).setFontSize(12).setBold())
                                        .setTextAlignment(TextAlignment.RIGHT).setBorder(Border.NO_BORDER);
                        expTable.addCell(cell);
                        cell = new Cell()
                                        .add(new Paragraph(formatter.format(tot)).setFont(font).setFontSize(12)
                                                        .setBold())
                                        .setTextAlignment(TextAlignment.RIGHT)
                                        .setBackgroundColor(ColorConstants.YELLOW);
                        expTable.addCell(cell);
                        document.add(expTable);
                        document.add(new Paragraph("\n\n"));
                        // report date/timestamp
                        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd h:mm a");
                        document.add(new Paragraph(dateFormatter.format(LocalDateTime.now()))
                                        .setTextAlignment(TextAlignment.CENTER));
                        document.close();

                } catch (Exception ex) {
                        Logger.getLogger(ReportPDFGenerator.class.getName()).log(Level.SEVERE, null, ex);
                }

                // finally send stream back to the controller
                return new ByteArrayInputStream(baos.toByteArray());
        }
}