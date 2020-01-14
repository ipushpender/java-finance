package com.jungleegames.finance.users.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.jungleegames.finance.paymentGenerate.config.IfscList;
import com.jungleegames.finance.paymentGenerate.model.AjaxResponse;
import com.jungleegames.finance.paymentGenerate.model.Employees;
import com.jungleegames.finance.paymentGenerate.repostories.EmployeeDetail;
import com.jungleegames.finance.users.model.Users;
import com.jungleegames.finance.users.repository.Auth;
import com.jungleegames.finance.users.repository.UsersDetail;
import com.jungleegames.finance.users.service.FileManager;
import com.jungleegames.finance.users.service.SendGridMail;
import com.sendgrid.Response;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class UsersController {

	@Autowired
	private UsersDetail userDetail;
	@Autowired
	private SendGridMail sendGridMail;
	@Autowired
	private EmployeeDetail empDetail;
	@Autowired
	private Auth auth;

	@Autowired
	private IfscList ifscList;

	@GetMapping(value = "/")
	public String index() {
		return "login";
	}

	@GetMapping(value = "/login")
	public String login() {
		return "login";
		
	}

	@ResponseBody
	@PostMapping(value = "/")
	public AjaxResponse<String> usersLogin(@ModelAttribute Users userRequest, HttpServletRequest request,
			HttpServletResponse response) {
		String userName = userRequest.getUsername();
		String password = userRequest.getPassword();
		AjaxResponse<String> ajaxRes = new AjaxResponse<>();
		ajaxRes.data = null;
		Users user = userDetail.getUserByUsername(userName);
		if (user == null) {
			ajaxRes.success = false;
			ajaxRes.msg = "Invalid User !!";
			return ajaxRes;
		}
		boolean checkUserAuth = auth.checkUserAuth(user.getPassword(), password);
		if (checkUserAuth) {

			auth.setUserTypeInSession(user.getUsername(), request);
			boolean flag = FileManager.createUserDirectory(request.getSession().getAttribute("username").toString());
			if (flag) {
				long rand = auth.getRandomNumber(10000, 99999);
				request.getSession().setAttribute("randNum", rand);
				long unixTime = System.currentTimeMillis() / 1000L;
				long nowPlus10Minutes = unixTime + +5 * 60 * 2;// for ten minute head
				request.getSession().setAttribute("randNum_timer", nowPlus10Minutes);
				Response mailResponse = sendGridMail.sendMailToUser(request.getSession().getAttribute("username").toString(), rand);
//				int i = 0;
				if (mailResponse.getStatusCode() == 202) {
//				if (i == 0) {
					ajaxRes.success = true;
					ajaxRes.msg = request.getSession().getAttribute("username").toString();
//					System.out.print(request.getSession().getAttribute("user_type")+"::::::" + (long) request.getSession().getAttribute("randNum") + ":::::::::::");
					return ajaxRes;
				} else {
					ajaxRes.success = false;
					ajaxRes.msg = "Invalid Login !!";
					return ajaxRes;
				}
			}

		} else {
			ajaxRes.success = false;
			ajaxRes.msg = "Invalid Credential !!";
			return ajaxRes;
		}

		ajaxRes.success = false;
		ajaxRes.msg = "Error in Login";
		return ajaxRes;
	}

	@ResponseBody
	@PostMapping(value = "/otpVerify")
	public AjaxResponse<String> otpVerify(@RequestParam("otp") long otp, @RequestParam("username") String username,
			HttpServletRequest request) {
		AjaxResponse<String> ajaxRes = new AjaxResponse<>();
//		Map<String,String> map= new HashMap<String,String>();
		ajaxRes.data = null;
		boolean verifyOtp = auth.compareOtp((long) request.getSession().getAttribute("randNum_timer"),
				(long) request.getSession().getAttribute("randNum"), otp);
		if (verifyOtp) {
			request.getSession().setAttribute("username", username);
			ajaxRes.success = true;
			ajaxRes.msg = "Success";
		} else {
			ajaxRes.success = false;
			ajaxRes.msg = "Invalid Otp";
		}
		return ajaxRes;
	}

	@GetMapping(value = "/main_page")
	public String mainPage(HttpServletRequest request) {
		if (request.getSession().getAttribute("username") == null) {
			return "redirect:/";
		}
		return "main";
	}

	@GetMapping(value = "/index")
	public ModelAndView paymentReport(HttpServletRequest request) {
		List<Employees> employee = empDetail.getAllEmployees();
		if (employee.isEmpty()) {
			System.out.print("No Employee Data!!!");
		}
//		for (int i = 0; i < employee.size(); i++) {
//			System.out.println(employee.get(i).toString() + ":::" + employee.size() + ":::"
//					+ request.getSession().getAttribute("user_type"));
//		}
		ModelAndView mav = new ModelAndView();
		mav.addObject("user_status", request.getSession().getAttribute("user_type"));
		mav.addObject("len", employee.size());
		mav.addObject("employeeData", employee);
		mav.addObject("title", "Payment Report");
		mav.setViewName("index");
		return mav;
	}

	@GetMapping(value = "/reset_password")
	public String resetPassword(HttpServletRequest request) {
		if (request.getSession().getAttribute("username") == null) {
			return "redirect:/";
		}
		return "reset_password";
	}

	@ResponseBody
	@PostMapping(value = "/getBankDetails")
	public AjaxResponse<String> getBankDetails(@RequestParam("ifsc") String ifsc) throws IOException {

		int n = ifsc.length();
		StringBuilder arr = new StringBuilder();
		AjaxResponse<String> ajax = new AjaxResponse<>();
		if (n > 0) {
			try {
				if (!ifscList.getIfscCode().isEmpty()) {
					for (int j = 0; j < ifscList.getIfscCode().size(); j++) {
						if (ifscList.getIfscCode().get(j).get(0).substring(0, n)
								.equalsIgnoreCase(ifsc.substring(0, n))) {
							arr.append("<a href=\"#\" value=" + ifscList.getIfscCode().get(j).get(0)
									+ " class=\"form-control ddn\">" + ifscList.getIfscCode().get(j).get(0) + "</a>");
						}
					}
				}
				ajax.success = true;
				ajax.msg = arr.toString();
				ajax.data = null;
				return ajax;
			} catch (Exception e) {
				System.out.print("::::" + e.getMessage());
				ajax.success = false;
				ajax.msg = e.getMessage();
				ajax.data = null;
				return ajax;
			}

		}
		ajax.success = false;
		ajax.msg = "Error !!";
		ajax.data = null;
		return ajax;
	}

	@ResponseBody
	@PostMapping(value = "/getBank")
	public AjaxResponse<Map<String, String>> getBank(@RequestParam("ifsc") String ifsc) {
		AjaxResponse<Map<String, String>> ajax = new AjaxResponse<>();
		Map<String, String> map = new HashMap<>();
		RestTemplate restTemplate = new RestTemplate();
		String ifscResponse = restTemplate.getForObject("http://api.techm.co.in/api/v1/ifsc/" + ifsc, String.class);
		if (ifscResponse == null) {
			map.put("", "");
			ajax.msg = map;
			ajax.success = false;
			ajax.data = null;
			return ajax;
		}
		JSONObject obj = new JSONObject(ifscResponse);
		map.put("CITY", obj.getJSONObject("data").get("CITY").toString());
		map.put("BANK", obj.getJSONObject("data").get("BANK").toString());

		ajax.msg = map;
		ajax.success = true;
		ajax.data = null;
		return ajax;
	}

	@ResponseBody
	@PostMapping(value = "/reset_password")
	public boolean changePassword(@RequestParam("password") String password,
			@RequestParam("current_password") String currentPassword, HttpServletRequest request) {
		return userDetail.resetPassword(password, currentPassword,
				request.getSession().getAttribute("username").toString());

	}

	@GetMapping(value = "/signout")
	public String signout(HttpSession session, HttpServletRequest request) {
		session.invalidate();
		return "redirect:/";
	}
}
