package com.jungleegames.finance.deposit.model;

public class CompareFileResponse {
	private String adminFileId;
	private String adminFileAmount;
	private String adminFileDate;
	private String gatewayFileId;
	private String gatewayFileAmount;
	private String gatewayFileDate;

	public String getAdminFileId() {
		return adminFileId;
	}

	public void setAdminFileId(String adminFileId) {
		this.adminFileId = adminFileId;
	}

	public String getAdminFileAmount() {
		return adminFileAmount;
	}

	public void setAdminFileAmount(String adminFileAmount) {
		this.adminFileAmount = adminFileAmount;
	}

	public String getAdminFileDate() {
		return adminFileDate;
	}

	public void setAdminFileDate(String adminFileDate) {
		this.adminFileDate = adminFileDate;
	}

	public String getGatewayFileId() {
		return gatewayFileId;
	}

	public void setGatewayFileId(String gatewayFileId) {
		this.gatewayFileId = gatewayFileId;
	}

	public String getGatewayFileAmount() {
		return gatewayFileAmount;
	}

	public void setGatewayFileAmount(String gatewayFileAmount) {
		this.gatewayFileAmount = gatewayFileAmount;
	}

	public String getGatewayFileDate() {
		return gatewayFileDate;
	}

	public void setGatewayFileDate(String gatewayFileDate) {
		this.gatewayFileDate = gatewayFileDate;
	}

}
