package org.robotframework.formslibrary.operator;

import org.robotframework.formslibrary.chooser.ByComponentTypeChooser;
import org.robotframework.formslibrary.util.ComponentType;
import org.robotframework.formslibrary.util.ComponentUtil;

public class OracleEngineOperator extends AbstractRootComponentOperator {

	public OracleEngineOperator() {
		super(new ByComponentTypeChooser(0, ComponentType.ORACLE_MAIN));
	}

	/**
	 * Capture a screenshot of the full window.
	 */
	public String capture(String targetDirectory) {
		return ComponentUtil.captureToFile(targetDirectory, getSource());
	}

}
