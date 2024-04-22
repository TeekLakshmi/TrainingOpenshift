package org.tektutor;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.*;
import java.sql.*;

@RestController
public class Hello {

	public String readGreetingMsgFromDB() {
	   String url = System.getenv("jdbc-url");
	   //String url = "jdbc:mysql://localhost:3306/tektutor";
           String username = ""; 
           String password = "";
           String query = "select * from greeting"; 

	   String msg="";

	   try {

                Class.forName(System.getenv("jdbc-connection")); 
		//Class.forName("com.mysql.cj.jdbc.Driver");

		username = System.getenv("username");
		password = System.getenv("password");

           	Connection connection = DriverManager.getConnection(url, username, password);

	   	Statement statement = connection.createStatement();

       	   	ResultSet resultSet = statement.executeQuery(query); 
           	resultSet.next();

           	msg = resultSet.getString("message"); 
           	statement.close(); 
           	connection.close(); 
	   }
	   catch( ClassNotFoundException e) {
		System.err.println(e);
	   } 
	   catch( Exception e) {
		System.err.println(e);
	   }

	   return msg; 
	}

	@RequestMapping("/")
	public String sayHello() {
		String message = readGreetingMsgFromDB();

		return message;
	}

}
