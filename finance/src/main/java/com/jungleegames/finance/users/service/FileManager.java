package com.jungleegames.finance.users.service;

import java.io.File;

// to create session specific folder for file upload
public class FileManager {
	public static boolean createUserDirectory(String username) {
		boolean flag=false;
		
			String directory = "uploads/" + username;
			File file = new File(directory);
			
			if (!file.exists()) {
				if (file.mkdir()) {
					System.out.println("Directory is created!");
					flag= true;
				} else {
					System.out.println("Failed to create directory!");
					flag= false;
				}
			}else {
				flag =true;
			}
		return flag;
	}
}
