package com.jungleegames.finance.paymentGenerate.repostories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.jungleegames.finance.commons.Utility;
import com.jungleegames.finance.commons.config.ProjectConfig;
import com.jungleegames.finance.paymentGenerate.model.PaymentFileGenerationModel;

@Service
public class PaymentGenerationImpl implements PaymentGeneration {

	@Autowired
	private MongoTemplate mongoTemplate;
	
	@Autowired
	ProjectConfig config;

	@Override
	public boolean saveBeneficiary(PaymentFileGenerationModel paymentFileGeneration) {
		PaymentFileGenerationModel fileContent = Utility.encryptContent(paymentFileGeneration, config.getSecretkey());

		if (mongoTemplate.insert(fileContent) != null)
			return true;
		else
			return false;
	}

	@Override
	public PaymentFileGenerationModel getLastRecord() {
		try {
			Query query = new Query();
			query.limit(1);
			query.with(Sort.by(Sort.Direction.DESC, "_id"));
			PaymentFileGenerationModel lastRecord = mongoTemplate.findOne(query, PaymentFileGenerationModel.class);

			if (lastRecord == null) {
				return new PaymentFileGenerationModel();
			} else {
				PaymentFileGenerationModel fileContent = Utility.decryptContent(lastRecord,  config.getSecretkey());
				if (fileContent == null) {
					return new PaymentFileGenerationModel();
				} else {
					return fileContent;
				}

			}

		} catch (Exception e) {
			System.out.println("Error :: " + e.getMessage());
		}
		return new PaymentFileGenerationModel();
	}

//	@Override
//	public PaymentFileGenerationModel getBeneficiaryById(int id) {
//		try {
//			Query query = new Query();
//			query.addCriteria(Criteria.where("_id").is(id));
//			PaymentFileGenerationModel pfg = mongoTemplate.findOne(query, PaymentFileGenerationModel.class);
//			if (pfg == null) {
//				return new PaymentFileGenerationModel();
//			} else {
//				if (Integer.parseInt(pfg.getCount()) == 1) {
//					String[] values = pfg.getFileContent().split(",");
//					System.out.print(values);
//				} else if (Integer.parseInt(pfg.getCount()) > 1) {
//					for (int i = 0; i < Integer.parseInt(pfg.getCount()); i++) {
//						String[] values = pfg.getFileContent().split(",");
//					}
//				}
//			}
//			return pfg;
//		} catch (Exception e) {
//			System.out.println("Error :: " + e.getMessage());
//		}
//		return new PaymentFileGenerationModel();
//	}

}
