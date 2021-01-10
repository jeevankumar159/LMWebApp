package com.example.LMWebApp;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;



import Model.User;
@Controller
public class RegisterUser {

	@PostMapping(path = "/registerUser")
	public RedirectView registerUser(@RequestParam("email") String email,@RequestParam("password") String password) throws IOException, SQLException {
		//Check if user already in DB
		User user = new User(email,password);
		System.out.println("Email: "+ email);
		Properties properties = new Properties();
		properties.load(LmWebAppApplication.class.getClassLoader().getResourceAsStream("application.properties"));
		Connection connection = DriverManager.getConnection(properties.getProperty("url"), properties);
		
		//Check if user already exists
		PreparedStatement readStatement = connection.prepareStatement("SELECT email FROM userDB;");
		ResultSet resultSet = readStatement.executeQuery();
		if (!resultSet.next()) {
			PreparedStatement insertStatement = connection.prepareStatement("INSERT INTO userDB (email,password) VALUES (?,?)");
			insertStatement.setString(1,user.getEmailid());
			insertStatement.setString(2, user.getPassword());
			insertStatement.executeUpdate();
			return new RedirectView("/login", true);
		}
		
		return new RedirectView("/userexist", true);
	}

	@GetMapping(path = "/userexist")
	public String userAlreadyExists() {
		return "home";
	}
}

