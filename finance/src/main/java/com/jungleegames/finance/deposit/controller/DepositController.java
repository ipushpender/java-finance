package com.jungleegames.finance.deposit.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.jungleegames.finance.commons.DownloadCsv;
import com.jungleegames.finance.commons.Utility;
import com.jungleegames.finance.commons.model.FileContent;
import com.jungleegames.finance.commons.service.FileStorageService;
import com.jungleegames.finance.commons.service.ParseXcelSheet;
import com.jungleegames.finance.deposit.model.CompareFileResponse;
import com.jungleegames.finance.deposit.model.DepositResponseToView;
import com.jungleegames.finance.deposit.service.DepositService;

@Controller
public class DepositController {

	@Autowired
	private FileStorageService fileStorageService;
	@Autowired
	private ParseXcelSheet parseXcelSheet;

	@GetMapping(value = "/deposit")
	public String deposit(HttpServletRequest request) {
		if (request.getSession().getAttribute("username") == null) {
			return "redirect:/";
		}
		return "deposit_reconcile";
	}

//	FileContent files = new FileContent();
	Map<String, List<String>> header = new HashMap<>();
	Map<String, List<ArrayList<String>>> fileRow = new HashMap<>();
	DownloadCsv csv = new DownloadCsv();

	@ResponseBody
	@PostMapping(value = "/uploadFile1")
	public List<String> uploadFile1(@RequestParam("file") MultipartFile file, HttpServletRequest request) {

		int adminFile = 1;
		String header1 = "header1" + request.getSession().getAttribute("username");
		String fileRow1 = "fileRow1" + request.getSession().getAttribute("username");
		String fileName = fileStorageService.storeFile(file, adminFile, request);
		if (fileName == null) {
			return new ArrayList<>();
		}
		System.out.print("now parse..");
		FileContent file1 = parseXcelSheet.parsexcelFile(adminFile, request);
		header.put(header1, file1.getFileHeader1());
		fileRow.put(fileRow1, file1.getFileRow1());
		return header.get(header1);
	}

	@ResponseBody
	@PostMapping(value = "/uploadFile2")
	public List<String> uploadFile2(@RequestParam("file") MultipartFile file, HttpServletRequest request) {
		String header2 = "header2" + request.getSession().getAttribute("username");
		String fileRow2 = "fileRow2" + request.getSession().getAttribute("username");
		int gatewayFile = 2;
		String fileName = fileStorageService.storeFile(file, gatewayFile, request);
		if (fileName == null) {
			return new ArrayList<>();
		}
		FileContent file2 = parseXcelSheet.parsexcelFile(gatewayFile, request);

		header.put(header2, file2.getFileHeader2());
		fileRow.put(fileRow2, file2.getFileRow2());

		return header.get(header2);

	}

