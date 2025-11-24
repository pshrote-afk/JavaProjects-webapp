package com.thinking.machines.hr.dl.dao;

import com.thinking.machines.hr.dl.exceptions.*;
import java.sql.*;

public class DAOConnection {
  private DAOConnection() // no need to make object
      {}

  public static Connection getConnection() throws DAOException {
    Connection connection = null;
    try {
	System.out.println("Before loading com.mysql.cj.jdbc.Driver");
  Class.forName("com.mysql.cj.jdbc.Driver");
System.out.println("After loading com.mysql.cj.jdbc.Driver");
  //connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/hrdb", "hr", "hr");

      //for generic credentials
      //String host = System.getenv("MYSQLHOST");
      //String port = System.getenv("MYSQLPORT");
      //String db = System.getenv("MYSQLDATABASE");
      //String user = System.getenv("MYSQLUSER");
      //String pass = System.getenv("MYSQLPASSWORD");

      //hard coded db credentials
      String host = "mysql.railway.internal";
      String port = "3306";
      String db = "railway";
      String user = "root";
      String pass = "gwbotLJnoXGSXcoBfxifCchsGzgROGiL";

      String jdbcUrl = "jdbc:mysql://" + host + ":" + port + "/" + db;

	//System.out.println("host:"+host);
	//System.out.println("port:"+port);
	//System.out.println("db:"+db);
	//System.out.println("user:"+user);
	//System.out.println("pass:"+pass);
	System.out.println("jdbcUrl: "+jdbcUrl);

      connection = DriverManager.getConnection(jdbcUrl,user,pass);
      return connection;
    } catch (Exception exception) {
	exception.printStackTrace();
	System.out.println("Exception in DAOConnection.java: "+exception.getMessage());
      throw new DAOException(exception.getMessage());
    }
  }
}
