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
package org.gwtaf;

import org.gwtaf.widgets.expanding.GwtTestExpandableTable;
import org.gwtaf.widgets.expanding.GwtTestExpandableTablePresenter;
import org.gwtaf.widgets.generic.GwtTestGwtAfListBox;
import org.gwtaf.widgets.generic.GwtTestRadioButtonGroup;
import org.gwtaf.widgets.generic.GwtTestUiBundleWidget;
import org.gwtaf.widgets.search.GwtTestSearchResultScrollTable;

import com.google.gwt.junit.client.GWTTestCase;
import com.google.gwt.junit.tools.GWTTestSuite;

import junit.framework.Test;
import junit.framework.TestCase;

/**
 * A {@link GWTTestSuite} to run all the {@link GWTTestCase}s for the
 * gwt-af-widgets project.
 * 
 * @author Arthur Kalmenson
 */
public class GwtWidgetsSuite extends TestCase {
	public static Test suite() {
		GWTTestSuite suite = new GWTTestSuite(
				"GWTTestSuite for gwt-af-widgets.");

		suite.addTestSuite(GwtTestExpandableTable.class);
		suite.addTestSuite(GwtTestUiBundleWidget.class);
		suite.addTestSuite(GwtTestExpandableTablePresenter.class);
		suite.addTestSuite(GwtTestSearchResultScrollTable.class);
		suite.addTestSuite(GwtTestGwtAfListBox.class);
		suite.addTestSuite(GwtTestRadioButtonGroup.class);
		return suite;
	}
}
