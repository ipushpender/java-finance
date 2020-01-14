package com.jungleegames.finance.withdrawal.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.jungleegames.finance.withdrawal.model.CompareWithdrawalFileResponse;

@Service
public class WithdrawalService {
	public static HashMap<String, Integer> getWithdrawalHeaderSequenceNumber(Map<String, List<String>> header,
			CompareWithdrawalFileResponse filehead, HttpServletRequest request) {
		String header1 = "header1" + request.getSession().getAttribute("username");
		String header2 = "header2" + request.getSession().getAttribute("username");
		String header3 = "header3" + request.getSession().getAttribute("username");

		HashMap<String, Integer> keyValue = new HashMap<>();
		for (int i = 0; i < header.get(header1).size(); i++) {
			if (header.get(header1).get(i).trim().equals(filehead.getAdminAmount().trim())) {
				keyValue.put("adminAmount", i);

			}
			if (header.get(header1).get(i).trim().equals(filehead.getAdminBankId().trim())) {
				keyValue.put("adminBankId", i);
			}
			if (header.get(header1).get(i).trim().equals(filehead.getAdminCashfreeId().trim())) {
				keyValue.put("adminCashfreeId", i);
			}
		}
		for (int j = 0; j < header.get(header2).size(); j++) {
			if (header.get(header2).get(j).trim().equals(filehead.getCashfreeId().trim())) {
				keyValue.put("cashfreeId", j);
			}
			if (header.get(header2).get(j).trim().equals(filehead.getCashfreeAmount().trim())) {
				keyValue.put("cashfreeAmount", j);
			}

		}
		for (int j = 0; j < header.get(header3).size(); j++) {
			if (header.get(header3).get(j).trim().equals(filehead.getBankId().trim())) {
				keyValue.put("bankId", j);
			}
			if (header.get(header3).get(j).trim().equals(filehead.getBankAmount().trim())) {
				keyValue.put("bankAmount", j);
			}

		}
		return keyValue;

	}

	public static HashMap<String, Object> filterBankFile(Map<String, List<ArrayList<String>>> file, String bankfile,
			int bankId, int amount, int idType) {
		int fileSize = file.get(bankfile).size();
		double sum = 0;
		Map<Object, List<String>> mapFile = new HashMap<>();
		for (int i = 1; i < fileSize; i++) {
			Object id = file.get(bankfile).get(i).get(bankId);
			id = id.toString().replaceAll("[^0-9]", "");

			file.get(bankfile).get(i).add("N/a");
			file.get(bankfile).get(i).add("N/a");
			file.get(bankfile).get(i).add("N/a");
			// to remove "," from csv file
			file.get(bankfile).get(i).set(amount, file.get(bankfile).get(i).get(amount).replace(",", ""));
			sum = sum + Double.parseDouble(file.get(bankfile).get(i).get(amount).replace(",", ""));
			if (idType == 0) {
				id = file.get(bankfile).get(i).get(bankId);
			} else {// numeric
				id = Long.parseLong(id.toString());
			}
			mapFile.put(id, file.get(bankfile).get(i));
		}
		Map<Object, List<String>> sortedFile = new TreeMap<>(mapFile);
		mapFile.clear();
		file.get(bankfile).clear();
		HashMap<String, Object> map = new HashMap<>();
		map.put("sortedFile", sortedFile);
		map.put("amount", sum);
//		to append sum of amount of bank in return
		return map;

	}

	public static HashMap<String, Object> getCashfreeeFilesSorted(Map<String, List<ArrayList<String>>> fileRow,
			String fileRowSingleFile, int idType, int fileId, int amount) {
		int fileSize = fileRow.get(fileRowSingleFile).size();
		Map<Object, List<String>> mapFile = new HashMap<>();
		double sum = 0;
		for (int j = 1; j < fileSize; j++) {
			Object id;

			if (idType == 0) {
				id = fileRow.get(fileRowSingleFile).get(j).get(fileId);
			} else {// numeric
				id = Long.parseLong(fileRow.get(fileRowSingleFile).get(j).get(fileId));
			}
			fileRow.get(fileRowSingleFile).get(j).add("N/a");
			fileRow.get(fileRowSingleFile).get(j).add("N/a");
			fileRow.get(fileRowSingleFile).get(j).add("N/a");
			fileRow.get(fileRowSingleFile).get(j).add("N/a");
			sum = sum + Double.parseDouble(fileRow.get(fileRowSingleFile).get(j).get(amount).replace(",", ""));
//.split("\\.", 2)[0]
			mapFile.put(id, fileRow.get(fileRowSingleFile).get(j));
		}
		Map<Object, List<String>> sortedFile = new TreeMap<>(mapFile);
		mapFile.clear();
		fileRow.get(fileRowSingleFile).clear();
		HashMap<String, Object> map = new HashMap<>();
		map.put("sortedFile", sortedFile);
		map.put("amount", sum);
		return map;
	}

