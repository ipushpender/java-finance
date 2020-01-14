package com.jungleegames.finance.paymentGenerate.repostories;

import java.util.List;

import org.bson.types.ObjectId;

import com.jungleegames.finance.paymentGenerate.model.DynaResp;
import com.jungleegames.finance.paymentGenerate.model.Employees;

public interface EmployeeDetail {
	List<Employees> getAllEmployees();

	Employees updatedEmplooyee(ObjectId empid,Employees emp);
	Employees saveEmployee(Employees emp);
	boolean deleteEmployee(ObjectId empid);
	List<DynaResp> getEmployeeName(String val);
	Employees getUserByName(String name);
	boolean isValidEmployee(String name);
	}
