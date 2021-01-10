package com.example.LMWebApp;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import Model.User;
@Controller
public class DisplaySelfImages {
	@RequestMapping(value="/self-display", method=RequestMethod.GET)    
	public ModelAndView save() throws SQLException, IOException  
	{    
	ArrayList <String> imageArrayList = new ArrayList<String>();
	User user1 = new User("Jeevan","pwd");
	Properties properties = new Properties();
	properties.load(LmWebAppApplication.class.getClassLoader().getResourceAsStream("application.properties"));
	Connection connection = DriverManager.getConnection(properties.getProperty("url"), properties);
	
	//Check if user already exists
	PreparedStatement readStatement = connection.prepareStatement("SELECT * FROM imageDB WHERE owner = 'jeevan';");
	ResultSet resultSet = readStatement.executeQuery();
	while(resultSet.next()) {
		String iname = resultSet.getString("id");
		System.out.println(iname);
		imageArrayList.add(iname);
	}
	
	ModelAndView modelAndView = new ModelAndView();    
	modelAndView.setViewName("selfimages");        
	modelAndView.addObject("user", user1); 
	modelAndView.addObject("imageArrayList",imageArrayList);
	return modelAndView;    
	}
}
