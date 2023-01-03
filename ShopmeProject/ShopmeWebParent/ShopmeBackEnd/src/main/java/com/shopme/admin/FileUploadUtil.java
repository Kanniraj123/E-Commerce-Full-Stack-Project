package com.shopme.admin;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.web.multipart.MultipartFile;



public class FileUploadUtil {
	
	public static void saveFile(String uploadDir,String filename,
			MultipartFile multipartFile) throws IOException {
		Path uploadPath = Paths.get(uploadDir);
		
		if(!Files.exists(uploadPath)) {
			Files.createDirectories(uploadPath);
		}
		
		try(InputStream inputstream = multipartFile.getInputStream()) {
			Path filepath = uploadPath.resolve(filename);
			Files.copy(inputstream, filepath, StandardCopyOption.REPLACE_EXISTING);
		}catch(IOException io) {
			throw new IOException("Could not save file: " + filename, io);
		}
	}
	
	public static void cleanDirectory(String dir) {
		Path dirPath = Paths.get(dir);
		
		try {
			Files.list(dirPath).forEach(file -> {
				if(!Files.isDirectory(file)) {
					try {
					Files.delete(file);
					}catch(IOException ex) {
						System.out.println("could not delete the file: " + file);
					}
				}
			});
		}catch(IOException ex) {
			System.out.println("Could not list directory: " + dirPath);
		}
	}

}
