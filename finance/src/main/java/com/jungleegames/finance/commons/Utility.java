package com.jungleegames.finance.commons;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import com.jungleegames.finance.paymentGenerate.model.Employees;
import com.jungleegames.finance.paymentGenerate.model.PaymentFileGenerationModel;
import com.jungleegames.finance.paymentGenerate.model.Vendors;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Utility {

	private Utility() {

	}

	private static SecretKeySpec secretKey;

	public static String checkValidation(Employees emp) {
		if (emp.getName().trim().isEmpty() || emp.getName().trim() == null) {
			return "Enter Valid Name !!";
		}
		if (emp.getAccount_name().trim().isEmpty() || emp.getAccount_name().trim() == null) {
			return "Enter Valid Account Name !!";
		}
		if (emp.getBank().trim().isEmpty() || emp.getBank().trim() == null) {
			return "Enter Bank Name !!";
		}
		if (emp.getIfsc().trim().isEmpty() || emp.getIfsc().trim() == null) {
			return "Enter Ifsc Code !!";
		}
		if (emp.getAccount_no().trim().isEmpty() || emp.getAccount_no().trim() == null) {
			return "Enter Valid Account Number !!";
		}
		if (emp.getLocation().trim().isEmpty() || emp.getLocation().trim() == null) {
			return "Enter Location !!";
		}

		return "success";
	}

	public static String checkValidation(Vendors vendor) {
		if (vendor.getName().trim().isEmpty() || vendor.getName().trim() == null) {
			return "Enter Valid Name !!";
		}
		if (vendor.getAccount_name().trim().isEmpty() || vendor.getAccount_name().trim() == null) {
			return "Enter Valid Account Name !!";
		}
		if (vendor.getBank().trim().isEmpty() || vendor.getBank().trim() == null) {
			return "Enter Bank Name !!";
		}
		if (vendor.getIfsc().trim().isEmpty() || vendor.getIfsc().trim() == null) {
			return "Enter Ifsc Code !!";
		}
		if (vendor.getAccount_no().trim().isEmpty() || vendor.getAccount_no().trim() == null) {
			return "Enter Valid Account Number !!";
		}
		if (vendor.getLocation().trim().isEmpty() || vendor.getLocation().trim() == null) {
			return "Enter Location !!";
		}
		return "success";
	}

	public static String convertToSentence(String a) {
		return a.substring(0, 1).toUpperCase() + a.substring(1, a.length()).toLowerCase();
	}

	public static PaymentFileGenerationModel encryptContent(PaymentFileGenerationModel paymentFileGeneration,
			String key) {
		String content;
		if (Integer.parseInt(paymentFileGeneration.getCount()) == 1) {
			String[] values = paymentFileGeneration.getFile_content().split(",");
			values[3] = encrypt(values[3], key);
			content = Arrays.toString(values).replace("[", "").replace("]", "").replaceAll("\\s", "");
			paymentFileGeneration.setFile_content(content);

		} else if (Integer.parseInt(paymentFileGeneration.getCount()) > 1) {
			StringBuilder contents = new StringBuilder();
			String[] row = paymentFileGeneration.getFile_content().split("\n");
			for (int i = 0; i < Integer.parseInt(paymentFileGeneration.getCount()); i++) {
				String str = null;
				String[] values = row[i].split(",");
				values[3] = encrypt(values[3], key);
				str = Arrays.toString(values).replace("[", "").replace("]", "").replaceAll("\\s", "");
				contents.append(str + "\n");
			}
			paymentFileGeneration.setFile_content(contents.toString());
		}
		paymentFileGeneration.setAmount(encrypt(paymentFileGeneration.getAmount(), key));
		return paymentFileGeneration;
	}

	public static PaymentFileGenerationModel decryptContent(PaymentFileGenerationModel paymentFileGeneration,
			String key) {
		String content;
		if (Integer.parseInt(paymentFileGeneration.getCount()) == 1) {
			String[] values = paymentFileGeneration.getFile_content().split(",");
			values[3] = decrypt(values[3], key);
			content = Arrays.toString(values).replace("[", "").replace("]", "").replaceAll("\\s", "");
			paymentFileGeneration.setFile_content(content);
		} else if (Integer.parseInt(paymentFileGeneration.getCount()) > 1) {
			StringBuilder contents = new StringBuilder();
			String[] row = paymentFileGeneration.getFile_content().split("\n");
			for (int i = 0; i < Integer.parseInt(paymentFileGeneration.getCount()); i++) {
				String str = null;
				String[] values = row[i].split(",");
				values[3] = decrypt(values[3], key);
				str = Arrays.toString(values).replace("[", "").replace("]", "").replaceAll("\\s", "");
				contents.append(str + "\n");
			}
			paymentFileGeneration.setFile_content(contents.toString());
		}
		paymentFileGeneration.setAmount(decrypt(paymentFileGeneration.getAmount(), key));
		return paymentFileGeneration;
	}

	public static List<PaymentFileGenerationModel> decryptReportContent(
			List<PaymentFileGenerationModel> paymentFileGeneration, String key) {
		for (int k = 0; k < paymentFileGeneration.size(); k++) {
			String content;
			if (Integer.parseInt(paymentFileGeneration.get(k).getCount()) == 1) {
				String[] values = paymentFileGeneration.get(k).getFile_content().split(",");
				values[3] = decrypt(values[3], key);
				content = Arrays.toString(values).replace("[", "").replace("]", "").replaceAll("\\s", "");
				paymentFileGeneration.get(k).setFile_content(content);
			} else if (Integer.parseInt(paymentFileGeneration.get(k).getCount()) > 1) {
				StringBuilder contents = new StringBuilder();
				String[] row = paymentFileGeneration.get(k).getFile_content().split("\n");
				for (int i = 0; i < Integer.parseInt(paymentFileGeneration.get(k).getCount()); i++) {
					String str = null;
					String[] values = row[i].split(",");
					values[3] = decrypt(values[3], key);
					str = Arrays.toString(values).replace("[", "").replace("]", "").replaceAll("\\s", "");
					contents.append(str + "\n");
				}
				paymentFileGeneration.get(k).setFile_content(contents.toString());
			}
			paymentFileGeneration.get(k).setAmount(decrypt(paymentFileGeneration.get(k).getAmount(), key));

		}
		return paymentFileGeneration;

	}

	private static String encrypt(String strToEncrypt, String secret) {
		try {
			secretKey = new SecretKeySpec(Arrays.copyOf(
					MessageDigest.getInstance("SHA-1").digest(secret.getBytes(StandardCharsets.UTF_8)), 16), "AES");
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			cipher.init(Cipher.ENCRYPT_MODE, secretKey);
			return Base64.getEncoder().encodeToString(cipher.doFinal(strToEncrypt.getBytes("UTF-8")));
		} catch (Exception e) {
			System.out.println("Error while encrypting: " + e.toString());
		}
		return "Not encrypted!!";
	}

	private static String decrypt(String strToDecrypt, String secret) {
		try {
			secretKey = new SecretKeySpec(Arrays.copyOf(
					MessageDigest.getInstance("SHA-1").digest(secret.getBytes(StandardCharsets.UTF_8)), 16), "AES");
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
			cipher.init(Cipher.DECRYPT_MODE, secretKey);
			return new String(cipher.doFinal(Base64.getDecoder().decode(strToDecrypt)));
		} catch (Exception e) {
//			log.error("Error while decrypting: ", e);
			System.out.println("Error while decrypting: " + e);
		}
		return "Not decrypted!!";
	}

	public static boolean isNumeric(String str) {
		return str.matches("-?\\d+(\\.\\d+)?"); // match a number with optional '-' and decimal.
	}

}
