package com.thinking.machines.hr.servlets;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import com.thinking.machines.hr.bl.interfaces.managers.*;
import com.thinking.machines.hr.bl.managers.*;
import com.thinking.machines.hr.bl.exceptions.*;

public class InitServlet extends HttpServlet
{	
    public void init()
    {
        try
        {
            CourseManagerInterface courseManager = CourseManager.getCourseManager();
            StudentManagerInterface studentManager = StudentManager.getStudentManager();
            System.out.println("Course and Student data structures loaded in BL (Business Layer) via InitServlet which was loaded on startup\n\n");
        }catch(BLException blException)
        {
            System.out.println(blException.getMessage());
        }
        catch(Exception exception)
        {
            System.out.println(exception.getMessage());
        }

    }
}