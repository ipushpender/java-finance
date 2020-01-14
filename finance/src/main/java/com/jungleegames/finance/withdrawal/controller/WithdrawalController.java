package com.jungleegames.finance.withdrawal.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.jungleegames.finance.withdrawal.model.CompareWithdrawalFileResponse;
import com.jungleegames.finance.withdrawal.model.ResponseToView;
import com.jungleegames.finance.withdrawal.service.WithdrawalService;

@Controller
public class WithdrawalController {
	DownloadCsv csv = new DownloadCsv();
	Map<String, List<String>> header = new HashMap<>();
	Map<String, List<ArrayList<String>>> fileRow = new HashMap<>();
	@Autowired
	private FileStorageService fileStorageService;
	@Autowired
	private ParseXcelSheet parseXcelSheet;

	@GetMapping(value = "/withdraw")
	public String withdrawal(HttpServletRequest request) {
		if (request.getSession().getAttribute("username") == null) {
			return "redirect:/";
		}

		return "withdrawal_reconcile";
	}

	@ResponseBody
	@PostMapping(value = "/adminFileUpload")
	public List<String> adminFileUpload(@RequestParam("file") MultipartFile file, HttpServletRequest request,
			HttpServletResponse response) {

		int adminFile = 1;
		String header1 = "header1" + request.getSession().getAttribute("username");
		String fileRow1 = "fileRow1" + request.getSession().getAttribute("username");
		String fileName = fileStorageService.storeFile(file, adminFile, request);
		if (fileName == null) {
			return new ArrayList<>();
		}

		FileContent file1 = parseXcelSheet.parsexcelFile(adminFile, request);
		header.put(header1, file1.getFileHeader1());
		fileRow.put(fileRow1, file1.getFileRow1());
		return header.get(header1);
	}

	@ResponseBody
	@PostMapping(value = "/cashfreeFileUpload")
	public List<String> cashfreeFileUpload(@RequestParam("file") MultipartFile file, HttpServletRequest request) {
		int cashfreeFileType = 2;
		String header2 = "header2" + request.getSession().getAttribute("username");
		String fileRow2 = "fileRow2" + request.getSession().getAttribute("username");
		String fileName = fileStorageService.storeFile(file, cashfreeFileType, request);
		if (fileName == null) {
			return new ArrayList<>();
		}

		FileContent file2 = parseXcelSheet.parsexcelFile(cashfreeFileType, request);
		header.put(header2, file2.getFileHeader2());
		fileRow.put(fileRow2, file2.getFileRow2());
		return header.get(header2);
	}

	@ResponseBody
	@PostMapping(value = "/bankFileUpload")
	public List<String> bankFileUpload(@RequestParam("file") MultipartFile file, HttpServletRequest request) {
		int bankFileType = 3;
		String header3 = "header3" + request.getSession().getAttribute("username");
		String fileRow3 = "fileRow3" + request.getSession().getAttribute("username");
		String fileName = fileStorageService.storeFile(file, bankFileType, request);
		if (fileName == null) {
			return new ArrayList<>();
		}
		FileContent file3 = parseXcelSheet.parsexcelFile(bankFileType, request);
		header.put(header3, file3.getFileHeader3());
		fileRow.put(fileRow3, file3.getFileRow3());
		return header.get(header3);
	}

