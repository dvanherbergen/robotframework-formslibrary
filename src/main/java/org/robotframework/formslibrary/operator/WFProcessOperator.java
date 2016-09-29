package org.robotframework.formslibrary.operator;

import java.awt.Component;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

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

	private Object getActivityWithID(String id) {
		Vector<Object> activities = getProcessActivities();
		for (Object object : activities) {
			Object actID = ObjectUtil.getField(object, "instanceID");
			if (actID.toString().equals(id)) {
				return object;
			}
		}
		return null;
	}

}
