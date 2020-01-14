package com.jungleegames.finance.paymentGenerate.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jungleegames.finance.paymentGenerate.model.AjaxResponse;
import com.jungleegames.finance.paymentGenerate.model.DynaResp;
import com.jungleegames.finance.paymentGenerate.model.Employees;
import com.jungleegames.finance.paymentGenerate.model.PaymentFileGenerationModel;
import com.jungleegames.finance.paymentGenerate.model.Vendors;
import com.jungleegames.finance.paymentGenerate.repostories.EmployeeDetail;
import com.jungleegames.finance.paymentGenerate.repostories.PaymentGeneration;
import com.jungleegames.finance.paymentGenerate.repostories.VendorService;

@Controller
public class PaymentFileGenerationController {

	private int extCount;
	private static final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

	@PostConstruct
	private void init() {
		scheduler.schedule(new Runnable() {

			@Override
			public void run() {
				extCount = 0;

			}

		}, 24, TimeUnit.HOURS);
	}

	@Autowired
	private EmployeeDetail employeeDetail;
	@Autowired
	private VendorService vendorService;
	@Autowired
	private PaymentGeneration paymentFileGeneration;
	private String vendorType = "Vendor";
	private String employeeType = "Employee";

	@ResponseBody
	@PostMapping(value = "/get_user_names")
	public List<String> getUserNames(@RequestParam("type") String type, @RequestParam("val") String val,
			HttpServletRequest request) {

//		if(request.getSession().getAttribute("username")===null ) {
//			return  "redirect:/";
//		}
		List<String> names = new ArrayList<>();
		if (type.equals(employeeType)) {
			List<DynaResp> dbNames = employeeDetail.getEmployeeName(val);
			if (dbNames.isEmpty()) {
				return new ArrayList<>();
			}
			for (int i = 0; i < dbNames.size(); i++) {
				names.add(dbNames.get(i).getName());
			}
			dbNames.clear();
		} else if (type.equals(vendorType)) {
			List<DynaResp> dbNames = vendorService.getVendorName(val);
			if (dbNames.isEmpty()) {
				return new ArrayList<>();
			}
			for (int i = 0; i < dbNames.size(); i++) {
				names.add(dbNames.get(i).getName());
			}
			dbNames.clear();
		}
		return names;
	}

	@ResponseBody
	@PostMapping(value = "/get_beneficiary_details")
	public AjaxResponse<?> getBeneficiaryDetails(@RequestParam("type") String type, @RequestParam("name") String name) {
		if (type.equals(employeeType)) {
			AjaxResponse<Employees> ajax = new AjaxResponse<>();
			Employees emp = employeeDetail.getUserByName(name);
			if (emp == null) {
				ajax.success = false;
				ajax.data = "NO data";
				ajax.msg = null;
				return ajax;
			}
			ajax.success = true;
			ajax.data = null;
			ajax.msg = emp;
			return ajax;
		} else if (type.equals(vendorType)) {
			AjaxResponse<Vendors> ajax = new AjaxResponse<>();
			Vendors vendor = vendorService.getUserByName(name);
			if (vendor == null) {
				ajax.success = false;
				ajax.data = "NO data";
				ajax.msg = null;
				return ajax;
			}
			ajax.success = true;
			ajax.data = null;
			ajax.msg = vendor;
			return ajax;
		}
		return null;
	}

	@ResponseBody
	@PostMapping(value = "/verify_beneficiary")
	public AjaxResponse<Boolean> verifyBeneficiary(@RequestParam("type") String type,
			@RequestParam("name") String name) {
		boolean status;
		AjaxResponse<Boolean> ajax = new AjaxResponse<>();
		ajax.data = null;
		ajax.msg = null;
		if (type.equals(employeeType)) {
			status = employeeDetail.isValidEmployee(name);
			ajax.success = status;
			return ajax;
		} else if (type.equals(vendorType)) {
			status = vendorService.isValidVendor(name);
			ajax.success = status;
			return ajax;
		}
		ajax.success = false;
		return ajax;
	}

	@ResponseBody
	@PostMapping(value = "/generatefile")
	public AjaxResponse<String> generatefile(@ModelAttribute PaymentFileGenerationModel paymentFileGenerationModel,
			HttpServletRequest request, HttpServletResponse response) {
		AjaxResponse<String> ajax = new AjaxResponse<>();
		ajax.data = null;
		ajax.msg = null;
//		System.out.print(paymentFileGenerationModel.getFile_content() + "___________-");

		if (request.getSession().getAttribute("username") == null) {
			ajax.success = false;
			ajax.msg = "Session Expired !!";
		}
		paymentFileGenerationModel.setPerson(request.getSession().getAttribute("username").toString());

		try {
			boolean saved = paymentFileGeneration.saveBeneficiary(paymentFileGenerationModel);
			if (saved) {
				PaymentFileGenerationModel pfg = paymentFileGeneration.getLastRecord();
				if (pfg == null) {
					ajax.success = false;
					return ajax;
				} else {
					extCount = extCount + 1;
					ajax.msg = "user.000" + extCount;
					ajax.data = pfg.getFile_content();
					ajax.success = true;
					return ajax;
				}
			} else {
				ajax.success = false;
				return ajax;
			}
		} catch (Exception e) {
			System.out.print("::::::" + e.getMessage());
		}
		return ajax;
	}

	@ResponseBody
	@PostMapping(value = "/pdf_download")
	public AjaxResponse<String> pdfDownload(@RequestParam("top") String top,
			@RequestParam("main_content") String mainContent, @RequestParam("bottom") String bottom) {
		System.out.println("PDF !");
		String d1 = "<div style=\"margin-top:50px;\"><div style=\"float: left;\">Approver Name:______________</div><div style=\"float: right;margin-right:40px\">Approver Signature:______________</div></div>";

		AjaxResponse<String> ajax = new AjaxResponse<>();
		final String HTML = top + mainContent + bottom + d1;
		ajax.success = true;
		ajax.msg = "PDF Generated";
		ajax.data = HTML;
		System.out.println("PDF Created!");
		return ajax;
	}

}
