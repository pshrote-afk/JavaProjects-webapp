package com.thinking.machines.hr.servlets.courses;

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
import java.util.*;

public class AddCourse extends HttpServlet {
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
    try {
      pw = response.getWriter();

      CourseManagerInterface courseManager = CourseManager.getCourseManager();
      CourseInterface course;

      String title = request.getParameter("title");

      course = new Course();
      course.setTitle(title);
      courseManager.addCourse(course);

      JsonObject jsonObject = new JsonObject();
      jsonObject.addProperty("Result", "OK");
      jsonObject.add("Record", gson.toJsonTree(course));

      String jsonString = gson.toJson(jsonObject);
      pw.write(jsonString);
      pw.flush();
    } catch (BLException blException) {
      String blExceptionMessage = blException.getGenericException();
      if (blExceptionMessage.equals("")) // means generic exception doesnt exist
      {
        blExceptionMessage = blException.getException("title");
      }

      JsonObject jsonObject = new JsonObject();
      jsonObject.addProperty("Result", "ERROR");
      jsonObject.addProperty("Message", blExceptionMessage);
      String jsonString = gson.toJson(jsonObject);
      pw.write(jsonString);
      pw.flush();
    } catch (Exception exception) {
      JsonObject jsonObject = new JsonObject();
      jsonObject.addProperty("Result", "ERROR");
      jsonObject.addProperty("Message", "Error while adding course");
      String jsonString = gson.toJson(jsonObject);
      pw.write(jsonString);
      pw.flush();
    }
  }
}
