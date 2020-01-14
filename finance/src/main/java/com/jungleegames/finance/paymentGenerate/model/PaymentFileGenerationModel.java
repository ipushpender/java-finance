package com.jungleegames.finance.paymentGenerate.model;

import javax.validation.constraints.NotNull;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

@Document("beneficials")

public class PaymentFileGenerationModel {

	public void setFile_content(String file_content) {
		this.file_content = file_content;
	}

	@NotNull
	private Long date = System.currentTimeMillis();
	@NotNull
	private String type;
	@NotNull
	@JsonSerialize(using = ToStringSerializer.class)
	private ObjectId _id;
	@NotNull
	private String file_content;
	@NotNull
	private String person;
	@NotNull
	private String count;
	@NotNull
	private String amount;

	public Long getDate() {
		return date;
	}

	public void setDate(Long date) {
		this.date = date;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	

	public String getFile_content() {
		return file_content;
	}

	public String getPerson() {
		return person;
	}

	public void setPerson(String person) {
		this.person = person;
	}

	public String getCount() {
		return count;
	}

	public void setCount(String count) {
		this.count = count;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

}
