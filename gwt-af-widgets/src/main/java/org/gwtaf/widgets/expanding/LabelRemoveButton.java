/*
 * Copyright 2008. Mount Sinai Hospital, Toronto, Canada.
 * 
 * Licensed under the Apache License, Version 2.0. You
 * can find a copy of the license at:
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * IN NO EVENT SHALL MOUNT SINAI HOSPITAL BE LIABLE TO ANY PARTY FOR DIRECT, 
 * INDIRECT, SPECIAL, INCIDENTAL, OR CONSEQUENTIAL DAMAGES, INCLUDING LOST 
 * PROFITS, ARISING OUT OF THE USE OF THIS SOFTWARE AND ITS DOCUMENTATION, 
 * EVEN IF MOUNT SINAI HOSPITAL HAS BEEN ADVISED OF THE POSSIBILITY OF SUCH 
 * DAMAGE.
 * 
 * MOUNT SINAI HOSPITAL SPECIFICALLY DISCLAIMS ANY IMPLIED WARRANTIES OF 
 * MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE. THE SOFTWARE AND 
 * ACCOMPANYING DOCUMENTATION, IF ANY, PROVIDED HEREUNDER IS PROVIDED "AS IS". 
 * MOUNT SINAI HOSPITAL HAS NO OBLIGATION TO PROVIDE MAINTENANCE, SUPPORT, 
 * UPDATES, ENHANCEMENTS, OR MODIFICATIONS. 
 */
package org.gwtaf.widgets.expanding;

import com.google.gwt.i18n.client.Constants;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

/**
 * A concrete implementation of {@link RemoveButton} using a {@link Label}.
 * 
 * @author Arthur Kalmenson
 */
public class LabelRemoveButton extends Label implements RemoveButton {

	public interface LabelRemoveButtonConstants extends Constants {

		@DefaultStringValue("remove")
		String removeLabel();
	}

	/**
	 * Creates a new RemoveButton.
	 * 
	 * @param constants
	 *            the externalised constants to use.
	 */
	@Inject
	public LabelRemoveButton(LabelRemoveButtonConstants constants) {
		this.setText(constants.removeLabel());
		this.setStylePrimaryName("gwtaf-DynamicFlexTable-Remove");
	}

	public Widget getContainingWidget() {
		return this;
	}
}
