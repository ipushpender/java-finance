package com.jungleegames.finance.commons.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.jungleegames.finance.commons.FileStorageException;
import com.jungleegames.finance.commons.FileStorageProperty;

@Service
public class FileStorageService {

	private Path fileStorageLocation;

	@Autowired
	public FileStorageService(FileStorageProperty fileStorageProperties) {

		this.fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir()).toAbsolutePath().normalize();
		try {
//	      
			Files.createDirectories(this.fileStorageLocation);
		} catch (Exception ex) {
			throw new FileStorageException("Could not create the directory where the uploaded files will be stored.",
					ex);
		}
	}

	public String storeFile(MultipartFile file, int type, HttpServletRequest request) {
		// Normalize file name
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());

		try {
			// Check if the file's name contains invalid characters
			if (fileName.contains("..")) {
				throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
			}
			String ext = FilenameUtils.getExtension(fileName);
			if (type == 1) {
				fileName = "Admin." + ext;
			} else if (type == 2) {
				fileName = "Gateway." + ext;// cashfree in case of withdrawal
			} else if (type == 3) {
				fileName = "Bank." + ext;
			}
			fileName = request.getSession().getAttribute("username") + "/" + fileName;
			// Copy file to the target location (Replacing existing file with the same name)
			Path targetLocation = this.fileStorageLocation.resolve(fileName);
			Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

			return fileName;
		} catch (IOException ex) {
			throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
		}
	}

}
