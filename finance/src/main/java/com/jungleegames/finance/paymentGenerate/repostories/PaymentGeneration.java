package com.jungleegames.finance.paymentGenerate.repostories;

import com.jungleegames.finance.paymentGenerate.model.PaymentFileGenerationModel;

public interface PaymentGeneration {
	boolean saveBeneficiary(PaymentFileGenerationModel paymentFileGeneration );
	PaymentFileGenerationModel getLastRecord();
//	PaymentFileGenerationModel getBeneficiaryById(int id);

}
