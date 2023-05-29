package com.example.bm.Utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.logging.log4j.util.Strings;

import com.example.bm.enums.ItemCategory;
import com.example.bm.enums.UserCategory;


public class Utils {
	
	
	
	public static UserCategory getUserCategory(String type) {
		
			if (type != null && type.equalsIgnoreCase(UserCategory.AFFILATED.toString())) {
				return UserCategory.AFFILATED;
			} else if(type != null && type.equalsIgnoreCase(UserCategory.CUSTOMER.toString())) {
				return UserCategory.CUSTOMER;
			} else if(type != null && type.equalsIgnoreCase(UserCategory.EMPLOYEE.toString())) {
				return UserCategory.EMPLOYEE;
			} else {
				return null;
			} 
	}
	
	
	public static ItemCategory getItemCategory(String type) {
		if (type != null && type.equalsIgnoreCase(ItemCategory.GROCERY.toString())) {
			return ItemCategory.GROCERY;
		} else if(type != null && type.equalsIgnoreCase(ItemCategory.NONGROCERY.toString())) {
			return ItemCategory.NONGROCERY;
		} else {
			return null;
		} 
	}
	

	public static boolean isNullOrEmpty(String value) {
		if (Strings.isBlank(value)) {
			return true;
		} else if (value.equalsIgnoreCase("null")) {
			return true;
		} else {
			return false;
		}
	}

	public static Date parseDate(final String date) {
		try {
			return new SimpleDateFormat("dd/MM/yyyy").parse(date);
		} catch (ParseException e) {
			return null;
		}
	}

}
