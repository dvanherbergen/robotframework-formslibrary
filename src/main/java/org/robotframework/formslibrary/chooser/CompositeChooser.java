package org.robotframework.formslibrary.chooser;

import java.awt.Component;

import org.netbeans.jemmy.ComponentChooser;

public class CompositeChooser implements ComponentChooser {

	private ComponentChooser[] choosers;
	
	public CompositeChooser(ComponentChooser...choosers) {
		this.choosers = choosers;
	}
	
	@Override
	public boolean checkComponent(Component comp) {
		
		for (ComponentChooser chooser : choosers) {
			if (chooser.checkComponent(comp)) {
				return true;
			}
		}
		
		return false;
	}

	@Override
	public String getDescription() {
		return choosers[0].getDescription();
	}

}
