package com.jungleegames.finance.users.model;

import lombok.Data;

@Data
public class MailResponse {
	
	private String responseMsg;
	private String status;
	
	public String getResponseMsg() {
		return responseMsg;
	}

	public void setResponseMsg(String responseMsg) {
		this.responseMsg = responseMsg;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
