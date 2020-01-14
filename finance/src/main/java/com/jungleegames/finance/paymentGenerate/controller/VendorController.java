package com.jungleegames.finance.paymentGenerate.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

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
import com.jungleegames.finance.paymentGenerate.model.Vendors;
import com.jungleegames.finance.paymentGenerate.repostories.VendorService;

@Controller
public class VendorController {

	@Autowired
	private VendorService vendorService;

	@ResponseBody
	@PostMapping(value = "/updateVendorData/{id}")
	public AjaxResponse<Vendors> updateEmployeeData(@PathVariable(value = "id") ObjectId id, @ModelAttribute Vendors vendor) {
		Vendors editedVendor = vendorService.updatedVendor(id, vendor);
		AjaxResponse<Vendors> ajaxRes = new AjaxResponse<>();
		if (editedVendor == null) {
			ajaxRes.success = false;
			// ajaxRes.msg = "Employee Data Not Updated!!";
		}
//		System.out.print(editedVendor.getVendorid() + ";;;;");
		ajaxRes.success = true;
		ajaxRes.msg = editedVendor;
		return ajaxRes;
	}

	@ResponseBody
	@PostMapping(value = "/saveVendor")
	public AjaxResponse<Vendors> saveVendor(@ModelAttribute Vendors vendor) {
		String validateVendor = Utility.checkValidation(vendor);
		AjaxResponse<Vendors> ajax = new AjaxResponse<>();
		
		if (!validateVendor.isEmpty() && !validateVendor.equals("success")) {
			ajax.success = false;
			ajax.data = validateVendor;
			ajax.msg = null;
			return ajax;
		} else {
			Vendors addVendor = vendorService.saveVendor(vendor);
			if (addVendor == null) {
				ajax.success = false;
				ajax.data = "Data Not Saved !!!";
				ajax.msg = null;
			}
			ajax.success = true;
			ajax.data = null;
			ajax.msg = addVendor;
			return ajax;
		}

	}
	@ResponseBody
	@GetMapping(value ="/getAllVendor")
	public AjaxResponse<List<Vendors>> paymentReport(HttpServletRequest request) {
		 List<Vendors>vendor = vendorService.getAllVendors();
		if (vendor.isEmpty()) {
			System.out.print("No Vendor Data!!!");
		}
//		for (int i = 0; i < vendor.size(); i++) {
//			System.out.println(vendor.get(i).toString() + ":::" + vendor.size() );
//		}
		AjaxResponse <List<Vendors>> ajax =new AjaxResponse<>();
		ajax.success=true;
		ajax.data=null;
		ajax.msg =vendor;
		return ajax;
	}
	
	
	@ResponseBody
	@GetMapping(value = "/deleteVendor/{id}")
	public boolean deleteVendor(@PathVariable(value = "id") ObjectId id) {
		return vendorService.deleteVendor(id);
	}
}
