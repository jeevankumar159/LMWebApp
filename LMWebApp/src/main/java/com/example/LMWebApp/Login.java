package com.example.LMWebApp;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import Model.User;

@Controller
public class Login {
	@GetMapping("/login-user")
	public RedirectView loginUser(@RequestParam("email") String email,@RequestParam("password") String password, HttpServletResponse response) throws IOException, SQLException {
		Properties properties = new Properties();
		properties.load(LmWebAppApplication.class.getClassLoader().getResourceAsStream("application.properties"));
		Connection connection = DriverManager.getConnection(properties.getProperty("url"), properties);
		
		PreparedStatement readStatement = connection.prepareStatement("SELECT email FROM userDB;");
		ResultSet resultSet = readStatement.executeQuery();
		if (!resultSet.next()) {
			return new RedirectView("/home");
		}
		// Check if password match
		if(resultSet.getString("password")!=password) {
			return new RedirectView("/home");
		}
		
		User user = new User();
		user.setEmailid(email);
		user.setPassword(password);
		Cookie cookie = new Cookie("email",email);
		response.addCookie(cookie);
		return new RedirectView("/home");
	}
	@GetMapping("/login")
	public String login() {
		return "login";
	}
}
