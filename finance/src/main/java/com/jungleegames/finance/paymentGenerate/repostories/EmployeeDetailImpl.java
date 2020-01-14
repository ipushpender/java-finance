package com.jungleegames.finance.paymentGenerate.repostories;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.text.WordUtils;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.jungleegames.finance.commons.Utility;
import com.jungleegames.finance.paymentGenerate.model.DynaResp;
import com.jungleegames.finance.paymentGenerate.model.Employees;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class EmployeeDetailImpl implements EmployeeDetail {

	@Autowired
	private MongoTemplate mongoTemplate;

	@Override
	public List<Employees> getAllEmployees() {
		Query query = new Query();
		query.with(Sort.by(Sort.Direction.DESC, "_id"));
		return mongoTemplate.find(query, Employees.class);
	}

	@Override
	public Employees updatedEmplooyee(ObjectId empIds, Employees emp) {
		Employees employee = null;
		Query query = new Query();
		query.addCriteria(Criteria.where("empid").is(empIds));
		Update update = new Update();
		update.set("name", emp.getName());
		update.set("account_name", emp.getAccount_name());
		update.set("location", emp.getLocation());
		update.set("bank", emp.getBank());
		update.set("account_no", emp.getAccount_no());
		update.set("ifsc", emp.getIfsc());
		update.set("empid", emp.getEmpid());
		FindAndModifyOptions famo = new FindAndModifyOptions();
		famo.returnNew(true);
		employee = mongoTemplate.findAndModify(query, update, famo, Employees.class);
		if (employee == null) {
			return new Employees();
		}
		return employee;
	}

	@Override
	public Employees saveEmployee(Employees emp) {
		return mongoTemplate.insert(emp);
	}

	@Override
	public boolean deleteEmployee(ObjectId id) {
		Query query = new Query();
		query.addCriteria(Criteria.where("empid").is(id));
		return (mongoTemplate.findAndRemove(query, Employees.class) != null);
	}

	@Override
	public List<DynaResp> getEmployeeName(String val) {
		String input;
		String input1;
		String input2;
		String input3;
		final char[] delimiters = { ' ', '_' };
		input = val;
		input1 = input.toUpperCase();
		input2 = WordUtils.capitalizeFully(input, delimiters);// Titlecase
		input3 = Utility.convertToSentence(input);// Sentence Case
		Criteria criteria = new Criteria();

		criteria.orOperator(Criteria.where("name").regex("^" + input + ".*"),
				Criteria.where("name").regex("^" + input1 + ".*"), Criteria.where("name").regex("^" + input2 + ".*"),
				Criteria.where("name").regex("^" + input3 + ".*"));
		Query query = new Query(criteria);
		query.fields().include("name");
		return mongoTemplate.find(query, DynaResp.class, "employees");
	}

	@Override
	public Employees getUserByName(String name) {
		Employees emp = null;
		Query query = new Query();
		query.addCriteria(Criteria.where("name").is(name));
		emp = mongoTemplate.findOne(query, Employees.class);
		if (emp == null) {
			return new Employees();
		}
		return emp;
	}

	@Override
	public boolean isValidEmployee(String name) {

		Query query = new Query();
		query.addCriteria(Criteria.where("name").is(name));
		return (mongoTemplate.findOne(query, Employees.class) != null);
	}
}
