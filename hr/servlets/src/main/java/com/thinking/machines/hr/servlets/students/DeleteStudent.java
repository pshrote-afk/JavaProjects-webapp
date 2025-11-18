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

public class DeleteStudent extends HttpServlet
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
try
{
pw = response.getWriter();

StudentManagerInterface studentManager = StudentManager.getStudentManager();

String rollNo = request.getParameter("rollNo");

studentManager.removeStudent(rollNo);

JsonObject jsonObject = new JsonObject();
jsonObject.add("success",new JsonPrimitive(true));
jsonObject.addProperty("message","Student with roll no " + rollNo + " successfully deleted!");

String jsonString = gson.toJson(jsonObject);
pw.write(jsonString);
pw.flush();
}catch(BLException blException)
{
JsonObject jsonObject = new JsonObject();
jsonObject.add("success",new JsonPrimitive(false));
jsonObject.addProperty("message","Error while deleting student: " + blException.getGenericException());

JsonObject errorObject = new JsonObject();
List<String> list1 = blException.getProperties();
for(int k=0; k < list1.size();k++)
{
String errorName = list1.get(k);
String errorMessage = blException.getException(list1.get(k));
errorObject.addProperty(errorName, errorMessage);
}
jsonObject.add("serverErrors",errorObject);
String jsonString = gson.toJson(jsonObject); 
pw.write(jsonString);
pw.flush();
}
catch(Exception exception)
{
JsonObject jsonObject = new JsonObject();
jsonObject.add("success",new JsonPrimitive(false));
jsonObject.addProperty("message","Error while deleting student");
String jsonString = gson.toJson(jsonObject); 
pw.write(jsonString);
pw.flush();
}

}

}