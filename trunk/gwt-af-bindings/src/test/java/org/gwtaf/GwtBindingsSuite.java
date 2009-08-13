/*
 * Copyright 2009. Mount Sinai Hospital, Toronto, Canada.
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
package org.gwtaf;

import junit.framework.Test;
import junit.framework.TestCase;

import org.gwtaf.bindings.BindingBuilderGwtTest;
import org.gwtaf.bindings.adapters.AnchorAdapterProviderGwtTest;
import org.gwtaf.bindings.adapters.CheckBoxAdapterProviderGwtTest;
import org.gwtaf.bindings.adapters.DateBoxAdapterProviderGwtTest;
import org.gwtaf.bindings.adapters.LabelAdapterProviderGwtTest;
import org.gwtaf.bindings.adapters.ListBoxAdapterProviderGwtTest;
import org.gwtaf.bindings.adapters.RadioButtonGroupAdapterProviderGwtTest;
import org.gwtaf.bindings.adapters.TextAreaAdapterProviderGwtTest;
import org.gwtaf.bindings.adapters.TextBoxAdapterProviderGwtTest;

import com.google.gwt.junit.tools.GWTTestSuite;

/**
 * The Test Suite for the GWT-af-bindings module
 * 
 * @author Jason Kong
 * 
 */
public class GwtBindingsSuite extends TestCase {
	public static Test suite() {
		GWTTestSuite suite = new GWTTestSuite(
				"GWTTestSuite for gwt-af-bindings.");

		suite.addTestSuite(DateBoxAdapterProviderGwtTest.class);
		suite.addTestSuite(RadioButtonGroupAdapterProviderGwtTest.class);
		suite.addTestSuite(BindingBuilderGwtTest.class);
		suite.addTestSuite(CheckBoxAdapterProviderGwtTest.class);
		suite.addTestSuite(TextAreaAdapterProviderGwtTest.class);
		suite.addTestSuite(TextBoxAdapterProviderGwtTest.class);
		suite.addTestSuite(LabelAdapterProviderGwtTest.class);
		suite.addTestSuite(ListBoxAdapterProviderGwtTest.class);
		suite.addTestSuite(AnchorAdapterProviderGwtTest.class);
		return suite;
	}
}