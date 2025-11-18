package com.thinking.machines.hr.servlets.courses;

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

public class ExportCoursesToPdf extends HttpServlet
{
public void doGet(HttpServletRequest request,HttpServletResponse response)
{
response.setContentType("application/pdf");
response.setHeader("Content-Disposition","attachment; filename=\"courses.pdf\"");
try
{
OutputStream outputStream = response.getOutputStream();
CourseManagerInterface courseManager = CourseManager.getCourseManager();
Set<CourseInterface> courses = courseManager.getCourses();
java.util.List<CourseInterface> coursesList = new ArrayList<>(courses);	//converted to a list to be able to use indexing

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
      while (r < coursesList.size()) {
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
          reportTitle.add("Courses").setTextAlignment(TextAlignment.CENTER);

          pdfTable = new Table(2);
          pdfTable.setHorizontalAlignment(HorizontalAlignment.CENTER);
          pdfTable.addHeaderCell(
              new Cell().add(new Paragraph(("Sr.No.")).setFont(timesRoman).setFontSize(16)));
          pdfTable.addHeaderCell(
              new Cell().add(new Paragraph(("Course")).setFont(timesRoman).setFontSize(16)));

          document.add(companyLogo);
          document.add(companyName);
          document.add(pageNumberParagraph);
          document.add(reportTitle);
          newPage = false;
        }
        sno++;
        // add row

        pdfTable.addCell(
            new Cell()
                .add(new Paragraph(String.valueOf(sno)))
                .setTextAlignment(TextAlignment.RIGHT)
                .setFont(timesRoman));
        pdfTable.addCell(
            new Cell()
                .add(new Paragraph(coursesList.get(r).getTitle()))
                .setTextAlignment(TextAlignment.LEFT)
                .setFont(timesRoman)
                .setItalic());

        if (sno % pageSize == 0 || sno == coursesList.size()) {
          // create footer
          document.add(pdfTable);
          document.add(lineSpacer);
          document.add(
              new Paragraph("Software by: Paras Shrote\nwww.github.com/pshrote-afk")
                  .setFont(timesRoman)
                  .setFontSize(10));
          //
          if (sno < coursesList.size()) {
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