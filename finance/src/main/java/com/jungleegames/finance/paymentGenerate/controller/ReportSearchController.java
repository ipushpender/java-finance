package com.jungleegames.finance.paymentGenerate.controller;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jungleegames.finance.paymentGenerate.model.AjaxResponse;
import com.jungleegames.finance.paymentGenerate.model.PaymentFileGenerationModel;
import com.jungleegames.finance.paymentGenerate.repostories.ReportSearch;

@Controller
public class ReportSearchController {

	@Autowired
	private ReportSearch reportSearch;
	
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

	@ResponseBody
	@PostMapping(value = "/get_beneficial_by_date")
	public AjaxResponse<List<PaymentFileGenerationModel>> getBeneficialByDate(HttpServletRequest request,
			@RequestParam("selected_day") String selectedDay, @RequestParam("selected_month") String selectedMonth,
			@RequestParam("selected_year") String selectedYear) {
		try {
			AjaxResponse<List<PaymentFileGenerationModel>> ajax = new AjaxResponse<>();
			List<PaymentFileGenerationModel> rsm;
			rsm = reportSearch.getDefaultData(selectedYear, selectedMonth, selectedDay);
			if (rsm == null) {
				ajax.success = false;
				ajax.data = null;
				ajax.msg = null;
			}
			ajax.success = true;
			ajax.data = (String) request.getSession().getAttribute("username");
			ajax.msg = rsm;
			return ajax;

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	@ResponseBody
	@PostMapping(value = "/get_myreport")
	public AjaxResponse<String> getMyreport(@RequestParam("id") ObjectId id, HttpServletResponse response) {
		PaymentFileGenerationModel report = reportSearch.getBeneficiaryById(id);
		AjaxResponse<String> ajax = new AjaxResponse<>();
		ajax.data = null;
		if (report == null) {
			ajax.success = false;
			ajax.msg = null;
		} else {
			extCount = extCount + 1;
			ajax.msg = "user.000" + extCount;
			ajax.data = report.getFile_content();
			ajax.success = true;

		}
		return ajax;
	}

	@ResponseBody
	@PostMapping(value = "/pdf_report_download")
	public AjaxResponse<String> pdfReportDownload(@RequestParam("id") ObjectId id) {
		PaymentFileGenerationModel report = reportSearch.getBeneficiaryById(id);
		AjaxResponse<String> ajax = new AjaxResponse<>();
		ajax.data = null;
		if (report == null) {
			ajax.success = false;
			ajax.msg = null;
		} else {
			String[] row = report.getFile_content().split("\n");
			StringBuilder mainContent = new StringBuilder();
			for (int i = 0; i < Integer.parseInt(report.getCount()); i++) {
				int incre = i + 1;
				String[] myRow = row[i].split(",");
				mainContent.append("<tr scope=\"row\"><th style=\"border: 1px solid black;text-align: center;\">"
						+ incre + "</th><td style=\"border: 1px solid black;text-align: center;\">" + myRow[4]
						+ "</td><td style=\"border: 1px solid black;text-align: center;\">" + myRow[13]
						+ "</td><td style=\"border: 1px solid black;text-align: center;\">" + myRow[22]
						+ "</td><td style=\"border: 1px solid black;text-align: center;\">" + myRow[3] + "</td></tr>");
			}
			java.util.Date time = new java.util.Date((long) report.getDate() * 1000);

			@SuppressWarnings("deprecation")
			String dateString = time.toLocaleString();
			String tableHead = "<div style=\"width: 100px; margin: auto; text-align: center;\"><img style=\"margin-top: 0px; width: 60px; height: 60px;\" src=\"http://13.235.184.241:4000/images/jg-icon.png\" /></div><div style=\"float: left;\">"
					+ dateString
					+ "</div><div style=\"text-align: center;\"><div style=\"text-align:center\"><h3 style=\"margin-top: 40px\"><b>Payout Approval Document</b></h3></div><table style=\"border: 1px solid black;border-collapse: collapse;width: 70%;margin: auto;\"><thead ><tr><th style=\"border: 1px solid black;\">#</th><th style=\"border: 1px solid black;\">Name</th><th style=\"border: 1px solid black;\">Comment</th><th style=\"border: 1px solid black;\">Date</th><th style=\"border: 1px solid black;\">Amount</th></tr></thead><tbody>";
			String tableBottom = "<tr style=\"text-align:center\";><td colspan=4>Total</td><td style=\"border:1px solid\">"
					+ report.getAmount() + "</td></tr></tbody></table>";
			String d2 = "<div style=\"margin-top:50px;\"><div style=\"float: left;\">Approver Name:______________</div><div style=\"float: right;margin-right:40px\">Approver Signature:______________</div></div>";

			String html = tableHead + mainContent + tableBottom + d2;
//			System.out.println(html);
			ajax.success = true;
			ajax.data =html;
			ajax.msg = "true";
			return ajax;

		}
		return ajax;
	}

}
