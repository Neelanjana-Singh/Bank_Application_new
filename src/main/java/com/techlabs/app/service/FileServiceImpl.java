package com.techlabs.app.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.techlabs.app.exception.CustomerRelatedException;

@Service
public class FileServiceImpl implements FileService{

	@Override
	public void uploadFile(MultipartFile file,Long userId) {
		String directory_path = "src/main/java/com/techlabs/app/attachments/";
		  
		  if (file.isEmpty()) {
		            throw new CustomerRelatedException("Please select a file to upload.");
		        }

		        try {
		         directory_path = directory_path + userId + "/";
		            File directory = new File(directory_path);
		            if (!directory.exists()) {
		             directory.mkdirs();
		            }

		            Path path = Paths.get(directory_path + file.getOriginalFilename());
		            if (Files.exists(path)) {
		             throw new CustomerRelatedException("File already exists: " + file.getOriginalFilename());
		            }
		            Files.write(path, file.getBytes());
		        } 
		        catch (IOException e) {
		         
		            throw new CustomerRelatedException("Could not upload the file: Error Occurred");
		        }
		
	}
	

}