	public static HashMap<String, Object> compareAdminWithCashfree(Map<String, List<ArrayList<String>>> fileRow,
			String adminFile, int idType, Map<Object, List<String>> cashfreeFile, int adminCashfreeId, int adminAmount,
			int cashId, int cashAmount, int adminBankId) {
		long adminCount = 0;
		List<List<String>> cashfreeDuplicateCheck = new ArrayList<>();
		List<List<Object>> cashfreeList = new ArrayList<>();
		double sumOfAdmin = 0;
		int browserCountCashfree = 0;
		for (int i = 1; i < fileRow.get(adminFile).size(); i++) {
			sumOfAdmin = sumOfAdmin + Double.parseDouble(fileRow.get(adminFile).get(i).get(adminAmount));
			int flag = 0;
			int cashfreeFlag = 0;

			List<String> cashfreeFileRow = new ArrayList<>();
			String adminId = fileRow.get(adminFile).get(i).get(adminCashfreeId);
			List<String> res = new ArrayList<>();
			if (idType == 1) {
				if (cashfreeFile.containsKey(Long.parseLong(adminId))) {
					res.add(cashfreeFile.get(Long.parseLong(adminId)).toString());
				}
			} else if (idType == 0) {
				if (cashfreeFile.containsKey(adminId)) {
					res.add(cashfreeFile.get(adminId).toString());
				}
			}
			if (!res.isEmpty()) {
				flag = 1;
				res.set(0, res.get(0).substring(1, res.get(0).length() - 1));
				String[] cashfreeArray = res.get(0).split(",");
				double adminDiffAmount = Double.parseDouble(fileRow.get(adminFile).get(i).get(adminAmount))
						- Double.parseDouble(cashfreeArray[cashAmount].trim().replace(",", ""));
				double cashfreeDiffAmount = Double.parseDouble(cashfreeArray[cashAmount].trim().replace(",", ""))
						- Double.parseDouble(fileRow.get(adminFile).get(i).get(adminAmount));
				if (Long.parseLong(fileRow.get(adminFile).get(i).get(adminAmount)) == Long
						.parseLong(cashfreeArray[cashAmount].trim().replace(",", ""))) {

					cashfreeFlag = 1;
					adminCount = adminCount + 1;
					fileRow.get(adminFile).get(i).add(cashfreeArray[cashId]);
					fileRow.get(adminFile).get(i).add(cashfreeArray[cashAmount].trim().replace(",", ""));
					fileRow.get(adminFile).get(i).add(String.valueOf(adminDiffAmount).trim().replace(",", ""));
					cashfreeArray[cashfreeArray.length - 4] = fileRow.get(adminFile).get(i).get(adminBankId);
					cashfreeArray[cashfreeArray.length - 3] = adminId;
					cashfreeArray[cashfreeArray.length - 2] = fileRow.get(adminFile).get(i).get(adminAmount);
					cashfreeArray[cashfreeArray.length - 1] = String.valueOf(cashfreeDiffAmount);
					cashfreeFileRow.add(Arrays.toString(cashfreeArray));
					if (idType == 1) {
						cashfreeFile.put(Long.parseLong(adminId), cashfreeFileRow);
					} else {
						cashfreeFile.put(adminId, cashfreeFileRow);
					}
					cashfreeDuplicateCheck.add(fileRow.get(adminFile).get(i));
					browserCountCashfree = browserCountCashfree + 1;
					if (browserCountCashfree < 50) {
						List<Object> temp = new ArrayList<>();
						temp.add(cashfreeArray[cashId]);
						temp.add(fileRow.get(adminFile).get(i).get(adminBankId));
						temp.add(fileRow.get(adminFile).get(i).get(adminAmount));
						temp.add(adminDiffAmount);
						cashfreeList.add(temp);

					}
				}
				if (cashfreeFlag == 0) {
					flag = 1;
					adminCount = adminCount + 1;
					fileRow.get(adminFile).get(i).add(fileRow.get(adminFile).get(i).get(adminCashfreeId));
					fileRow.get(adminFile).get(i).add(cashfreeArray[cashAmount].trim().replace(",", ""));
					fileRow.get(adminFile).get(i).add(String.valueOf(adminDiffAmount).trim().replace(",", ""));
					cashfreeArray[cashfreeArray.length - 4] = fileRow.get(adminFile).get(i).get(adminBankId);
					cashfreeArray[cashfreeArray.length - 3] = adminId;
					cashfreeArray[cashfreeArray.length - 2] = fileRow.get(adminFile).get(i).get(adminAmount);
					cashfreeArray[cashfreeArray.length - 1] = String.valueOf(cashfreeDiffAmount);
					cashfreeFileRow.add(Arrays.toString(cashfreeArray));
					if (idType == 1) {
						cashfreeFile.put(Long.parseLong(adminId), cashfreeFileRow);
					} else {
						cashfreeFile.put(adminId, cashfreeFileRow);
					}

					cashfreeDuplicateCheck.add(fileRow.get(adminFile).get(i));

					browserCountCashfree = browserCountCashfree + 1;
					if (browserCountCashfree < 50) {
						List<Object> temp = new ArrayList<>();
						temp.add(cashfreeArray[cashId]);
						temp.add(fileRow.get(adminFile).get(i).get(adminBankId));
						temp.add(fileRow.get(adminFile).get(i).get(adminAmount));
						temp.add(adminDiffAmount);
						cashfreeList.add(temp);

					}
				}
			}
			if (flag == 0 && cashfreeFlag == 0) {
				fileRow.get(adminFile).get(i).add("N/a");
				fileRow.get(adminFile).get(i).add("N/a");
				fileRow.get(adminFile).get(i).add("N/a");
				adminCount = adminCount + 1;
				browserCountCashfree = browserCountCashfree + 1;
				if (browserCountCashfree < 50) {
					List<Object> temp = new ArrayList<>();
					temp.add(fileRow.get(adminFile).get(i).get(adminCashfreeId));
					temp.add(fileRow.get(adminFile).get(i).get(adminBankId));
					temp.add("Not Found");
					temp.add("Not Found");
					cashfreeList.add(temp);

				}
			}
		}
		HashMap<String, Object> map = new HashMap<>();
		map.put("adminCount", adminCount);
		map.put("sumOfAdmin", sumOfAdmin);
		map.put("cashfreeDuplicateCheck", cashfreeDuplicateCheck);
		map.put("cashfreeFile", cashfreeFile);
		map.put("fileRow", fileRow);
//		map.put("adminHtml", adminHtml);
//		map.put("cashfreeHtml", cashfreeHtml);
		map.put("cashfreeList", cashfreeList);
		return map;
	}

