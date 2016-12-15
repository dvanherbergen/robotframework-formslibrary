package org.robotframework.formslibrary.operator;

import java.awt.Component;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.robotframework.formslibrary.FormsLibraryException;
import org.robotframework.formslibrary.chooser.ByComponentTypeChooser;
import org.robotframework.formslibrary.util.ComponentType;
import org.robotframework.formslibrary.util.ComponentUtil;
import org.robotframework.formslibrary.util.Logger;
import org.robotframework.formslibrary.util.ObjectUtil;

/**
 * Operator for oracle Workflow Components.
 */
public class WFProcessOperator extends AbstractRootComponentOperator {

	String[] ALL_PROCESS_ACTIVITY_FIELDS = { "instanceID", "dispName", "activityStatus", "beginDate", "endDate", "activityResultCode", "iconGeometry",
			"function", "itemType", "description", "userComment", "activityResultCode", "assignedUser", "startFlag", "name" };

	/**
	 * Initialize a window operator with the main oracle forms desktop window.
	 */
	public WFProcessOperator() {
		super(new ByComponentTypeChooser(0, ComponentType.WFPROCESS));
	}

	/**
	 * Dumps a list of the process states for the given WFProcess component in
	 * the application
	 */
	public void listAllProcessActivities() {
		String result = "";
		Vector<Object> procActss = getProcessActivities();
		for (Object processActivity : procActss) {
			if (result.length() > 0) {
				result += "\n";
			}
			result += printActivityNice(processActivity);

		}

		Logger.info(result);
	}

	/**
	 * Dumps a list of the process states for the given WFProcess component in
	 * the application
	 */
	public void listAllProcessActivitiesInfo() {
		String result = "";
		Vector<Object> procActss = getProcessActivities();
		for (Object processActivity : procActss) {
			Map<String, Object> processActivityInfo = getProcessActivityInfo(processActivity);
			if (result.length() > 0) {
				result += "\n";
			}
			result += printActivityAll(processActivityInfo);

		}

		Logger.info(result);
	}

	private Object printActivityAll(Map<String, Object> processActivityInfo) {
		String result = "";
		for (String field : ALL_PROCESS_ACTIVITY_FIELDS) {
			Object value = processActivityInfo.get(field);
			if (result.length() > 0) {
				result += ", ";
			}
			result += field + "=" + value;
		}
		return result;
	}

	private Object printActivityNice(Object act) {
		String formatStr = "%d - %-10s %s - %s%n";

		Integer id = (Integer) ObjectUtil.getField(act, "instanceID");
		String name = (String) ObjectUtil.getField(act, "dispName");
		String status = (String) ObjectUtil.getField(act, "activityStatus");
		String geo = (String) ObjectUtil.getField(act, "iconGeometry");

		return String.format(formatStr, id, status, name, geo);
	}

	@SuppressWarnings("unchecked")
	private Vector<Object> getProcessActivities() {
		Component wfProcess = getSource();

		Object db = ObjectUtil.getField(wfProcess, "db");
		Vector<Object> procActss = (Vector<Object>) ObjectUtil.getField(db, "procActs");
		return procActss;
	}

	private Map<String, Object> getProcessActivityInfo(Object processActivity) {

		Map<String, Object> result = new HashMap<String, Object>();
		for (String field : ALL_PROCESS_ACTIVITY_FIELDS) {
			result.put(field, ObjectUtil.getField(processActivity, field));
		}
		return result;

	}

	public String capture(String targetDirectory) {
		return ComponentUtil.captureToFile(targetDirectory, getSource());
	}

	public void selectActivity(String id) {
		Component wfProcess = getSource();
		Object monitor = ObjectUtil.getField(wfProcess, "theMonitor");

		if (id == null || id.isEmpty()) {
			Object baseProc = ObjectUtil.getField(wfProcess, "baseProc");
			ObjectUtil.invokeMethodWithTypedArg(monitor, "selectHeaderObj", "oracle.apps.fnd.wf.Propertizable", baseProc);
			Logger.info("Selected base Activity ");
		} else {
			Object act = getActivityWithID(id);
			ObjectUtil.invokeMethodWithTypedArg(monitor, "selectWFProcObj", "oracle.apps.fnd.wf.Propertizable", act);
			Logger.info("Selected Activity " + printActivityNice(act));
		}
	}

	private Object getActivityWithID(String identifier) {
		if (identifier == null || identifier.isEmpty()) {
			throw new FormsLibraryException("Attribute identifier is required");
		}
		Vector<Object> activities = getProcessActivities();
		Map<String, Integer> nameCount = new HashMap<String, Integer>();
		for (Object object : activities) {
			Object actID = ObjectUtil.getField(object, "instanceID");
			String dispName = (String) ObjectUtil.getField(object, "dispName");
			Integer count = nameCount.get(dispName);
			count = count == null ? 1 : ++count;
			nameCount.put(dispName, count);
			String name = parseName(identifier);
			int index = parseIndex(identifier);
			if (actID.toString().equals(identifier) || (count.equals(index) && dispName.trim().matches(name))) {
				return object;
			}
		}
		throw new FormsLibraryException("No activity with identifier '" + identifier + "' could be resolved");
	}

	private static final Pattern INDEXED_NAME_PATTERN = Pattern.compile("(.*)(\\[)([0-9]*)(\\])");

	private int parseIndex(String identifier) {
		Matcher m = INDEXED_NAME_PATTERN.matcher(identifier);
		if (m.matches()) {
			return Integer.valueOf(m.group(3));
		} else {
			return 1;
		}
	}

	private String parseName(String identifier) {
		Matcher m = INDEXED_NAME_PATTERN.matcher(identifier);
		if (m.matches()) {
			return m.group(1);
		} else {
			return identifier.trim();
		}
	}

	public Object getProcessActivityProperties(String id) {
		Object activity = getActivityWithID(id);

		Map<String, Object> activityInfo = getProcessActivityInfo(activity);
		return activityInfo;
	}

	private Object getItem() {
		Component wfProcess = getSource();
		Object monitor = ObjectUtil.getField(wfProcess, "theMonitor");
		return ObjectUtil.getField(monitor, "item");

	}

	public Object getProcessAttributes() {
		return getItemAttributes(getItem());
	}

	private Map<String, String> getItemAttributes(Object item) {
		@SuppressWarnings("rawtypes")
		Vector rows = (Vector) ObjectUtil.getField(item, "rows");
		Map<String, String> propMap = new HashMap<String, String>();
		for (Object prop : rows) {
			propMap.put((String) ObjectUtil.getField(prop, "dispName"), (String) ObjectUtil.getField(prop, "value"));
		}
		return propMap;
	}

}
