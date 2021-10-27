package com.info5059.exercises.report;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import java.io.ByteArrayInputStream;

import javax.servlet.http.HttpServletRequest;

import com.info5059.exercises.employee.EmployeeRepository;
import com.info5059.exercises.expense.ExpenseRepository;
import com.itextpdf.io.IOException;

@CrossOrigin
@RestController
public class ReportPDFController {

    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private ExpenseRepository expenseRepository;
    @Autowired
    private ReportDAO reportDAO;

    @RequestMapping(value = "/ReportPDF", method = RequestMethod.GET, produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<InputStreamResource> streamPDF(HttpServletRequest request)
            throws IOException, java.io.IOException {

        String repid = request.getParameter("repid");

        // get formatted pdf as a stream
        ByteArrayInputStream bis = ReportPDFGenerator.generateReport(repid, reportDAO, employeeRepository,
                expenseRepository);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=report.pdf");

        // dump stream to browser
        return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(bis));

    }
}
