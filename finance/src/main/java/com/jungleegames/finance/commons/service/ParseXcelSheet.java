package com.jungleegames.finance.commons.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Service;

import com.jungleegames.finance.commons.model.FileContent;
import com.monitorjbl.xlsx.StreamingReader;

@Service
public class ParseXcelSheet {

	private DataFormatter dataFormatter = new DataFormatter();

	public FileContent parsexcelFile(int fileType, HttpServletRequest request) {

		List<String> head = new ArrayList<>();
		List<ArrayList<String>> dataRow = new ArrayList<>();
		String userFile = null;
		int rowCount = 0;
		int colCount = 0;
		if (fileType == 1) {
			userFile = "Admin.xlsx";
		} else if (fileType == 2) {
			userFile = "Gateway.xlsx";
		} else if (fileType == 3) {
			userFile = "Bank.xlsx";
		}
//		userFile = fileType == 1 ? "Admin.xlsx" : "Gateway.xlsx";
//		String userFileName = fileType == 1 ? "Admin" : "Gateway";
		System.out.println("Inside parsing." + userFile);
		try {
			InputStream file = new FileInputStream(
					new File("uploads/" + request.getSession().getAttribute("username") + "/" + userFile));
			Workbook workbook = StreamingReader.builder().rowCacheSize(100) // number of rows to keep in memory
					.bufferSize(4096) // index of sheet to use (defaults to 0)
					.open(file); // InputStream or File for XLSX file (required)

			Iterator<Row> rowIterator = workbook.getSheetAt(0).rowIterator();
			System.out.println("parsing done.");
			while (rowIterator.hasNext()) {
				Row row = rowIterator.next();
				Iterator<Cell> cellIterator = row.cellIterator();
				colCount = 0;
				dataRow.add(new ArrayList<String>());
				while (cellIterator.hasNext()) {
					Cell cell = cellIterator.next();
					String cellValue = dataFormatter.formatCellValue(cell);
					dataRow.get(rowCount).add(colCount, cellValue);
					if (rowCount == 0) {
						head.add(cellValue);
					}
					colCount = colCount + 1;
				}
				rowCount = rowCount + 1;
			}
			System.out.println(rowCount + " Loaded :  Lines");
			file.close();
			workbook.close();// to release memory
		} catch (Exception e) {
			System.out.println("Error in parsexcelFile for fileType = " + fileType + e);
		}
		FileContent file = new FileContent();

		if (fileType == 1) {
			file.setFileHeader1(head);
			file.setFileRow1(dataRow);
		} else if (fileType == 2) {
			file.setFileHeader2(head);
			file.setFileRow2(dataRow);
		} else if (fileType == 3) {
			file.setFileHeader3(head);
			file.setFileRow3(dataRow);
		}
		return file;

	}
}