	public static HashMap<String, Object> compareAdminWithBank(Map<String, List<ArrayList<String>>> fileRow,
			String adminFile, int idType, Map<Object, List<String>> bankFile, int adminBankId, int adminAmount,
			int bankId, int bankAmount, int adminCashfreeId) {
		int adminCount = 0;
		List<List<Object>> bankList = new ArrayList<>();
		List<List<String>> bankDuplicateCheck = new ArrayList<>();
		int browserCountBank = 0;
		for (int i = 1; i < fileRow.get(adminFile).size(); i++) {
			int flag = 0;
			int bankFlag = 0;

			List<String> bankFileRow = new ArrayList<>();
			String adminId = fileRow.get(adminFile).get(i).get(adminBankId);
			List<String> res = new ArrayList<>();
			if (idType == 1) {
				if (bankFile.containsKey(Long.parseLong(adminId))) {
					res.add(bankFile.get(Long.parseLong(adminId)).toString());
				}
			} else if (idType == 0) {
				if (bankFile.containsKey(adminId)) {
					res.add(bankFile.get(adminId).toString());
				}
			}

			if (!res.isEmpty()) {
				flag = 1;
				res.set(0, res.get(0).substring(1, res.get(0).length() - 1));
				String[] bankArray = res.get(0).split(",");

				double adminDiffAmount = Double.parseDouble(fileRow.get(adminFile).get(i).get(adminAmount))
						- Double.parseDouble(bankArray[bankAmount].trim().replace(",", ""));
				double bankDiffAmount = Double.parseDouble(bankArray[bankAmount].trim().replace(",", ""))// split("\\.",
																											// 2)[0]
						- Double.parseDouble(fileRow.get(adminFile).get(i).get(adminAmount));
				if (Double.parseDouble(fileRow.get(adminFile).get(i).get(adminAmount)) == Double
						.parseDouble(bankArray[bankAmount].trim().replace(",", ""))) {// .split("\\.", 2)[0])
					bankFlag = 1;
					adminCount = adminCount + 1;
					fileRow.get(adminFile).get(i).add(bankArray[bankId]);
					fileRow.get(adminFile).get(i).add(bankArray[bankAmount].trim().replace(",", ""));
					fileRow.get(adminFile).get(i).add(String.valueOf(adminDiffAmount));
					bankArray[bankArray.length - 3] = adminId;
					bankArray[bankArray.length - 2] = fileRow.get(adminFile).get(i).get(adminAmount).trim().replace(",",
							"");
					bankArray[bankArray.length - 1] = String.valueOf(bankDiffAmount);
					bankFileRow.add(Arrays.toString(bankArray));

					if (idType == 1) {
						bankFile.put(Long.parseLong(adminId), bankFileRow);
					} else {
						bankFile.put(adminId, bankFileRow);
					}
					bankDuplicateCheck.add(fileRow.get(adminFile).get(i));
					browserCountBank = browserCountBank + 1;
					if (browserCountBank < 50) {
						List<Object> temp = new ArrayList<>();
						temp.add(fileRow.get(adminFile).get(i).get(adminCashfreeId));
						temp.add(fileRow.get(adminFile).get(i).get(adminBankId));
						temp.add(fileRow.get(adminFile).get(i).get(adminAmount));
						temp.add(adminDiffAmount);
						bankList.add(temp);

					}
				}
				if (bankFlag == 0) {
					flag = 1;
					adminCount = adminCount + 1;
					fileRow.get(adminFile).get(i).add(fileRow.get(adminFile).get(i).get(adminBankId));
					fileRow.get(adminFile).get(i).add(bankArray[bankAmount].trim().replace(",", ""));
					fileRow.get(adminFile).get(i).add(String.valueOf(adminDiffAmount));
					bankArray[bankArray.length - 3] = adminId;
					bankArray[bankArray.length - 2] = fileRow.get(adminFile).get(i).get(adminAmount).trim().replace(",",
							"");
					bankArray[bankArray.length - 1] = String.valueOf(bankDiffAmount);
					bankFileRow.add(Arrays.toString(bankArray));
					if (idType == 1) {
						bankFile.put(Long.parseLong(adminId), bankFileRow);
					} else {
						bankFile.put(adminId, bankFileRow);
					}

					bankDuplicateCheck.add(fileRow.get(adminFile).get(i));

					browserCountBank = browserCountBank + 1;
					if (browserCountBank < 50) {
						List<Object> temp = new ArrayList<>();
						temp.add(fileRow.get(adminFile).get(i).get(adminCashfreeId));
						temp.add(fileRow.get(adminFile).get(i).get(adminBankId));
						temp.add(fileRow.get(adminFile).get(i).get(adminAmount));
						temp.add(bankDiffAmount);
						bankList.add(temp);

					}
				}

			}
			if (flag == 0 && bankFlag == 0) {
				fileRow.get(adminFile).get(i).add("N/a");
				fileRow.get(adminFile).get(i).add("N/a");
				fileRow.get(adminFile).get(i).add("N/a");
				browserCountBank = browserCountBank + 1;
				if (browserCountBank < 50) {
					List<Object> temp = new ArrayList<>();
					temp.add(fileRow.get(adminFile).get(i).get(adminCashfreeId));
					temp.add(fileRow.get(adminFile).get(i).get(adminBankId));
					temp.add("Not Found");
					temp.add("Not Found");
					bankList.add(temp);

				}
			}
		}
		HashMap<String, Object> map = new HashMap<>();
		map.put("bankDuplicateCheck", bankDuplicateCheck);
		map.put("bankFile", bankFile);
		map.put("fileRow", fileRow);
		map.put("bankList", bankList);
		return map;
	}

