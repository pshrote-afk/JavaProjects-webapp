package com.thinking.machines.hr.servlets.students;

import com.google.gson.*;
import com.thinking.machines.enums.*;
import com.thinking.machines.hr.bl.exceptions.*;
import com.thinking.machines.hr.bl.interfaces.managers.*;
import com.thinking.machines.hr.bl.interfaces.pojo.*;
import com.thinking.machines.hr.bl.managers.*;
import com.thinking.machines.hr.bl.pojo.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.*;
import java.math.*;
import java.text.*;
import java.util.*;

public class UpdateStudent extends HttpServlet {
  public void doGet(HttpServletRequest request, HttpServletResponse response) {
    try {
      response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
    } catch (Exception e) {
      // do nothing
    }
  }

  public void doPost(HttpServletRequest request, HttpServletResponse response) {
    response.setContentType("application/json");
    PrintWriter pw = null;
    Gson gson = new Gson();
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
    try {
      pw = response.getWriter();

      StudentManagerInterface studentManager = StudentManager.getStudentManager();
      CourseManagerInterface courseManager = CourseManager.getCourseManager();
      StudentInterface student;

      String rollNo = request.getParameter("rollNo");
      String name = request.getParameter("name");
      int courseCode = Integer.parseInt(request.getParameter("courseCode"));
      java.util.Date dateOfBirth = simpleDateFormat.parse(request.getParameter("dateOfBirth"));
      String gender = request.getParameter("gender");
      String isIndian = request.getParameter("isIndian");
      if (isIndian == null) isIndian = "N"; // if null, means unchecked
      BigDecimal fees = new BigDecimal(request.getParameter("fees"));
      String enrollmentNumber = request.getParameter("enrollmentNumber");
      String aadharCardNumber = request.getParameter("aadharCardNumber");

      student = new Student();
      student.setRollNo(rollNo);
      student.setName(name);

      CourseInterface course = courseManager.getCourseByCode(courseCode);
      student.setCourse(course);
	
      student.setDateOfBirth(dateOfBirth);
      student.setGender(gender.equalsIgnoreCase("M") ? GENDER.MALE : GENDER.FEMALE);
      student.setIsIndian(isIndian.equalsIgnoreCase("Y") ? true : false);
      student.setFees(fees);
      student.setEnrollmentNumber(enrollmentNumber);
      student.setAadharCardNumber(aadharCardNumber);
      studentManager.updateStudent(student);

      JsonObject jsonObject = new JsonObject();
      jsonObject.add("success", new JsonPrimitive(true));
      jsonObject.addProperty(
          "message", "Student with roll no " + student.getRollNo() + " successfully updated!");

      String jsonString = gson.toJson(jsonObject);
      pw.write(jsonString);
      pw.flush();
    } catch (BLException blException) {
      JsonObject jsonObject = new JsonObject();
      jsonObject.add("success", new JsonPrimitive(false));
      jsonObject.addProperty(
          "message", "Error while updating student: " + blException.getGenericException());

      JsonObject errorObject = new JsonObject();
      List<String> list1 = blException.getProperties();
      for (int k = 0; k < list1.size(); k++) {
        String errorName = list1.get(k);
        String errorMessage = blException.getException(list1.get(k));
        errorObject.addProperty(errorName, errorMessage);
      }
      jsonObject.add("serverErrors", errorObject);
      String jsonString = gson.toJson(jsonObject);
      pw.write(jsonString);
      pw.flush();
    } catch (Exception exception) {
      JsonObject jsonObject = new JsonObject();
      jsonObject.add("success", new JsonPrimitive(false));
      jsonObject.addProperty("message", "Error while updating student");
      String jsonString = gson.toJson(jsonObject);
      pw.write(jsonString);
      pw.flush();
    }
  }
}
