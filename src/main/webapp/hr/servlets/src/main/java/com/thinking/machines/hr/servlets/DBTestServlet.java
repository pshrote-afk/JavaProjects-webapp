package com.thinking.machines.hr.servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import com.thinking.machines.hr.dl.exceptions.*;
import com.thinking.machines.hr.dl.dao.*;

public class DBTestServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {
			Connection connection = DAOConnection.getConnection();

			response.getWriter().println("DB Connected: " + !connection.isClosed());
		} catch (DAOException dao) {
			System.out.println(dao.getMessage());
		}

		catch (SQLException e) {
			e.printStackTrace(response.getWriter());
		} catch (Exception e) {
			e.printStackTrace(response.getWriter());
		}

	}
}
