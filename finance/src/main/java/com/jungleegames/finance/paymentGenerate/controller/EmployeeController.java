package com.jungleegames.finance.paymentGenerate.controller;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jungleegames.finance.commons.Utility;
import com.jungleegames.finance.paymentGenerate.model.AjaxResponse;
import com.jungleegames.finance.paymentGenerate.model.Employees;
import com.jungleegames.finance.paymentGenerate.repostories.EmployeeDetail;

@Controller
public class EmployeeController {

	@Autowired
	private EmployeeDetail employeeDetail;

	@ResponseBody
	@PostMapping(value = "/updateEmployeeData/{id}")
	public AjaxResponse<Employees> updateEmployeeData(@PathVariable(value = "id") ObjectId id,
			@ModelAttribute Employees emp) {
		String validateEmployee = Utility.checkValidation(emp);
		AjaxResponse<Employees> ajaxRes = new AjaxResponse<>();
		if (!validateEmployee.isEmpty() && !validateEmployee.equals("success")) {
			ajaxRes.success = false;
			ajaxRes.data = validateEmployee;
			ajaxRes.msg = null;
			return ajaxRes;
		}

		Employees editedEmployee = employeeDetail.updatedEmplooyee(id, emp);
		if (editedEmployee == null) {
			ajaxRes.success = false;
			ajaxRes.data = "Employee Data Not Updated!!";
			ajaxRes.msg = null;
		}
		ajaxRes.success = true;
		ajaxRes.msg = editedEmployee;
		ajaxRes.data = null;
		return ajaxRes;
	}

	@ResponseBody
	@PostMapping(value = "/saveEmployee")
	public AjaxResponse<Employees> saveEmployee(@ModelAttribute Employees employee) {
		String validateEmployee = Utility.checkValidation(employee);
		AjaxResponse<Employees> ajax = new AjaxResponse<>();

		if (!validateEmployee.isEmpty() && !validateEmployee.equals("success")) {
			ajax.success = false;
			ajax.data = validateEmployee;
			ajax.msg = null;
			return ajax;
		} else {
			Employees addEmployee = employeeDetail.saveEmployee(employee);
			if (addEmployee == null) {
				ajax.success = false;
				ajax.data = "Data Not Saved !!!";
				ajax.msg = null;
				return ajax;
			}
			ajax.success = true;
			ajax.data = null;
			ajax.msg = addEmployee;
			return ajax;
		}

	}

	@ResponseBody
	@GetMapping(value = "/deleteEmployee/{id}")
	public boolean deleteEmployee(@PathVariable(value = "id") ObjectId id) {
		return employeeDetail.deleteEmployee(id);
	}
}
