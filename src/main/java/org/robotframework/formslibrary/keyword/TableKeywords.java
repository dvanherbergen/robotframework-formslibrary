package org.robotframework.formslibrary.keyword;

import java.awt.Component;
import java.util.ArrayList;
import java.util.List;

import org.robotframework.formslibrary.FormsLibraryException;
import org.robotframework.formslibrary.chooser.ByNameChooser;
import org.robotframework.formslibrary.context.ContextChangeMonitor;
import org.robotframework.formslibrary.operator.ContextOperator;
import org.robotframework.formslibrary.operator.TableOperator;
import org.robotframework.formslibrary.operator.TextFieldOperatorFactory;
import org.robotframework.formslibrary.operator.VerticalScrollBarOperator;
import org.robotframework.formslibrary.util.ComponentType;
import org.robotframework.javalib.annotation.ArgumentNames;
import org.robotframework.javalib.annotation.RobotKeyword;
import org.robotframework.javalib.annotation.RobotKeywords;

@RobotKeywords
public class TableKeywords {

	@RobotKeyword("Select a row in a result table by content.\n\n" + "Example:\n" + "| Select Row | _market_ | _gas_ | \n")
	@ArgumentNames({ "*columnvalues" })
	public void selectRow(String... columnValues) {
		new TableOperator().selectRow(columnValues);
	}

	@RobotKeyword("Select a row in a result table by content. If the row is not visible, the down button in the scrollbar will be pressed up to 50 times in an attempt to try and locate the row. Specify the index (occurrence) of the scrollbar which should be used for scrolling."
			+ "Example:\n" + "| Scroll To Row | _scrollbarIndex_ | _market_ | _gas_ | \n")
	@ArgumentNames({ "scrollbarIndex", "*columnvalues" })
	public void scrollToRow(int scrollBarIndex, String... columnValues) {

		VerticalScrollBarOperator scrollOperator = new VerticalScrollBarOperator(scrollBarIndex - 1);

		TableOperator tableOperator = new TableOperator();

		for (int i = 0; i < 50; i++) {
			if (tableOperator.rowExists(columnValues)) {
				tableOperator.selectRow(columnValues);
				return;
			} else {
				scrollOperator.scrollDown(1);
			}
		}
		throw new FormsLibraryException("Row could not be found within the first 50 records.");
	}

	@RobotKeyword("Set a field value in a table row." + " The row is identified by values\n\n" + "Example:\n"
			+ "| Set Row Field | _field name_ || _field value_ | _first col value_ | _second col value_ | \n")
	@ArgumentNames({ "identifier", "value", "*columnvalues" })
	public void setRowField(String identifier, String value, String... columnValues) {
		new TableOperator().setRowField(identifier, value, columnValues);
	}

	@RobotKeyword("Get a text field value in a table row." + " The field is identified by name. The row is identified by values\n\n" + "Example:\n"
			+ "| Get Row Field | _field name_ || _first col value_ | _second col value_ | \n")
	@ArgumentNames({ "identifier", "*columnvalues" })
	public String getRowField(String identifier, String... columnValues) {
		return new TableOperator().getRowField(identifier, columnValues);
	}

	@RobotKeyword("Select a checkbox in a table row. The first checkbox in a row is identified using index 1, the second one as 2, etc."
			+ " The row is identified by values\n\n" + "Example:\n"
			+ "| Select Row Checkbox | _checkbox index_ | _first col value_ | _second col value_ | \n")
	@ArgumentNames({ "index", "*columnvalues" })
	public void selectRowCheckbox(int index, String... columnValues) {
		new TableOperator().selectRowCheckbox(index, columnValues);
	}

	@RobotKeyword("Deselect a checkbox in a table row. The first checkbox in a row is identified using index 1, the second one as 2, etc."
			+ " The row is identified by values\n\n" + "Example:\n"
			+ "| Select Row Checkbox | _checkbox index_ | _first col value_ | _second col value_ | \n")
	@ArgumentNames({ "index", "*columnvalues" })
	public void deselectRowCheckbox(int index, String... columnValues) {
		new TableOperator().deselectRowCheckbox(index, columnValues);
	}

	@RobotKeyword("Get the state (true/false) of a checkbox in a table row. The first checkbox in a row is identified using index 1, the second one as 2, etc."
			+ " The row is identified by values\n\n" + "Example:\n"
			+ "| ${value}= | Get Row Checkbox | _checkbox index_ | _first col value_ | _second col value_ | \n")
	@ArgumentNames({ "index", "*columnvalues" })
	public boolean getRowCheckbox(int index, String... columnValues) {
		return new TableOperator().getRowCheckboxState(index, columnValues);
	}

	@RobotKeyword("Click on a button in a table row. The first button in a row is identified using index 1, the second one as 2, etc."
			+ " The row is identified by values\n\n" + "Example:\n"
			+ "| Select Row Button | _button index_ | _first col value_ | _second col value_ | \n")
	@ArgumentNames({ "index", "*columnvalues" })
	public void selectRowButton(int index, String... columnValues) {
		ContextChangeMonitor monitor = new ContextChangeMonitor();
		monitor.start();
		new TableOperator().selectRowButton(index, columnValues);
		monitor.stop();
	}

	// @formatter:off
	@RobotKeyword("Get all values for certain columns in a table. Returns an array[row][column]. \n"+
	"\n Example usage:\n" +
	"| @{table}= | Get Table Fields | _col1_ | _col3_ | \n" +
	"| Log Many | @{Table} | | | \n" +
	"| Log | ${Table[3][1]} | | | \n" )
	// @formatter:on
	@ArgumentNames({ "*columnnames" })
	public List<List<String>> getTableFields(String[] identifiers) {

		List<List<String>> result = new ArrayList<List<String>>();

		for (int i = 0; i < identifiers.length; i++) {

			List<Component> columnFields = new ContextOperator()
					.findTableFields(new ByNameChooser(identifiers[i], ComponentType.ALL_TEXTFIELD_TYPES));

			if (i == 0) {
				// first column, so we need to create the rows
				for (Component c : columnFields) {

					String value = TextFieldOperatorFactory.getOperator(c).getValue();
					if (value == null || value.length() == 0) {
						break;
					}
					List<String> row = new ArrayList<String>();
					row.add(value);
					result.add(row);
				}
			} else {
				int j = 0;
				for (Component c : columnFields) {
					if (result.size() > j) {
						List<String> row = result.get(j++);
						if (row != null) {
							String value = TextFieldOperatorFactory.getOperator(c).getValue();
							if (value == null || value.length() == 0) {
								value = "";
							}
							row.add(value);
						}
					}
				}
			}
		}

		return result;
	}
}
