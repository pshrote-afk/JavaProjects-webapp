package com.thinking.machines.hr.servlets.courses;

import java.io.*;
import java.util.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import com.thinking.machines.enums.*;
import com.thinking.machines.hr.bl.exceptions.*;
import com.thinking.machines.hr.bl.interfaces.managers.*;
import com.thinking.machines.hr.bl.interfaces.pojo.*;
import com.thinking.machines.hr.bl.managers.*;
import com.thinking.machines.hr.bl.pojo.*;
import com.google.gson.*;

public class DeleteCourse extends HttpServlet
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

CourseManagerInterface courseManager = CourseManager.getCourseManager();

int code = Integer.parseInt(request.getParameter("code"));

courseManager.removeCourse(code);

JsonObject jsonObject = new JsonObject();
jsonObject.addProperty("Result","OK");
String jsonString = gson.toJson(jsonObject);
pw.write(jsonString);
pw.flush();
}catch(BLException blException)
{
String blExceptionMessage = blException.getGenericException();
if(blExceptionMessage.equals(""))	//means generic exception doesnt exist
{

blExceptionMessage = blException.getException("title");
if(blExceptionMessage.equals("")) 	//means it is not an exception related to title.
{
blExceptionMessage = blException.getException("code");
}

}

JsonObject jsonObject = new JsonObject();
jsonObject.addProperty("Result","ERROR");
jsonObject.addProperty("Message",blExceptionMessage);
String jsonString = gson.toJson(jsonObject); 
pw.write(jsonString);
pw.flush();
}
catch(Exception exception)
{
JsonObject jsonObject = new JsonObject();
jsonObject.addProperty("Result","ERROR");
jsonObject.addProperty("Message","Error while deleting course");
String jsonString = gson.toJson(jsonObject); 
pw.write(jsonString);
pw.flush();
}

}

}