	@ResponseBody
	@PostMapping(value = "/compareWithdraw")
	public ResponseToView compareWithdraw(@ModelAttribute CompareWithdrawalFileResponse filehead,
			HttpServletRequest request) {
// 		==============================REMOVE UPLOADED FILES======================================================
		try {
			FileUtils.cleanDirectory(new File("uploads/" + request.getSession().getAttribute("username") + "/"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		String fileRow1 = "fileRow1" + request.getSession().getAttribute("username");
		String fileRow2 = "fileRow2" + request.getSession().getAttribute("username");
		String fileRow3 = "fileRow3" + request.getSession().getAttribute("username");
		String header1 = "header1" + request.getSession().getAttribute("username");
		String header2 = "header2" + request.getSession().getAttribute("username");
		String header3 = "header3" + request.getSession().getAttribute("username");
		List<String> fileHeader1 = header.get(header1);
		List<String> fileHeader2 = header.get(header2);
		List<String> fileHeader3 = header.get(header3);
//		========================APPEND HEADER TO RECONCILE FILE==========================================
		fileHeader1.add("Cashfree Id");
		fileHeader1.add("Cashfree Amount");
		fileHeader1.add("Cashfree Diff");
		fileHeader1.add("Bank Id");
		fileHeader1.add("Bank Amount");
		fileHeader1.add("Bank Diff");
		fileHeader2.add("Admin Id");
		fileHeader2.add("Admin Amount");
		fileHeader2.add("Admin Diff");
		fileHeader3.add("Admin Id");
		fileHeader3.add("Admin Amount");
		fileHeader3.add("Admin Diff");

		int adminBankId = 0;
		int adminCashfreeId = 0;
		int adminAmount = 0;
		int cashfreeId = 0;
		int cashfreeAmount = 0;
		int bankId = 0;
		int bankAmount = 0;

		HashMap<String, Integer> headers = WithdrawalService.getWithdrawalHeaderSequenceNumber(header, filehead, request);
//		========================SET USER ENTER OPTION COLUMN NUMBER==========================================
		adminBankId = headers.get("adminBankId");
		adminCashfreeId = headers.get("adminCashfreeId");
		adminAmount = headers.get("adminAmount");
		cashfreeId = headers.get("cashfreeId");
		cashfreeAmount = headers.get("cashfreeAmount");
		bankId = headers.get("bankId");
		bankAmount = headers.get("bankAmount");
		headers.clear();//


//		========================CHECK ID ID STRING OR NUMBER==========================================
		int idType = Utility.isNumeric(fileRow.get(fileRow2).get(1).get(cashfreeId)) ? 1 : 0;// 0=alphanumeric,1=numeric
//		========================FILTER ID IF ALPHANUMERIC ,APPEND N/A and get SUM AMOUNT,==========================
		System.out.println("Filter before bank");
		HashMap<String, Object> bankFileRes = WithdrawalService.filterBankFile(fileRow, fileRow3, bankId, bankAmount, idType);
		Map<Object, List<String>> bankFileAfterFilter = (Map<Object, List<String>>) bankFileRes.get("sortedFile");
		double amountBank = (double) bankFileRes.get("amount");
		bankFileRes.remove("amount");
//		========================GET CASHFREE FILE IN SORTING AND APPEND N/A======================================
		HashMap<String, Object> cashfreeRes = WithdrawalService.getCashfreeeFilesSorted(fileRow, fileRow2, idType, cashfreeId,
				cashfreeAmount);
		Map<Object, List<String>> cashfreeFileRes = (Map<Object, List<String>>) cashfreeRes.get("sortedFile");
		double amountCashfree = (double) cashfreeRes.get("amount");
//		========================CLEAR FILEROW 2 AND 3======================================
		fileRow.get(fileRow3).clear();// delete old bank file array
		fileRow.get(fileRow2).clear();//
//		========================COMPARE ADMIN WITH CASHFREE AND FETCH ALL DETAILS==============================
		System.out.println("compareAdminWithCashfree");
		HashMap<String, Object> cashfreeResult = WithdrawalService.compareAdminWithCashfree(fileRow, fileRow1, idType,
				cashfreeFileRes, adminCashfreeId, adminAmount, cashfreeId, cashfreeAmount, adminBankId);
		List<List<Object>> cashfreeList = (List<List<Object>>) cashfreeResult.get("cashfreeList");
		double sumAdmin = (double) cashfreeResult.get("sumOfAdmin");
		Map<String, List<ArrayList<String>>> adminFileRow = (Map<String, List<ArrayList<String>>>) cashfreeResult
				.get("fileRow");
		List<List<String>> cashfreeDuplicateCheck = (List<List<String>>) cashfreeResult.get("cashfreeDuplicateCheck");
		Map<Object, List<String>> finalCashfreeFile = (Map<Object, List<String>>) cashfreeResult.get("cashfreeFile");

		String adminCount = cashfreeResult.get("adminCount").toString();
		String adminHtml = cashfreeResult.get("adminHtml").toString();
		String cashfreeHtml = cashfreeResult.get("cashfreeHtml").toString();
//		========================COMPARE ADMIN WITH BANK AND FETCH ALL DETAIL==============================
		HashMap<String, Object> bankResult = WithdrawalService.compareAdminWithBank(adminFileRow, fileRow1, idType,
				bankFileAfterFilter, adminBankId, adminAmount, bankId, bankAmount, adminCashfreeId);
		List<List<Object>> bankList = (List<List<Object>>) bankResult.get("bankList");
		StringBuilder adminHtm = new StringBuilder(adminHtml);
		adminHtm.append(bankResult.get("bankAdminHtml").toString());
		List<List<String>> bankDuplicateCheck = (List<List<String>>) bankResult.get("bankDuplicateCheck");
		Map<Object, List<String>> finalBankFile = (Map<Object, List<String>>) bankResult.get("bankFile");
		String bankHtml = bankResult.get("bankHtml").toString();

		Map<String, List<ArrayList<String>>> mainAdminFile = (Map<String, List<ArrayList<String>>>) bankResult
				.get("fileRow");
//		========================CHECK IF ADMIN CONTAIN BOTH(CASHFREE AND BANK) ON SAME ROW========================
		String resultHtml = WithdrawalService.getResultHtml(cashfreeList, bankList);

		Map<String, List<ArrayList<String>>> finalADminFile = WithdrawalService.fetchDuplicateRow(mainAdminFile,
				bankDuplicateCheck, cashfreeDuplicateCheck, adminBankId, adminCashfreeId, idType);

		csv.setFile1(finalADminFile.get(fileRow1).toString().replace("[", "").replace("],", "\n").replace("]]", ""));
//		========================CONVERT BANKFILE TO ARRAYLIST FOR CSV==============================		
		List<String> bankfileCsv = new ArrayList<>();
		for (Map.Entry<Object, List<String>> entry : finalBankFile.entrySet()) {
			bankfileCsv.add(entry.getValue().toString());
		}
		bankfileCsv.add(0, fileHeader3.toString());
		String bankfileCount = String.valueOf(bankfileCsv.size());
		csv.setFile2(bankfileCsv.toString().replace("[", "").replace("],", "\n").replace("]]", "").replace("]", ""));
//		========================CONVERT CASHFREE TO ARRAYLIST FOR CSV==============================				
		List<String> cashfreefileHeader = new ArrayList<>();
		for (Map.Entry<Object, List<String>> entry : finalCashfreeFile.entrySet()) {
			cashfreefileHeader.add(entry.getValue().toString());
		}
		cashfreefileHeader.add(0, fileHeader2.toString());
		String cashfreeFileSize = String.valueOf(cashfreefileHeader.size());

		csv.setFile3(
				cashfreefileHeader.toString().replace("[", "").replace("],", "\n").replace("]]", "").replace("]", ""));
//		==================================RESPONSE TO VIEW=====================================================				
		ResponseToView res = new ResponseToView();
		res.setAdminHtml(resultHtml);
		res.setAdminSize(adminCount);
		res.setAdminTotalAmount(String.valueOf(sumAdmin));
		res.setBankHtml(bankHtml);
		res.setBankSize(bankfileCount);
		res.setBankTotalAmount(String.valueOf(amountBank));
		res.setCashfreeHtml(cashfreeHtml);
		res.setCashfreeSize(cashfreeFileSize);
		res.setCashfreeTotalAmount(String.valueOf(amountCashfree));
		return res;
	}

	@ResponseBody
	@PostMapping(value = "/downloadWithdrawalCsv")
	public String downloadWithdrawalCsv(@RequestParam String typeCsv) {
		if (typeCsv.equals("Admin.csv")) {
			return csv.getFile1();
		} else if (typeCsv.equals("Bank.csv")) {
			return csv.getFile2();
		} else if (typeCsv.equals("CashFree.csv")) {
			return csv.getFile3();
		}
		return typeCsv;
	}
}
