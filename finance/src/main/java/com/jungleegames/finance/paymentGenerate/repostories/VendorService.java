package com.jungleegames.finance.paymentGenerate.repostories;

import java.util.List;
import org.bson.types.ObjectId;

import com.jungleegames.finance.paymentGenerate.model.DynaResp;
import com.jungleegames.finance.paymentGenerate.model.Vendors;

public interface VendorService {
	List<Vendors> getAllVendors();

	Vendors updatedVendor(ObjectId vendorid,Vendors emp);
	Vendors saveVendor(Vendors vendor);
	boolean deleteVendor(ObjectId vendorid);
	List<DynaResp> getVendorName(String val);
	Vendors getUserByName(String name);
	boolean isValidVendor(String name);
}
