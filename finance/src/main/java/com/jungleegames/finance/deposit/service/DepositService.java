package com.jungleegames.finance.deposit.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;
import com.jungleegames.finance.deposit.model.CompareFileResponse;

@Service
public class DepositService {
	public static HashMap<String, Integer> getHeaderSequenceNumber(Map<String, List<String>> header,
			CompareFileResponse filehead, HttpServletRequest request) {
		String header1 = "header1" + request.getSession().getAttribute("username");
		String header2 = "header2" + request.getSession().getAttribute("username");

		HashMap<String, Integer> keyValue = new HashMap<>();
		for (int i = 0; i < header.get(header1).size(); i++) {
			if (header.get(header1).get(i).trim().equals(filehead.getAdminFileId().trim())) {
				keyValue.put("adminFileId", i);

			}
			if (header.get(header1).get(i).trim().equals(filehead.getAdminFileAmount().trim())) {
				keyValue.put("adminFileAmount", i);
			}
			if (header.get(header1).get(i).trim().equals(filehead.getAdminFileDate().trim())) {
				keyValue.put("adminFileDate", i);
			}
		}
		for (int j = 0; j < header.get(header2).size(); j++) {
			if (header.get(header2).get(j).trim().equals(filehead.getGatewayFileId().trim())) {
				keyValue.put("gatewayFileId", j);
			}
			if (header.get(header2).get(j).trim().equals(filehead.getGatewayFileAmount().trim())) {
				keyValue.put("gatewayFileAmount", j);
			}
			if (header.get(header2).get(j).trim().equals(filehead.getGatewayFileDate().trim())) {
				keyValue.put("gatewayFileDate", j);
			}
		}
		return keyValue;

	}
	public static Map<String, Object> getAdminFile(Map<Object, List<String>> sortedFile1,
			Map<Object, List<String>> sortedFile2, int idType, int adminFileId, int adminFileAmount, int adminFileDate,
			int gatewayFileAmount, int gatewayFileDate) {
		int countMatchFile1 = 0;
		Long sumOfAmountFile1 = 0L;
		int checkBrowserDisplayCount = 0;
		StringBuilder adminHtml = new StringBuilder();
		List<List<String>> csvArrayFile1 = new ArrayList<>();
//		System.out.println("Start loop");
		for (Map.Entry<Object, List<String>> entry : sortedFile1.entrySet()) {

			int flag = 0;
			Object id = entry.getKey().toString();
			List<String> rowFile1 = entry.getValue();
			List<String> res = new ArrayList<>();
			if (idType == 1) {
				if (sortedFile2.containsKey(Long.parseLong(id.toString()))) {
					res.add(sortedFile2.get(Long.parseLong(id.toString())).toString());
				}
			} else if (idType == 0) {
				if (sortedFile2.containsKey(id.toString())) {
					res.add(sortedFile2.get(id.toString()).toString());
				}
			}
			if (!res.isEmpty()) {
				flag = 1;
				res.set(0, res.get(0).substring(1, res.get(0).length() - 1));
				String[] rowFile2 = res.get(0).split(",");
				// now calculate amount diffrence
				long diffOfAmount = Long.parseLong(rowFile1.get(adminFileAmount).replace(",", "").split("\\.", 2)[0])
						- Long.parseLong(rowFile2[gatewayFileAmount].trim().replace(",", "").split("\\.", 2)[0]);
				rowFile1.add(String.valueOf(rowFile2[gatewayFileAmount]));
				rowFile1.add(String.valueOf(diffOfAmount));
				rowFile1.add(String.valueOf(rowFile2[gatewayFileDate]));

				if (idType == 1) {
					sortedFile1.put(Long.parseLong(id.toString()), rowFile1);
				} else {
					sortedFile1.put(id.toString(), rowFile1);
				}
				countMatchFile1 = countMatchFile1 + 1;
				sumOfAmountFile1 = sumOfAmountFile1
						+ Long.parseLong(rowFile1.get(adminFileAmount).replace(",", "").split("\\.", 2)[0]);
				checkBrowserDisplayCount = checkBrowserDisplayCount + 1;
				if (checkBrowserDisplayCount < 50) {
					String str = "<tr><td>" + rowFile1.get(adminFileId) + "</td><td>" + rowFile1.get(adminFileDate)
							+ "</td><td>" + rowFile1.get(adminFileAmount) + "</td><td>" + rowFile2[gatewayFileAmount]
							+ "</td><td>" + diffOfAmount + "</td></tr>";
					adminHtml.append(str);
				}
				csvArrayFile1.add(rowFile1);
			}
			if (flag == 0) {
				checkBrowserDisplayCount = checkBrowserDisplayCount + 1;
				if (checkBrowserDisplayCount < 50) {
					String str = "<tr style=\"background-color: #f9f9f9;color: #ec153d;\"><td>"
							+ rowFile1.get(adminFileId) + "</td><td>" + rowFile1.get(adminFileDate) + "</td><td>"
							+ rowFile1.get(adminFileAmount) + "</td><td>N/a</td><td>N/a</td></tr>";
					adminHtml.append(str);
				}
				countMatchFile1 = countMatchFile1 + 1;
				sumOfAmountFile1 = sumOfAmountFile1
						+ Long.parseLong(rowFile1.get(adminFileAmount).replace(",", "").split("\\.", 2)[0]);
				rowFile1.add("N/a");
				rowFile1.add("N/a");
				rowFile1.add("N/a");
				csvArrayFile1.add(rowFile1);
			}

		}

		Map<String, Object> map = new HashMap<>();
		map.put("csvArrayFile1", csvArrayFile1);
		map.put("countMatchFile1", countMatchFile1);
		map.put("sumOfAmountFile1", sumOfAmountFile1);
		map.put("adminHtml", adminHtml);
		return map;
	}

