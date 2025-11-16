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

public class GetAllStudents extends HttpServlet
{
public void doGet(HttpServletRequest request,HttpServletResponse response)
{
response.setContentType("application/json");
PrintWriter pw = null;
Gson gson = new Gson();
SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
try
{
pw = response.getWriter();
StudentManagerInterface studentManager = StudentManager.getStudentManager();
Set<StudentInterface> students = studentManager.getStudents();
JsonObject jsonObject = new JsonObject();
jsonObject.addProperty("Result","OK");

jsonObject.add("Records",gson.toJsonTree(students));
//converting to flat object:

/*
JsonArray jsonArray = new JsonArray();
JsonObject jsonObject2;
for(StudentInterface student : students)
{
jsonObject2 = new JsonObject();
jsonObject2.addProperty("rollNo",student.getRollNo());
jsonObject2.addProperty("name",student.getName());
jsonObject2.addProperty("courseCode",student.getCourse().getCode());
jsonObject2.addProperty("courseTitle",student.getCourse().getTitle());
jsonObject2.addProperty("dateOfBirth",simpleDateFormat.format(student.getDateOfBirth()));
jsonObject2.addProperty("gender",student.getGender());
jsonObject2.addProperty("isIndain",student.getIsIndian());
jsonObject2.addProperty("fees",student.getFees());
jsonObject2.addProperty("enrollmentNumber",student.getEnrollmentNumber());
jsonObject2.addProperty("aadharCardNumber",student.getAadharCardNumber());
jsonArray.add(jsonObject2);
}
jsonObject.add("Records",jsonArray);
*/

jsonObject.addProperty("TotalRecordCount",students.size());

String jsonString = gson.toJson(jsonObject);
pw.write(jsonString);
pw.flush();

}catch(Exception exception)
{
pw.write("Error while fetching students");
pw.flush();
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