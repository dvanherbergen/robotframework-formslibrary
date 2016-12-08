package org.robotframework.formslibrary.keyword;

import org.robotframework.formslibrary.operator.MECOMSValuesOperator;
import org.robotframework.javalib.annotation.ArgumentNames;
import org.robotframework.javalib.annotation.RobotKeyword;
import org.robotframework.javalib.annotation.RobotKeywordOverload;
import org.robotframework.javalib.annotation.RobotKeywords;

@RobotKeywords
public class MECOMSValuesKeywords {

	@RobotKeyword("Returns a List with Dictionary Like data structure containing all the MECOMS values\n\n"
			+ " Example:\n | Get MECOMS Values | \n\"")
	public Object getMECOMSValues() {
		return new MECOMSValuesOperator().getValues();
	}

	@RobotKeyword("Returns a the MECOMS values for the given date time, time is treated as a string \n\n"
			+ "Example:\n Get MECOMS Value | 20-01-2014 00:15:00|\n")
	@ArgumentNames({ "date" })
	public Object getMECOMSValue(String date) {

		return new MECOMSValuesOperator().getValue(date);
	}

	@RobotKeyword("Select the MECOMS values from the given date till the given date. If only one date is provided, just this record will be selected. \n\n"
			+ "Example:\n Select MECOMS Value | 20-01-2014 00:15:00|\n" + "\n Select MECOMS Value | 20-01-2014 00:15:00 | 20-01-2015 00:15:00|\n")
	@ArgumentNames({ "dateFrom", "dateTo=" })
	public void selectMECOMSValue(String dateFrom, String dateTo) {

		new MECOMSValuesOperator().selectValue(dateFrom, dateTo);
	}

	@RobotKeywordOverload
	public void selectMECOMSValue(String dateFrom) {
		selectMECOMSValue(dateFrom, null);
	}

}