	public static Map<String, Object> getGatewayFile(Map<Object, List<String>> sortedFile1,
			Map<Object, List<String>> sortedFile2, int idType, int adminFileAmount, int adminFileDate,
			int gatewayFileId, int gatewayFileAmount, int gatewayFileDate) {
		int countMatchFile2 = 0;
		Long sumOfAmountFile2 = 0L;
		int checkBrowserDisplayCountFile2 = 0;
		StringBuilder gatewayHtml = new StringBuilder();
		List<List<String>> gatewayDataOnly = new ArrayList<>();
		int deleteme = 0;
		for (Map.Entry<Object, List<String>> entry : sortedFile2.entrySet()) {
			int flag = 0;
			Object id = entry.getKey().toString();
			List<String> rowFile2 = entry.getValue();
//			List<String> res = Utility.binarySearchForFile(sortedFile1, sortedFile1.size(), id.toString(), idType);
			List<String> res = new ArrayList<>();
			if (idType == 1) {
				if (sortedFile1.containsKey(Long.parseLong(id.toString()))) {
					res.add(sortedFile1.get(Long.parseLong(id.toString())).toString());
				}
			} else if (idType == 0) {
				if (sortedFile1.containsKey(id.toString())) {
					res.add(sortedFile1.get(id.toString()).toString());
				}
			}
			if (!res.isEmpty()) {
				flag = 1;
				res.set(0, res.get(0).substring(1, res.get(0).length() - 1));
				String[] rowFile1 = res.get(0).split(",");

				// now calculate amount diffrence
				long diffOfAmount = Long.parseLong(rowFile2.get(gatewayFileAmount).split("\\.", 2)[0])
						- Long.parseLong(rowFile1[adminFileAmount].trim().split("\\.", 2)[0]);

				rowFile2.add(String.valueOf(rowFile1[adminFileAmount]));
				rowFile2.add(String.valueOf(diffOfAmount));
				rowFile2.add(String.valueOf(rowFile1[adminFileDate]));

				if (idType == 1) {
					sortedFile2.put(Long.parseLong(id.toString()), rowFile2);
				} else {
					sortedFile2.put(id.toString(), rowFile2);
				}
				countMatchFile2 = countMatchFile2 + 1;
				sumOfAmountFile2 = sumOfAmountFile2
						+ Long.parseLong(rowFile2.get(gatewayFileAmount).split("\\.", 2)[0]);
				checkBrowserDisplayCountFile2 = checkBrowserDisplayCountFile2 + 1;
				if (checkBrowserDisplayCountFile2 < 50) {
					String str = "<tr><td>" + rowFile2.get(gatewayFileId) + "</td><td>" + rowFile2.get(gatewayFileDate)
							+ "</td><td>" + rowFile2.get(gatewayFileAmount) + "</td><td>" + rowFile1[adminFileAmount]
							+ "</td><td>" + diffOfAmount + "</td></tr>";
					gatewayHtml.append(str);
				}
				gatewayDataOnly.add(rowFile2);
			}
			if (flag == 0) {
				checkBrowserDisplayCountFile2 = checkBrowserDisplayCountFile2 + 1;
				if (checkBrowserDisplayCountFile2 < 50) {
					String str = "<tr style=\"background-color: #f9f9f9;color: #ec153d;\"><td>"
							+ rowFile2.get(gatewayFileId) + "</td><td>" + rowFile2.get(gatewayFileDate) + "</td><td>"
							+ rowFile2.get(gatewayFileAmount) + "</td><td>N/a</td><td>N/a</td></tr>";
					gatewayHtml.append(str);
				}
				countMatchFile2 = countMatchFile2 + 1;
				sumOfAmountFile2 = sumOfAmountFile2
						+ Long.parseLong(rowFile2.get(gatewayFileAmount).split("\\.", 2)[0]);
				rowFile2.add("N/a");
				rowFile2.add("N/a");
				rowFile2.add("N/a");
				gatewayDataOnly.add(rowFile2);
			}
		}
		Map<String, Object> map = new HashMap<>();
		map.put("gatewayHtml", gatewayHtml);
		map.put("countMatchFile2", countMatchFile2);
		map.put("sumOfAmountFile2", sumOfAmountFile2);
		map.put("gatewayDataOnly", gatewayDataOnly);
		return map;
	}
	public static Map<Object, List<String>> getFilesSorted(Map<String, List<ArrayList<String>>> fileRow,
			String fileRowSingleFile, int idType, int fileId) {
		int fileSize = fileRow.get(fileRowSingleFile).size();
		Map<Object, List<String>> mapFile = new HashMap<>();

		for (int j = 1; j < fileSize; j++) {
			Object id;

			if (idType == 0) {
				id = fileRow.get(fileRowSingleFile).get(j).get(fileId);
			} else {// numeric
				id = Long.parseLong(fileRow.get(fileRowSingleFile).get(j).get(fileId));
			}
			mapFile.put(id, fileRow.get(fileRowSingleFile).get(j));
		}

		Map<Object, List<String>> sortedFile = new TreeMap<>(mapFile);

		mapFile.clear();

		return sortedFile;
	}


}
