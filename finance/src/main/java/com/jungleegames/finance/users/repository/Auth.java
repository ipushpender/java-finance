package com.jungleegames.finance.users.repository;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.jungleegames.finance.commons.config.ProjectConfig;

@Service
public class Auth {

	@Autowired
	private ProjectConfig config;

	public void setUserTypeInSession(String username, HttpServletRequest request) {

		request.getSession().setAttribute("user_type", 0);
		String[] adminName = config.getAdminName().split(",");
		for (int i = 0; i < adminName.length; i++) {
//			System.out.println(adminName[i]+"::::::" +username);
			if (adminName[i].equals(username)) {
				request.getSession().setAttribute("user_type", 1);
				if (username.equals("Pawan@finance") || username.equals("Abhishek@finance")) {
					username = config.getAccountingEmail();
				}
				if (username.equals("Yokta")) {
					username = config.getYoktaEmail();
				}
				if (username.equals("pushpender")) {
					username = config.getPushpenderEmail();
				}
//				System.out.println(adminName[i]+":::ll:::" +username);
				request.getSession().setAttribute("username",username);
			}
		}

	}

	public boolean compareOtp(long randNumTimer, long randNum, long userInput) {
		long now = System.currentTimeMillis() / 1000L;
		long checkTimerExpired = randNumTimer - now;
		if (randNum == userInput) {
			return checkTimerExpired > 0 ? true : false;
		}
		return false;

	}

	public int getRandomNumber(int min, int max) {
		return (int) ((Math.random() * (max - min)) + min);
	}

	public boolean checkUserAuth(String dbuser, String userEnterPassword) {
		BCryptPasswordEncoder b = new BCryptPasswordEncoder();
		return b.matches(userEnterPassword, dbuser);
	}

}
