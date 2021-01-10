package com.example.LMWebApp;

import com.azure.storage.*;
import com.azure.storage.blob.*;
import com.azure.storage.blob.models.*;
import com.microsoft.azure.storage.CloudStorageAccount;
import com.microsoft.azure.storage.OperationContext;
import com.microsoft.azure.storage.StorageException;
import com.microsoft.azure.storage.blob.BlobContainerPublicAccessType;
import com.microsoft.azure.storage.blob.BlobRequestOptions;
import com.microsoft.azure.storage.blob.CloudBlobClient;
import com.microsoft.azure.storage.blob.CloudBlobContainer;
import com.microsoft.azure.storage.blob.CloudBlockBlob;
import com.azure.storage.blob.*;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.sql.*;
import java.util.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.view.RedirectView;
import org.springframework.web.util.WebUtils;

import Model.Image;


@Controller
public class UploadFilesAPI {
	
	@GetMapping( path = "/api")
	public String HelloWorld() {
		return "home";
	}
	
	@PostMapping(path = "/upload")
	public RedirectView UploadFile(@RequestParam("image") MultipartFile multipartFile,HttpServletRequest request) throws IOException, SQLException, InvalidKeyException, URISyntaxException, StorageException {
		String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
		//Get Cookie Details
		Cookie email = WebUtils.getCookie(request, "email");
		if(email == null) {
			System.out.println("Email Empty");
		}
		
		Image image = new Image("Jeevan");
		
		Properties properties = new Properties();
		properties.load(LmWebAppApplication.class.getClassLoader().getResourceAsStream("application.properties"));
		Connection connection = DriverManager.getConnection(properties.getProperty("url"), properties);
		
	    PreparedStatement insertStatement = connection
	            .prepareStatement("INSERT INTO ImageDB (id, owner) VALUES (?, ?);");

	    insertStatement.setString(1, image.getId());
	    insertStatement.setString(2, image.getOwner());

	    insertStatement.executeUpdate();
	    
	    //Upload Image to blob and get Address.
	    CloudStorageAccount storageAccount;
		CloudBlobClient blobClient = null;
		CloudBlobContainer container=null;
	    String connectionString = "DefaultEndpointsProtocol=https;AccountName=lmimages;AccountKey=hO9xcUQTSaF1odzq5QhZx+GPo8x+miTscBumwSwGm+dXv5Ky+v0nGIyWmBBYoZXaT3iG19jYRt+oSOp0oDGV2g==";

	    storageAccount = CloudStorageAccount.parse(connectionString);
	    blobClient = storageAccount.createCloudBlobClient();
	    container = blobClient.getContainerReference("imagecontainer");

	    // Create the container if it does not exist with public access
	    container.createIfNotExists(BlobContainerPublicAccessType.CONTAINER, new BlobRequestOptions(), new OperationContext());
	    
	    CloudBlockBlob blob = container.getBlockBlobReference(image.getId());
	    blob.upload(multipartFile.getInputStream(),multipartFile.getSize());
		return new RedirectView("/uploadSuccess", true);
	}
	
	@GetMapping(path = "/uploadSuccess")
	public String UploadSuccess() {
		return "uploadSuccess";
	}
}
