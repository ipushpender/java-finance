package com.jungleegames.finance.users.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.jungleegames.finance.users.model.Users;

@Service
public class UsersDetailImpl implements UsersDetail {

	@Autowired
	private MongoTemplate mongoTemplate;
	@Autowired
	private Auth auth;

	@Override
	public List<Users> getAllUsers() {
		return mongoTemplate.findAll(Users.class);
	}
	private String dbusername ="username";
	
	
	
	@Override
	public Users getUserByUsername(String username) {
		Query query = new Query();
		query.addCriteria(Criteria.where(dbusername).is(username));
		Users user = mongoTemplate.findOne(query, Users.class);
		if (user == null) {
			return new Users();
		} else {
			return user;
		}
	}

	@Override
	public boolean resetPassword(String newPassword, String currentPassword, String username) {
		Query query = new Query();
		boolean flag;
		query.addCriteria(Criteria.where(dbusername).is(username));
		Users user = mongoTemplate.findOne(query, Users.class);
		if (user == null) {
			flag = false;
		} else {
			if (auth.checkUserAuth(user.getPassword(), currentPassword) == true) {
				BCryptPasswordEncoder b = new BCryptPasswordEncoder();
				Query updatequery = new Query();
				updatequery.addCriteria(Criteria.where(dbusername).is(username));
				Update update = new Update();
				update.set("password", b.encode(newPassword));
				FindAndModifyOptions famo = new FindAndModifyOptions();
				famo.returnNew(true);
				Users userUpdated = mongoTemplate.findAndModify(updatequery, update, famo, Users.class);
				if (userUpdated == null) {
					flag = false;
				} else {
					flag = true;
				}
			} else {
				flag = false;
			}
		}
		return flag;
	}

}
