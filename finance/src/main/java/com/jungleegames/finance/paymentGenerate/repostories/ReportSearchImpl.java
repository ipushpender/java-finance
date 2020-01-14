package com.jungleegames.finance.paymentGenerate.repostories;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.jungleegames.finance.commons.Utility;
import com.jungleegames.finance.commons.config.ProjectConfig;
import com.jungleegames.finance.paymentGenerate.model.PaymentFileGenerationModel;

@Service
public class ReportSearchImpl implements ReportSearch {

	@Autowired
	private MongoTemplate mongoTemplate;
	@Autowired
	private ProjectConfig config;

	@Override
	public List<PaymentFileGenerationModel> getDefaultData(String year, String month, String day)
			throws ParseException {// ateUtils.getDate
		Query query = new Query();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = format.parse(year + "-" + month + "-" + day + " 00:00:00");
		long timestamp1 = date.getTime();
		Date date1 = format.parse(year + "-" + month + "-" + day + " 23:57:35");
		long timestamp2 = date1.getTime();
		query.addCriteria(Criteria.where("date").gte(timestamp1).lte(timestamp2));
		query.with(Sort.by(Sort.Direction.DESC, "_id"));
		List<PaymentFileGenerationModel> basicDBObjects = mongoTemplate.find(query, PaymentFileGenerationModel.class);
		if (basicDBObjects == null) {
			return new ArrayList<>();
		} else {
			List<PaymentFileGenerationModel> decryptData = Utility.decryptReportContent(basicDBObjects,config.getSecretkey());
			if (decryptData == null) {
				return new ArrayList<>();
			}
			return decryptData;
		}
	}

	@Override
	public PaymentFileGenerationModel getBeneficiaryById(ObjectId id) {
		try {
			Query query = new Query();
			query.addCriteria(Criteria.where("_id").is(id));
			PaymentFileGenerationModel fileDownload = mongoTemplate.findOne(query, PaymentFileGenerationModel.class);
			if (fileDownload == null) {
				return new PaymentFileGenerationModel();
			} else {
				PaymentFileGenerationModel fileContent = Utility.decryptContent(fileDownload,config.getSecretkey());
				if (fileContent == null) {
					return new PaymentFileGenerationModel();
				}
				return fileContent;
			}
		} catch (Exception e) {
			System.out.print(":::" + e.getMessage());
		}
		return new PaymentFileGenerationModel();
	}

}
