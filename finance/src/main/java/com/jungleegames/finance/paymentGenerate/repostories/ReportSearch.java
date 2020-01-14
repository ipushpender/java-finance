package com.jungleegames.finance.paymentGenerate.repostories;

import java.text.ParseException;
import java.util.List;

import org.bson.types.ObjectId;

import com.jungleegames.finance.paymentGenerate.model.PaymentFileGenerationModel;

public interface ReportSearch {
	List<PaymentFileGenerationModel> getDefaultData(String year,String month,String day) throws ParseException ;
	PaymentFileGenerationModel getBeneficiaryById(ObjectId id);
}
