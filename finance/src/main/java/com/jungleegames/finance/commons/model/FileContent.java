package com.jungleegames.finance.commons.model;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class FileContent {
	List<String> header;
	List<ArrayList<String>> dataRoes;
	List<String> header1;
	List<ArrayList<String>> file1;
	List<String> header2;
	List<ArrayList<String>> file2;
	List<String> header3;
	List<ArrayList<String>> file3;

	

	public List<String> getHeader() {
		return header;
	}

	public void setHeader(List<String> header) {
		this.header = header;
	}

	public List<ArrayList<String>> getDataRoes() {
		return dataRoes;
	}

	public void setDataRoes(List<ArrayList<String>> dataRoes) {
		this.dataRoes = dataRoes;
	}

	public void setFileHeader1( List<String> header) {
		this.header1 = header;
	}
	public void setFileHeader2( List<String> header) {
		this.header2 = header;
	}
	public void setFileRow1(List<ArrayList<String>> file) {
		this.file1 = file;
	}
	public void setFileRow2(List<ArrayList<String>> file) {
		this.file2 = file;
	}
	public List<String> getFileHeader1() {
		return header1;
	}

	public List<ArrayList<String>>  getFileRow2() {
		return file2;
	}
	public List<String> getFileHeader2() {
		return header2;
	}

	public List<ArrayList<String>>  getFileRow1() {
		return file1;
	}
	public List<String> getFileHeader3() {
		return header3;
	}

	public void setFileHeader3(List<String> header3) {
		this.header3 = header3;
	}

	public List<ArrayList<String>> getFileRow3() {
		return file3;
	}

	public void setFileRow3(List<ArrayList<String>> file3) {
		this.file3 = file3;
	}
//	public List<Object> getFile1(List<ArrayList<String>> file, List<String> header) {
//		return Arrays.asList(file1, header1); 
//	}
//	public List<Object> getFile2(List<ArrayList<String>> file, List<String> header) {
//		return Arrays.asList(file2, header2); 
//	}
}
