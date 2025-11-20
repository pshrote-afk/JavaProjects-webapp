package com.thinking.machines.hr.dl.dao;

import com.thinking.machines.hr.dl.exceptions.*;
import java.sql.*;

public class DAOConnection {
  private DAOConnection() // no need to make object
      {}

  public static Connection getConnection() throws DAOException {
    Connection connection = null;
    try {
      Class.forName("com.mysql.cj.jdbc.Driver");
      //connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/hrdb", "hr", "hr");

      String host = System.getenv("MYSQLHOST");
      String port = System.getenv("MYSQLPORT");
      String db = System.getenv("MYSQLDATABASE");
      String user = System.getenv("MYSQLUSER");
      String pass = System.getenv("MYSQLPASSWORD");

      String jdbcUrl = "jdbc:mysql://" + host + ":" + port + "/" + db;

      connection = DriverManager.getConnection(jdbcUrl,user,pass);
      return connection;
    } catch (Exception exception) {
      throw new DAOException(exception.getMessage());
    }
  }
}
