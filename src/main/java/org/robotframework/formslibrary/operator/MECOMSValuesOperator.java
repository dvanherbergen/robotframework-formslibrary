package org.robotframework.formslibrary.operator;

import java.awt.Component;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.netbeans.jemmy.ComponentChooser;
import org.robotframework.formslibrary.FormsLibraryException;
import org.robotframework.formslibrary.chooser.ByComponentTypeChooser;
import org.robotframework.formslibrary.util.ComponentType;
import org.robotframework.formslibrary.util.ObjectUtil;

/**
 * Operator for MECOMS Values.
 */
public class MECOMSValuesOperator extends AbstractComponentOperator {

	/**
	 * Initialize a MECOMS Values operator.
	 */
	public MECOMSValuesOperator() {
		super(new ByComponentTypeChooser(0, ComponentType.MECOMS_VALUES));
	}

	public MECOMSValuesOperator(ComponentChooser chooser) {
		super(chooser);
	}

	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> getValues() {
		List<Object> values = (List<Object>) ObjectUtil.getField(getSource(), "values");
		// returns list of fcs.validationgraph.data.Value
		List<Map<String, Object>> results = new ArrayList<Map<String, Object>>();
		for (Object value : values) {
			results.add(copyValue(value));
		}
		return results;
	}

	private Map<String, Object> copyValue(Object value) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (value == null) {
			return map;
		}
		List<String> fieldNames = ObjectUtil.getFieldNames(value);
		for (String name : fieldNames) {
			map.put(name, ObjectUtil.getField(value, name));
		}
		return map;
	}

	private Object getMECOMSValue(String date) {
		@SuppressWarnings("unchecked")
		List<Object> values = (List<Object>) ObjectUtil.getField(getSource(), "values");

		for (Object value : values) {
			String timestamp = (String) ObjectUtil.getField(value, "timestamp").toString();
			if (timestamp.startsWith(date)) {
				return value;
			}
		}
		return null;

	}

	private int getMECOMSValueIndex(String date) {
		@SuppressWarnings("unchecked")
		List<Object> values = (List<Object>) ObjectUtil.getField(getSource(), "values");
		for (int i = 0; i < values.size(); i++) {
			Object value = values.get(i);
			String timestamp = ObjectUtil.getField(value, "timestamp").toString();
			if (timestamp.startsWith(date)) {
				return i;
			}
		}
		throw new FormsLibraryException("Date " + date + " not found");

	}

	public Map<String, Object> getValue(String date) {
		Object value = getMECOMSValue(date);
		return copyValue(value);
	}

	public void selectValue(String dateFrom, String dateTo) {
		int valueFrom = getMECOMSValueIndex(dateFrom);
		int valueTo;
		if (dateTo != null) {
			valueTo = getMECOMSValueIndex(dateTo);
		} else {
			valueTo = valueFrom;
		}
		Component component = getSource();

		ObjectUtil.invokeMethodWithIntArg(component, "setSelectionStart", valueFrom);
		ObjectUtil.invokeMethodWithIntArg(component, "setSelectionEnd", valueTo);
		Object graphPane = ObjectUtil.getField(component, "graphPane");
		ObjectUtil.invokeMethod(graphPane, "refresh");
		ObjectUtil.invokeMethod(graphPane, "tableToGraph");
	}

}
