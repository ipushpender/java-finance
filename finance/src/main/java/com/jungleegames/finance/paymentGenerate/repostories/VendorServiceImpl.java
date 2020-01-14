package com.jungleegames.finance.paymentGenerate.repostories;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.text.WordUtils;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.jungleegames.finance.commons.Utility;
import com.jungleegames.finance.paymentGenerate.model.DynaResp;
import com.jungleegames.finance.paymentGenerate.model.Vendors;

@Service
public class VendorServiceImpl implements VendorService {

	@Autowired
	private MongoTemplate mongoTemplate;

	@Override
	public List<Vendors> getAllVendors() {
		Query query = new Query();
		query.with(Sort.by(Sort.Direction.DESC, "_id"));
		return mongoTemplate.find(query, Vendors.class);

	}

	@Override
	public Vendors updatedVendor(ObjectId vendorid, Vendors vendor) {
		Query query = new Query();
		query.addCriteria(Criteria.where("vendorid").is(vendorid));
		Update update = new Update();
		update.set("name", vendor.getName());
		update.set("account_name", vendor.getAccount_name());
		update.set("location", vendor.getLocation());
		update.set("bank", vendor.getBank());
		update.set("account_no", vendor.getAccount_no());
		update.set("ifsc", vendor.getIfsc());
		update.set("empid", vendor.getVendorid());
		FindAndModifyOptions famo = new FindAndModifyOptions();
		famo.returnNew(true);
		return mongoTemplate.findAndModify(query, update, famo, Vendors.class);
	}

	@Override
	public Vendors saveVendor(Vendors vendor) {
		return mongoTemplate.insert(vendor);
	}

	@Override
	public boolean deleteVendor(ObjectId id) {
		Query query = new Query();
		query.addCriteria(Criteria.where("vendorid").is(id));
		return (mongoTemplate.findAndRemove(query, Vendors.class) != null);
	}

	@Override
	public List<DynaResp> getVendorName(String val) {
		String input;
		String input1;
		String input2;
		String input3;
		final char[] delimiters = { ' ', '_' };
		input = val;
		input1 = input.toUpperCase();
		input2 = WordUtils.capitalizeFully(input, delimiters);// Titlecase
		input3 = Utility.convertToSentence(input);// Sentence Case
		Criteria criteria = new Criteria();

		criteria.orOperator(Criteria.where("name").regex("^" + input + ".*"),
				Criteria.where("name").regex("^" + input1 + ".*"), Criteria.where("name").regex("^" + input2 + ".*"),
				Criteria.where("name").regex("^" + input3 + ".*"));
		Query query = new Query(criteria);
		query.fields().include("name");
		return mongoTemplate.find(query, DynaResp.class, "vendors");
	}

	@Override
	public Vendors getUserByName(String name) {
		Vendors vendor = null;
		Query query = new Query();
		query.addCriteria(Criteria.where("name").is(name));
		vendor = mongoTemplate.findOne(query, Vendors.class);
		if (vendor == null) {
			return new Vendors();
		}
		return vendor;
	}

	@Override
	public boolean isValidVendor(String name) {
		Query query = new Query();
		query.addCriteria(Criteria.where("name").is(name));
		return (mongoTemplate.findOne(query, Vendors.class) != null);

	}

}
