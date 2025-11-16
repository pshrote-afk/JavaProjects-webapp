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

public class GetAllCourses extends HttpServlet
{
public void doGet(HttpServletRequest request,HttpServletResponse response)
{
response.setContentType("application/json");
PrintWriter pw = null;
Gson gson = new Gson();
try
{
pw = response.getWriter();
CourseManagerInterface courseManager = CourseManager.getCourseManager();
Set<CourseInterface> courses = courseManager.getCourses();
JsonObject jsonObject = new JsonObject();
jsonObject.addProperty("Result","OK");

jsonObject.add("Records",gson.toJsonTree(courses));

jsonObject.addProperty("TotalRecordCount",courses.size());

String jsonString = gson.toJson(jsonObject);
pw.write(jsonString);
pw.flush();

}catch(Exception exception)
{
pw.write("Error while fetching courses");
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