package com.jungleegames.finance.commons.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;


@Configuration()
public class ProjectConfig {
	
	@Value("${project.adminName}")
	private String adminName;
	@Value("${project.sendgrid.key}")
	private String sendgridkey;
	@Value("${project.secretkey}")
	private String secretkey;
	@Value("${project.adminEmail.accounting}")
	private String accountingEmail;
	@Value("${project.adminEmail.yokta}")
	private String yoktaEmail;
	@Value("${project.adminEmail.pushpender}")
	private String pushpenderEmail;

	public String getAccountingEmail() {
		return accountingEmail;
	}

	public void setAccountingEmail(String accountingEmail) {
		this.accountingEmail = accountingEmail;
	}

	public String getYoktaEmail() {
		return yoktaEmail;
	}

	public void setYoktaEmail(String yoktaEmail) {
		this.yoktaEmail = yoktaEmail;
	}

	public String getPushpenderEmail() {
		return pushpenderEmail;
	}

	public void setPushpenderEmail(String pushpenderEmail) {
		this.pushpenderEmail = pushpenderEmail;
	}

	public String getAdminName() {
		return adminName;
	}

	public void setAdminName(String adminName) {
		this.adminName = adminName;
	}

	public String getSendgridkey() {
		return sendgridkey;
	}

	public void setSendgridkey(String sendgridkey) {
		this.sendgridkey = sendgridkey;
	}

	public String getSecretkey() {
		return secretkey;
	}

	public void setSecretkey(String secretkey) {
		this.secretkey = secretkey;
	}

}
