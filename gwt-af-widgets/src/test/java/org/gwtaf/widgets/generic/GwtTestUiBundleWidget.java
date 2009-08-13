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
package org.gwtaf.widgets.generic;

import com.google.gwt.junit.client.GWTTestCase;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.TextBox;

/**
 * Integration test to ensure {@link UiBundleWidget} can be attached to the DOM.
 * 
 * @author Arthur Kalmenson
 */
public class GwtTestUiBundleWidget extends GWTTestCase {

	/**
	 * Test creating it and adding it to a widget. This will check if it's been
	 * set correctly and can be added to the DOM.
	 */
	public void testCreateAddToWidget() {

		// make the dependencies.
		FlowPanel panel = new FlowPanel();
		Label label = new Label();
		TextBox textBox = new TextBox();

		// create the bundle.
		UiBundleWidget<Label, TextBox> uiBundleWidget = new UiBundleWidget<Label, TextBox>(
				panel, label, textBox);

		// add to a panel.
		SimplePanel simplePanel = new SimplePanel();
		simplePanel.add(uiBundleWidget);

		// check it was added.
		assertEquals(uiBundleWidget, simplePanel.getWidget());
	}

	@Override
	public String getModuleName() {
		return "org.gwtaf.GwtAfWidgetsTesting";
	}

}
