package com.thinking.machines.hr.servlets.students;

import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import com.thinking.machines.enums.*;
import com.thinking.machines.hr.bl.exceptions.*;
import com.thinking.machines.hr.bl.interfaces.managers.*;
import com.thinking.machines.hr.bl.interfaces.pojo.*;
import com.thinking.machines.hr.bl.managers.*;
import com.thinking.machines.hr.bl.pojo.*;
import com.google.gson.*;

public class AddStudent extends HttpServlet
{
public void doGet(HttpServletRequest request,HttpServletResponse response)
{
try
{
response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
}catch(Exception e)
{
//do nothing
}
}
public void doPost(HttpServletRequest request,HttpServletResponse response)
{
response.setContentType("application/json");
PrintWriter pw = null;
Gson gson = new Gson();
SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
try
{
pw = response.getWriter();

StudentManagerInterface studentManager = StudentManager.getStudentManager();
CourseManagerInterface courseManager = CourseManager.getCourseManager();
StudentInterface student;

String name = request.getParameter("name");
int courseCode = Integer.parseInt(request.getParameter("courseCode"));
java.util.Date dateOfBirth = simpleDateFormat.parse(request.getParameter("dateOfBirth"));
String gender = request.getParameter("gender");
String isIndian = request.getParameter("isIndian");
if(isIndian==null) isIndian="N";	//if null, means unchecked
BigDecimal fees = new BigDecimal(request.getParameter("fees"));
String enrollmentNumber = request.getParameter("enrollmentNumber");
String aadharCardNumber = request.getParameter("aadharCardNumber");


//validate 1. course code 2. enrollment 3. aadhar
boolean courseCodeExists = courseManager.courseCodeExists(courseCode);
boolean enrollmentNumberExists = studentManager.studentEnrollmentNumberExists(enrollmentNumber);
boolean aadharCardNumberExists = studentManager.studentAadharCardNumberExists(aadharCardNumber);
// enter if block if any of above three are problematic - send back form with filled data, and error messages
if(courseCodeExists==false || enrollmentNumberExists==true || aadharCardNumberExists==true)
{
JsonObject jsonObject = new JsonObject();
jsonObject.add("success",new JsonPrimitive(false));

JsonObject errorObject = new JsonObject();
if(courseCodeExists==false) errorObject.addProperty("courseCode", "Invalid course");
if(enrollmentNumberExists==true) errorObject.addProperty("enrollmentNumber", "Enrollment number already exists: " + enrollmentNumber);
if(aadharCardNumberExists==true) errorObject.addProperty("aadharCardNumber", "Aadhar card number already exists: " + aadharCardNumber);

jsonObject.add("serverErrors",errorObject);
String jsonString = gson.toJson(jsonObject);
pw.write(jsonString);
pw.flush();
return;
}

student = new Student();
student.setName(name);

CourseInterface course = courseManager.getCourseByCode(courseCode);
student.setCourse(course);

student.setDateOfBirth(dateOfBirth);
student.setGender(gender.equalsIgnoreCase("M") ? GENDER.MALE : GENDER.FEMALE); 
student.setIsIndian(isIndian.equalsIgnoreCase("Y") ? true : false);
student.setFees(fees);
student.setEnrollmentNumber(enrollmentNumber);
student.setAadharCardNumber(aadharCardNumber);
studentManager.addStudent(student);

JsonObject jsonObject = new JsonObject();
jsonObject.add("success",new JsonPrimitive(true));
jsonObject.addProperty("message","Student with roll no " + student.getRollNo() + " successfully added!");

String jsonString = gson.toJson(jsonObject);
pw.write(jsonString);
pw.flush();
}catch(BLException blException)
{
JsonObject jsonObject = new JsonObject();
jsonObject.add("success",new JsonPrimitive(false));
jsonObject.addProperty("message","Error while adding student: " + blException.getGenericException());
String jsonString = gson.toJson(jsonObject); 
pw.write(jsonString);
pw.flush();
}
catch(Exception exception)
{
JsonObject jsonObject = new JsonObject();
jsonObject.add("success",new JsonPrimitive(false));
jsonObject.addProperty("message","Error while adding student");
String jsonString = gson.toJson(jsonObject); 
pw.write(jsonString);
pw.flush();
}

}

}