	@ResponseBody
	@PostMapping(value = "/compare")
	public DepositResponseToView compare(@ModelAttribute CompareFileResponse filehead, HttpServletRequest request) {
		try {
			FileUtils.cleanDirectory(new File("uploads/" + request.getSession().getAttribute("username") + "/"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		String fileRow1 = "fileRow1" + request.getSession().getAttribute("username");
		String fileRow2 = "fileRow2" + request.getSession().getAttribute("username");
		String header1 = "header1" + request.getSession().getAttribute("username");
		String header2 = "header2" + request.getSession().getAttribute("username");
		int adminFileId = 0;
		int adminFileAmount = 0;
		int adminFileDate = 0;
		int gatewayFileId = 0;
		int gatewayFileAmount = 0;
		int gatewayFileDate = 0;
		HashMap<String, Integer> headers = DepositService.getHeaderSequenceNumber(header, filehead, request);
//		========================SET USER ENTER OPTION COLUMN NUMBER==========================================

		adminFileId = headers.get("adminFileId");
		adminFileAmount = headers.get("adminFileAmount");
		adminFileDate = headers.get("adminFileDate");
		gatewayFileId = headers.get("gatewayFileId");
		gatewayFileAmount = headers.get("gatewayFileAmount");
		gatewayFileDate = headers.get("gatewayFileDate");
		System.out.println("---" + adminFileId + "----" + adminFileAmount + "----" + adminFileDate + "------"
				+ gatewayFileId + "---" + gatewayFileAmount + "----" + gatewayFileDate + "---");
//		========================CHECK ID ID STRING OR NUMBER==========================================
		int idType = Utility.isNumeric(fileRow.get(fileRow1).get(1).get(adminFileId)) ? 1 : 0;// 0=alphanumeric,1=numeric
//		===========================SORT FILE USING HASHTREE+===============================================	
		Map<Object, List<String>> sortedFile1 = DepositService.getFilesSorted(fileRow, fileRow1, idType, adminFileId);
		Map<Object, List<String>> sortedFile2 = DepositService.getFilesSorted(fileRow, fileRow2, idType, gatewayFileId);
//		==========================FIRST COMPARE ADMIN TO GATEWAY========================================== 	
		Map<String, Object> map = DepositService.getAdminFile(sortedFile1, sortedFile2, idType, adminFileId, adminFileAmount,
				adminFileDate, gatewayFileAmount, gatewayFileDate);
		List<List<String>> csvArrayFile1 = (List<List<String>>) map.get("csvArrayFile1");
		int countMatchFile1 = (int) map.get("countMatchFile1");
		Long sumOfAmountFile1 = (Long) map.get("sumOfAmountFile1");
		String adminHtml = map.get("adminHtml").toString();
		System.out.println("Start loop....2");
//		==========================NOW COMPARE GATEWAY TO ADMIN(VICE VERSA) ========================================== 			
		Map<String, Object> gatewaymap = DepositService.getGatewayFile(sortedFile1, sortedFile2, idType, adminFileAmount,
				adminFileDate, gatewayFileId, gatewayFileAmount, gatewayFileDate);
		int countMatchFile2 = (int) gatewaymap.get("countMatchFile2");
		Long sumOfAmountFile2 = (Long) gatewaymap.get("sumOfAmountFile2");
		String gatewayHtml = gatewaymap.get("gatewayHtml").toString();
		List<List<String>> gatewayDataOnly = (List<List<String>>) gatewaymap.get("gatewayDataOnly");
		System.out.println("End loop....2");

//		==========================FETCH ADMIN DATWISE GATEWAY ROW ========================================== 			

		HashSet<String> adminUniqueDate1 = new HashSet<>();

		for (int j = 1; j < fileRow.get(fileRow1).size(); j++) {// index is 1 to remove header
			adminUniqueDate1.add(fileRow.get(fileRow1).get(j).get(adminFileDate));
		}
		int gatewayDataOnlyLength = gatewayDataOnly.size();
		List<String> adminUniqueDate = new ArrayList<>(adminUniqueDate1);

		System.out.println(adminUniqueDate + "_________" + adminUniqueDate.size());
		Iterator value = adminUniqueDate.iterator();
		while (value.hasNext()) {
			List<List<String>> gatewayRows = new ArrayList<>();
			String date = value.next().toString();
			for (int j = 0; j < gatewayDataOnlyLength; j++) {
				if (date.equals(gatewayDataOnly.get(j).get(gatewayFileDate))) {
					gatewayRows.add(gatewayDataOnly.get(j));
				}
			}
			campareWithGateway(gatewayRows, date, gatewayFileAmount);
		}
//		==========================APPEND HEADER TO RESULT CSV FILE========================================== 		
		header.get(header2).add("AdminAmount");
		header.get(header2).add("AdminDiff");
		header.get(header2).add("AdminDate");
		header.get(header1).add("GatewayAmount");
		header.get(header1).add("GatewayDiff");
		header.get(header1).add("GatewayDate");
		csvArrayFile1.add(0, header.get(header1));
		gatewayCsvRow.add(0, header.get(header2));
//		==========================CONVERT TO RESULT CSV FILE========================================== 			
		csv.setFile1(csvArrayFile1.toString().replace("[", "").replace("],", "\n").replace("]]", ""));
		csv.setFile2(gatewayCsvRow.toString().replace("[", "").replace("],", "\n").replace("]]", ""));

//		==========================RESPONSE TO VIEW========================================== 	
		DepositResponseToView res = new DepositResponseToView();
		res.setAdminAmount(String.valueOf(sumOfAmountFile1));
		res.setAdminAmountword("");
		res.setAdminCount(String.valueOf(countMatchFile1));
		res.setAdminHtmlArray(adminHtml);
		res.setGatewayAmount(String.valueOf(sumOfAmountFile2));
		res.setGatewayAmountword("");
		res.setGatewayCount(String.valueOf(countMatchFile2));
		res.setGatewayHtmlArray(gatewayHtml);
		gatewayCsvRow.clear();
		csvArrayFile1.clear();
		return res;

	}

	List<List<String>> gatewayCsvRow = new ArrayList<>();

	public void campareWithGateway(List<List<String>> gatewayRows, String date, int gatewayFileAmount) {
		List<String> sumOfRow = new ArrayList<>();
		Long amount = 0L;
		for (int i = 0; i < gatewayRows.size(); i++) {
			amount = amount + Long.parseLong(gatewayRows.get(i).get(gatewayFileAmount).replace(",", ""));
			gatewayCsvRow.add(gatewayRows.get(i));
		}
		sumOfRow.add("Total");
		sumOfRow.add(date);
		sumOfRow.add(amount.toString());
		gatewayCsvRow.add(sumOfRow);
	}

	@ResponseBody
	@PostMapping(value = "/downloadCsv")
	public String downloadCsv(@RequestParam String typeCsv) {
		if (typeCsv.equals("Admin.csv")) {
			return csv.getFile1();
		} else if (typeCsv.equals("Gateway.csv")) {
			return csv.getFile2();
		}
		return "";
	}

}