	public static Map<String, List<ArrayList<String>>> fetchDuplicateRow(
			Map<String, List<ArrayList<String>>> finalAdminFile, List<List<String>> bankDuplicateCheck,
			List<List<String>> cashfreeDuplicateCheck, int adminBankId, int adminCashfreeId, int idType) {
		for (int i = 0; i < bankDuplicateCheck.size(); i++) {
			List<ArrayList<String>> duplicate = new ArrayList<>();
			int bankId = Integer.parseInt(bankDuplicateCheck.get(i).get(adminBankId));
			int cahfreeId = Integer.parseInt(bankDuplicateCheck.get(i).get(adminCashfreeId));
			int len = cashfreeDuplicateCheck.size();

			ArrayList<String> res = binarySearchDuplicateRow(cashfreeDuplicateCheck, len, bankId, cahfreeId, idType,
					adminBankId, adminCashfreeId);
			if (!res.isEmpty()) {
				duplicate.add(res);
				finalAdminFile.put(String.valueOf(cahfreeId), duplicate);
				return finalAdminFile;
			}
		}
		return finalAdminFile;
	}

	public static ArrayList<String> binarySearchDuplicateRow(List<List<String>> arr, int len, int bankId, int cashId,
			int idType, int adminBankId, int adminCashfreeId) {
		int start = 0;
		int stop = len - 1;
		int middle = (start + stop) / 2;
		ArrayList<String> temp = new ArrayList<>();
		if (idType == 1) {

			while (Integer.parseInt(arr.get(middle).get(adminCashfreeId)) != cashId
					&& Integer.parseInt(arr.get(middle).get(adminBankId)) != bankId && start < stop) {

				if (bankId < Integer.parseInt(arr.get(middle).get(adminBankId))) {
					stop = middle - 1;
				} else {
					start = middle + 1;
				}
				if (start < 0 || stop < 0) {
					return new ArrayList<>();
				}
				middle = (start + stop) / 2;
			}
			if (Integer.parseInt(arr.get(middle).get(adminBankId)) != bankId) {
				return new ArrayList<>();
			} else if (Integer.parseInt(arr.get(middle).get(adminBankId)) == bankId
					&& Integer.parseInt(arr.get(middle).get(adminCashfreeId)) == cashId) {
				temp.add(arr.get(middle).toString());
				return temp;
			}
		}
		return new ArrayList<>();
	}


	public static String getResultHtml(List<List<Object>> cashfreeList, List<List<Object>> bankList) {
		StringBuilder finalHtml = new StringBuilder();
		for (int i = 0; i < cashfreeList.size(); i++) {
			String str = "";
			String type = "";
			String type1 = "";
			if (cashfreeList.get(i).get(2).equals("Not Found")) {
				type = "red";
			} else {
				type = "black";
			}
			if (bankList.get(i).get(2).equals("Not Found")) {
				type1 = "red";
			} else {
				type1 = "black";
			}

			str = "<tr ><td class=\"w15\">" + cashfreeList.get(i).get(0) + "</td><td class=\"w15\">"
					+ bankList.get(i).get(1) + "</td>" + "<td class=\"w15 " + type + "\">" + cashfreeList.get(i).get(2)
					+ "</td><td class=\"w15 " + type + "\">" + cashfreeList.get(i).get(3) + "</td><td class=\"w15  "
					+ type1 + "\">" + bankList.get(i).get(2) + "</td><td class=\"w15  " + type1 + "\">"
					+ bankList.get(i).get(3) + "</td></tr>";

			finalHtml.append(str);
		}
		return finalHtml.toString();

	}


}
