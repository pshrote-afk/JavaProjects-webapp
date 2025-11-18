package com.thinking.machines.hr.servlets.students;

import java.io.*;
import java.util.*;
import java.text.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import com.thinking.machines.hr.bl.interfaces.managers.*;
import com.thinking.machines.hr.bl.interfaces.pojo.*;
import com.thinking.machines.hr.bl.managers.*;
import com.thinking.machines.hr.bl.pojo.*;
import com.google.gson.*;
//iText7
import com.itextpdf.io.font.constants.*;
import com.itextpdf.io.image.*;
import com.itextpdf.kernel.font.*;
import com.itextpdf.kernel.pdf.*;
import com.itextpdf.layout.*;
import com.itextpdf.layout.element.*; // for Paragraph
import com.itextpdf.layout.property.*;

public class ExportStudentsToPdf extends HttpServlet
{
public void doGet(HttpServletRequest request,HttpServletResponse response)
{
response.setContentType("application/pdf");
response.setHeader("Content-Disposition","attachment; filename=\"students.pdf\"");
try
{
OutputStream outputStream = response.getOutputStream();
StudentManagerInterface studentManager = StudentManager.getStudentManager();
Set<StudentInterface> students = studentManager.getStudents();
java.util.List<StudentInterface> studentsList = new ArrayList<>(students);	//converted to a list to be able to use indexing
StudentInterface student;

//SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMM dd, yyyy");


 PdfWriter pdfWriter = new PdfWriter(outputStream);
      PdfDocument pdfDocument = new PdfDocument(pdfWriter);
      Document document = new Document(pdfDocument);
      int r = 0;
      int sno = 0;
      int pageSize = 25;
      int pageNumber = 0;
      boolean newPage = true;
      Table pdfTable = null;
      Div lineSpacer = new Div().setHeight(20);
      PdfFont timesRoman = PdfFontFactory.createFont("Times-Roman");
      document.setMargins(15, 15, 15, 15);
      while (r < studentsList.size()) {
        if (newPage == true) // create header,
        {
          Image companyLogo =
              new Image(ImageDataFactory.create(getServletContext().getRealPath("/images/logo.png")));
          companyLogo.setFixedPosition(0 + 5, 800 - 10);
          companyLogo.scaleToFit(50, 50);
          Paragraph companyName = new Paragraph().setTextAlignment(TextAlignment.CENTER);
          companyName.add("Company Name");

          pageNumber++;
          Paragraph pageNumberParagraph = new Paragraph().setTextAlignment(TextAlignment.RIGHT);
          pageNumberParagraph.add(String.valueOf(pageNumber));

          Paragraph reportTitle = new Paragraph();
          reportTitle.add("Students").setTextAlignment(TextAlignment.CENTER);

          pdfTable = new Table(10);
          pdfTable.setHorizontalAlignment(HorizontalAlignment.LEFT);
          pdfTable.addHeaderCell(
              new Cell().add(new Paragraph(("Sr.No.")).setFont(timesRoman).setFontSize(14)));
          pdfTable.addHeaderCell(
              new Cell().add(new Paragraph(("Roll no.")).setFont(timesRoman).setFontSize(14)));
          pdfTable.addHeaderCell(
              new Cell().add(new Paragraph(("Name")).setFont(timesRoman).setFontSize(14)));
          pdfTable.addHeaderCell(
              new Cell().add(new Paragraph(("Course")).setFont(timesRoman).setFontSize(14)));
          pdfTable.addHeaderCell(
              new Cell().add(new Paragraph(("Date of birth")).setFont(timesRoman).setFontSize(14)));
          pdfTable.addHeaderCell(
              new Cell().add(new Paragraph(("Gender")).setFont(timesRoman).setFontSize(14)));
          pdfTable.addHeaderCell(
              new Cell().add(new Paragraph(("Is Indian")).setFont(timesRoman).setFontSize(14)));
          pdfTable.addHeaderCell(
              new Cell().add(new Paragraph(("Fees")).setFont(timesRoman).setFontSize(14)));
          pdfTable.addHeaderCell(
              new Cell().add(new Paragraph(("Enrollment Number")).setFont(timesRoman).setFontSize(14)));
          pdfTable.addHeaderCell(
              new Cell().add(new Paragraph(("Aadhar Card Number")).setFont(timesRoman).setFontSize(14)));

          document.add(companyLogo);
          document.add(companyName);
          document.add(pageNumberParagraph);
          document.add(reportTitle);
          newPage = false;
        }
        sno++;
        // add row
        student = studentsList.get(r);
        pdfTable.addCell(
            new Cell()
                .add(new Paragraph(String.valueOf(sno)))
                .setTextAlignment(TextAlignment.RIGHT)
                .setFont(timesRoman));
        pdfTable.addCell(
            new Cell()
                .add(new Paragraph(student.getRollNo()))
                .setTextAlignment(TextAlignment.LEFT)
                .setFont(timesRoman));
        pdfTable.addCell(
            new Cell()
                .add(new Paragraph(student.getName()))
                .setTextAlignment(TextAlignment.LEFT)
                .setFont(timesRoman)
                .setItalic());
        pdfTable.addCell(
            new Cell()
                .add(new Paragraph(student.getCourse().getTitle()))
                .setTextAlignment(TextAlignment.LEFT)
                .setFont(timesRoman)
                .setItalic());
        pdfTable.addCell(
            new Cell()
                .add(new Paragraph(simpleDateFormat.format(student.getDateOfBirth())))
                .setTextAlignment(TextAlignment.LEFT)
                .setFont(timesRoman)
                .setItalic());
        pdfTable.addCell(
            new Cell()
                .add(new Paragraph(String.valueOf(student.getGender())))
                .setTextAlignment(TextAlignment.CENTER)
                .setFont(timesRoman)
                .setItalic());  
        pdfTable.addCell(
            new Cell()
                .add(new Paragraph(String.valueOf(String.valueOf(student.getIsIndian()))))
                .setTextAlignment(TextAlignment.LEFT)
                .setFont(timesRoman)
                .setItalic());
        pdfTable.addCell(
            new Cell()
                .add(new Paragraph(student.getFees().toPlainString()))
                .setTextAlignment(TextAlignment.LEFT)
                .setFont(timesRoman)
                .setItalic());
        pdfTable.addCell(
            new Cell()
                .add(new Paragraph(student.getEnrollmentNumber()))
                .setTextAlignment(TextAlignment.LEFT)
                .setFont(timesRoman)
                .setItalic()); 
        pdfTable.addCell(
            new Cell()
                .add(new Paragraph(student.getAadharCardNumber()))
                .setTextAlignment(TextAlignment.LEFT)
                .setFont(timesRoman)
                .setItalic());               

        if (sno % pageSize == 0 || sno == studentsList.size()) {
          // create footer
          document.add(pdfTable);
          document.add(lineSpacer);
          document.add(
              new Paragraph("Software by: Paras Shrote\nwww.github.com/pshrote-afk")
                  .setFont(timesRoman)
                  .setFontSize(10));
          //
          if (sno < studentsList.size()) {
            pdfDocument.addNewPage();
            document.add(new AreaBreak(AreaBreakType.NEXT_PAGE));
          }
          newPage = true;
        }

        r++;
      }
      document.close();

outputStream.flush();

}catch(Exception exception)
{
System.out.println(exception.getMessage());
}
}
public void doPost(HttpServletRequest request,HttpServletResponse response)
{
try
{
response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
}catch(Exception e)
{
//do nothing
}
}
}