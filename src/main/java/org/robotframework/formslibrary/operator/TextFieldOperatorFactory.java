package org.robotframework.formslibrary.operator;

import java.awt.Component;
import java.awt.Point;
import java.util.List;
import java.util.stream.Stream;

import org.robotframework.formslibrary.FormsLibraryException;
import org.robotframework.formslibrary.chooser.ByNameChooser;
import org.robotframework.formslibrary.chooser.ByPrecedingLabelChooser;
import org.robotframework.formslibrary.util.ComponentType;
import org.robotframework.formslibrary.util.ComponentUtil;

/**
 * Operator factory for creating operators for select and text fields.
 */
public class TextFieldOperatorFactory {

	/**
	 * Create an operator for the given component.
	 * 
	 * @return TextFieldOperator or SelectFieldOperator depending on the type of
	 *         component that was provided.
	 */
	public static TextFieldOperator getOperator(Component component) {

		if (component != null) {
			if (ComponentType.SELECT_FIELD.matches(component)) {
				return new SelectFieldOperator(component);
			} else {
				return new TextFieldOperator(component);
			}
		}

		return null;
	}

	/**
	 * Create a field operator for a component with the given name.
	 * 
	 * @return TextFieldOperator or SelectFieldOperator depending on the type of
	 *         component that was found.
	 */
	public static TextFieldOperator getOperator(String identifier) {

		Component component = new ContextOperator().findTextField(new ByNameChooser(identifier, ComponentType.ALL_TEXTFIELD_TYPES));
		if (component != null) {
			return getOperator(component);
		}

		return new TextFieldOperator(new ByPrecedingLabelChooser(identifier));
	}

	/**
	 * Create a field operator for a component with the given name.
	 * 
	 * @return TextFieldOperator or SelectFieldOperator depending on the type of
	 *         component that was found.
	 */
	public static TextFieldOperator getOperator(LabelOperator labelOperator) {
		Point labelLocation = ComponentUtil.getLocationInWindow(labelOperator.getSource());
		List<Component> textFields = new ContextOperator().findNonTableTextFields();

		Component textFieldToTheLeft;
		try {
			Stream<Component> textFieldsToTheLeft = textFields.stream()
					.filter(p -> ComponentUtil.getLocationInWindow(p).getY() == labelLocation.getY()
							&& ComponentUtil.getLocationInWindow(p).getX() > labelLocation.getX());
			textFieldToTheLeft = textFieldsToTheLeft.min((x, y) -> Integer.compare(x.getX(), y.getX())).orElse(null);
		} catch (Exception e) {
			e.printStackTrace();
			throw new FormsLibraryException("Error in finding text field", e);
		}

		if (textFieldToTheLeft != null) {
			return getOperator(textFieldToTheLeft);
		}

		return null;
	}

}
