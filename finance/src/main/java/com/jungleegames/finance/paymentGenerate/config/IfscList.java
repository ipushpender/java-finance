package com.jungleegames.finance.paymentGenerate.config;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

@Service
public class IfscList {
	static List<List<String>> records = new ArrayList<>();
	@PostConstruct
	public  void setIfscCode() {
		try (BufferedReader br = new BufferedReader(new FileReader(
				"bank-ifsc/new-ifsc.csv"))) {
			String line;
			while ((line = br.readLine()) != null) {
				String[] values = line.split(",");
				records.add(Arrays.asList(values));
			}
		} catch (Exception e) {
			System.out.println("Error loading city state info"+e.getMessage());
		}
	}

	public  List<List<String>> getIfscCode()  {
		return records;
	}

}
