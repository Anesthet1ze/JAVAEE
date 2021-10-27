package com.info5059.casestudy.po;

import com.info5059.casestudy.vendor.Vendor;
import com.info5059.casestudy.vendor.VendorRepository;
import com.info5059.casestudy.product.Product;
import com.info5059.casestudy.product.ProductRepository;

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
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * PurchaseOrderPDFGenerator - a class for creating dynamic purchase order
 * output in PDF format using the iText 7 library
 *
 * @author Lisa
 */
public abstract class PurchaseOrderPDFGenerator extends AbstractPdfView {

        public static ByteArrayInputStream generatePO(String poid, PurchaseOrderDAO purchaseOrderDAO,
                        VendorRepository vendorRepository, ProductRepository productRepository) throws IOException {

                URL imageUrl = PurchaseOrderPDFGenerator.class.getResource("/static/assets/images/Logo.GIF");
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                PdfWriter writer = new PdfWriter(baos);
                // Initialize PDF document to be written to a stream not a file
                PdfDocument pdf = new PdfDocument(writer);
                // Document is the main object
                Document document = new Document(pdf);
                PdfFont font = PdfFontFactory.createFont(StandardFonts.HELVETICA);
                // add the image to the document
                Image img = new Image(ImageDataFactory.create(imageUrl)).scaleAbsolute(120, 120).setFixedPosition(75,
                                680);
                document.add(img);
                document.add(new Paragraph("\n\n"));
                Locale locale = new Locale("en", "US");
                NumberFormat formatter = NumberFormat.getCurrencyInstance(locale);
                BigDecimal subTot = new BigDecimal(0.0);

                try {
                        // Purchase Order Heading and PO# Sub-heading
                        PurchaseOrder purchaseOrder = purchaseOrderDAO.findOne(Long.parseLong(poid));
                        document.add(new Paragraph(String.format("Purchase Order")).setFont(font).setFontSize(24)
                                        .setMarginRight(75).setTextAlignment(TextAlignment.RIGHT).setBold());
                        document.add(new Paragraph("# " + poid).setFont(font).setFontSize(16).setBold()
                                        .setMarginRight(150).setMarginTop(-10).setTextAlignment(TextAlignment.RIGHT));
                        document.add(new Paragraph("\n\n"));

                        // Vendor Info
                        Optional<Vendor> opt = vendorRepository.findById(purchaseOrder.getVendorid());
                        if (opt.isPresent()) {
                                Vendor vendor = opt.get();
                                // Vendor table (2 column table)
                                Table venTable = new Table(2);
                                venTable.setWidth(new UnitValue(UnitValue.PERCENT, 40));
                                // Vendor table details
                                Cell cell = new Cell().add(new Paragraph("Vendor: ").setFont(font).setFontSize(12))
                                                .setTextAlignment(TextAlignment.RIGHT).setBorder(Border.NO_BORDER);
                                venTable.addCell(cell);
                                cell = new Cell().add(new Paragraph(vendor.getName()).setFont(font).setFontSize(12))
                                                .setTextAlignment(TextAlignment.LEFT)
                                                .setBackgroundColor(ColorConstants.LIGHT_GRAY)
                                                .setBorder(Border.NO_BORDER);
                                venTable.addCell(cell);
                                cell = new Cell().add(new Paragraph("").setFont(font).setFontSize(12))
                                                .setBorder(Border.NO_BORDER);
                                venTable.addCell(cell);
                                cell = new Cell().add(new Paragraph(vendor.getAddress1()).setFont(font).setFontSize(12))
                                                .setTextAlignment(TextAlignment.LEFT)
                                                .setBackgroundColor(ColorConstants.LIGHT_GRAY)
                                                .setBorder(Border.NO_BORDER);
                                venTable.addCell(cell);
                                cell = new Cell().add(new Paragraph("").setFont(font).setFontSize(12))
                                                .setBorder(Border.NO_BORDER);
                                venTable.addCell(cell);
                                cell = new Cell().add(new Paragraph(vendor.getCity()).setFont(font).setFontSize(12))
                                                .setTextAlignment(TextAlignment.LEFT)
                                                .setBackgroundColor(ColorConstants.LIGHT_GRAY)
                                                .setBorder(Border.NO_BORDER);
                                venTable.addCell(cell);
                                cell = new Cell().add(new Paragraph("").setFont(font).setFontSize(12))
                                                .setBorder(Border.NO_BORDER);
                                venTable.addCell(cell);
                                cell = new Cell().add(new Paragraph(vendor.getProvince()).setFont(font).setFontSize(12))
                                                .setTextAlignment(TextAlignment.LEFT)
                                                .setBackgroundColor(ColorConstants.LIGHT_GRAY)
                                                .setBorder(Border.NO_BORDER);
                                venTable.addCell(cell);
                                cell = new Cell().add(new Paragraph("").setFont(font).setFontSize(12))
                                                .setBorder(Border.NO_BORDER);
                                venTable.addCell(cell);
                                cell = new Cell().add(new Paragraph(vendor.getEmail()).setFont(font).setFontSize(12))
                                                .setTextAlignment(TextAlignment.LEFT)
                                                .setBackgroundColor(ColorConstants.LIGHT_GRAY)
                                                .setBorder(Border.NO_BORDER);
                                venTable.addCell(cell);
                                document.add(venTable);
                                document.add(new Paragraph("\n\n"));
                        }

                        // Product Line Item table (5 column table)
                        Table pliTable = new Table(5);
                        pliTable.setWidth(new UnitValue(UnitValue.PERCENT, 100));
                        // table headings
                        Cell cell = new Cell()
                                        .add(new Paragraph("Product Code").setFont(font).setFontSize(12).setBold())
                                        .setTextAlignment(TextAlignment.CENTER);
                        pliTable.addCell(cell);
                        cell = new Cell().add(new Paragraph("Description").setFont(font).setFontSize(12).setBold())
                                        .setTextAlignment(TextAlignment.CENTER);
                        pliTable.addCell(cell);
                        cell = new Cell().add(new Paragraph("Qty Sold").setFont(font).setFontSize(12).setBold())
                                        .setTextAlignment(TextAlignment.CENTER);
                        pliTable.addCell(cell);
                        cell = new Cell().add(new Paragraph("Price").setFont(font).setFontSize(12).setBold())
                                        .setTextAlignment(TextAlignment.CENTER);
                        pliTable.addCell(cell);
                        cell = new Cell().add(new Paragraph("Ext. Price").setFont(font).setFontSize(12).setBold())
                                        .setTextAlignment(TextAlignment.CENTER);
                        pliTable.addCell(cell);
                        // dump out the line items
                        for (PurchaseOrderLineitem line : purchaseOrder.getItems()) {
                                Optional<Product> optx = productRepository.findById(line.getProductid());
                                if (optx.isPresent()) {
                                        Product product = optx.get();
                                        cell = new Cell().add(new Paragraph(String.valueOf(product.getId()))
                                                        .setFont(font).setFontSize(12)
                                                        .setTextAlignment(TextAlignment.CENTER));
                                        pliTable.addCell(cell);
                                        cell = new Cell().add(new Paragraph(product.getName()).setFont(font)
                                                        .setFontSize(12).setTextAlignment(TextAlignment.CENTER));
                                        pliTable.addCell(cell);
                                        cell = new Cell().add(new Paragraph(String.valueOf(line.getQty())).setFont(font)
                                                        .setFontSize(12).setTextAlignment(TextAlignment.RIGHT));
                                        pliTable.addCell(cell);
                                        cell = new Cell().add(new Paragraph(formatter.format(product.getCostprice()))
                                                        .setFont(font).setFontSize(12)
                                                        .setTextAlignment(TextAlignment.RIGHT));
                                        pliTable.addCell(cell);
                                        BigDecimal extPrice = product.getCostprice().multiply(
                                                        new BigDecimal(line.getQty()),
                                                        new MathContext(8, RoundingMode.UP));
                                        cell = new Cell().add(new Paragraph(formatter.format(extPrice)).setFont(font)
                                                        .setFontSize(12).setTextAlignment(TextAlignment.RIGHT));
                                        pliTable.addCell(cell);
                                        subTot = subTot.add(extPrice, new MathContext(8, RoundingMode.UP));
                                }
                        }

                        // PO Sub Total
                        cell = new Cell(1, 4).add(new Paragraph("Sub Total: ").setFont(font).setFontSize(12).setBold())
                                        .setTextAlignment(TextAlignment.RIGHT).setBorder(Border.NO_BORDER);
                        pliTable.addCell(cell);
                        cell = new Cell().add(
                                        new Paragraph(formatter.format(subTot)).setFont(font).setFontSize(12).setBold())
                                        .setTextAlignment(TextAlignment.RIGHT);
                        pliTable.addCell(cell);
                        // PO Tax
                        BigDecimal tax = subTot.multiply(new BigDecimal(0.13), new MathContext(8, RoundingMode.UP));
                        cell = new Cell(1, 4).add(new Paragraph("Tax: ").setFont(font).setFontSize(12).setBold())
                                        .setTextAlignment(TextAlignment.RIGHT).setBorder(Border.NO_BORDER);
                        pliTable.addCell(cell);
                        cell = new Cell().add(
                                        new Paragraph(formatter.format(tax)).setFont(font).setFontSize(12).setBold())
                                        .setTextAlignment(TextAlignment.RIGHT);
                        pliTable.addCell(cell);
                        // PO Total
                        cell = new Cell(1, 4).add(new Paragraph("PO Total: ").setFont(font).setFontSize(12).setBold())
                                        .setTextAlignment(TextAlignment.RIGHT).setBorder(Border.NO_BORDER);
                        pliTable.addCell(cell);
                        cell = new Cell()
                                        .add(new Paragraph(formatter
                                                        .format(subTot.add(tax, new MathContext(8, RoundingMode.UP))))
                                                                        .setFont(font).setFontSize(12).setBold())
                                        .setTextAlignment(TextAlignment.RIGHT)
                                        .setBackgroundColor(ColorConstants.YELLOW);
                        pliTable.addCell(cell);
                        document.add(pliTable);
                        document.add(new Paragraph("\n\n"));
                        // PO date/timestamp
                        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd h:mm a");
                        document.add(new Paragraph(dateFormatter.format(purchaseOrder.getPodate()))
                                        .setTextAlignment(TextAlignment.CENTER));
                        document.close();

                } catch (Exception ex) {
                        Logger.getLogger(PurchaseOrderPDFGenerator.class.getName()).log(Level.SEVERE, null, ex);
                }

                // finally send stream back to the controller
                return new ByteArrayInputStream(baos.toByteArray());
        }
}