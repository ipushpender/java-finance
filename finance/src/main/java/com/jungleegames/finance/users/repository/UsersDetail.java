package com.jungleegames.finance.users.repository;

import java.util.List;

import com.jungleegames.finance.users.model.Users;

public interface UsersDetail {
	List<Users> getAllUsers();
	Users getUserByUsername(String username); 
	boolean resetPassword(String newPassword,String currentPassword,String username);
}
