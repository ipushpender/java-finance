package com.jungleegames.finance.users.service;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.jungleegames.finance.commons.config.ProjectConfig;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;

@Component
public class SendGridMail {

	@Autowired
	private ProjectConfig config;

	public Response sendMailToUser(String userMail, long rand) {
		Response response = new Response();
		Email from = new Email("Accounting@jungleegames.com");
		String subject = "JG-Finance :-";
		Email to = new Email(userMail);
		Content content = new Content("text/plain", "your OTP is : " + rand + ". Valid only for 10 minutes");
		Mail mail = new Mail(from, subject, to, content);
		SendGrid sg = new SendGrid(config.getSendgridkey());
		Request request = new Request();
		try {
			request.setMethod(Method.POST);
			request.setEndpoint("mail/send");
			request.setBody(mail.build());
			response = sg.api(request);
//            System.out.println(response.getStatusCode());
//            System.out.println(response.getBody());
//            System.out.println(response.getHeaders());
		} catch (IOException ex) {
			response.setStatusCode(500);
			response.setBody(ex.getMessage());
		}

		return response;
	}

}
