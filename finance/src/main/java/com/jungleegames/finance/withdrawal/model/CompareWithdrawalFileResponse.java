package com.jungleegames.finance.withdrawal.model;

public class CompareWithdrawalFileResponse {
	private String adminBankId;
	private String adminCashfreeId;
	private String adminAmount;
	private String cashfreeId;
	private String cashfreeAmount;
	private String bankId;
	private String bankAmount;

	public String getAdminBankId() {
		return adminBankId;
	}

	public void setAdminBankId(String adminBankId) {
		this.adminBankId = adminBankId;
	}

	public String getAdminCashfreeId() {
		return adminCashfreeId;
	}

	public void setAdminCashfreeId(String adminCashfreeId) {
		this.adminCashfreeId = adminCashfreeId;
	}

	public String getAdminAmount() {
		return adminAmount;
	}

	public void setAdminAmount(String adminAmount) {
		this.adminAmount = adminAmount;
	}

	public String getCashfreeId() {
		return cashfreeId;
	}

	public void setCashfreeId(String cashfreeId) {
		this.cashfreeId = cashfreeId;
	}

	public String getCashfreeAmount() {
		return cashfreeAmount;
	}

	public void setCashfreeAmount(String cashfreeAmount) {
		this.cashfreeAmount = cashfreeAmount;
	}

	public String getBankId() {
		return bankId;
	}

	public void setBankId(String bankId) {
		this.bankId = bankId;
	}

	public String getBankAmount() {
		return bankAmount;
	}

	public void setBankAmount(String bankAmount) {
		this.bankAmount = bankAmount;
	}

}
