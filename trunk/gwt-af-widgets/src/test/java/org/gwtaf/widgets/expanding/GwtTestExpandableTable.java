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

import org.gwtaf.widgets.expanding.gin.ExpandableTableDep;
import org.gwtaf.widgets.expanding.gin.ExpandableTableDepGinModule;

import com.google.gwt.core.client.GWT;
import com.google.gwt.inject.client.GinModules;
import com.google.gwt.inject.client.Ginjector;
import com.google.gwt.junit.client.GWTTestCase;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.TextBox;
import com.google.inject.Provider;

/**
 * Test whether the {@link ExpandableTableDepGinModule} can return the components
 * required to make an {@link ExpandableTable}.
 * 
 * @author Arthur Kalmenson
 */
public class GwtTestExpandableTable extends GWTTestCase {

	/**
	 * The test {@link Ginjector} we'll use to test injections for the
	 * {@link ExpandableTable} implementation.
	 * 
	 * @author Arthur Kalmenson
	 */
	@GinModules(ExpandableTableDepGinModule.class)
	public static interface ExpandableTableGwtTestGinjector extends Ginjector {
		
		@ExpandableTableDep
		FlexTable mainPanel();

		@ExpandableTableDep
		AddButton addButton();

		@ExpandableTableDep
		Provider<RemoveButton> removeButtonProvider();
	}

	/**
	 * The {@link Ginjector} that'll return the components we need.
	 */
	private ExpandableTableGwtTestGinjector injector;

	@Override
	protected void gwtSetUp() throws Exception {
		super.gwtSetUp();

		injector = GWT.create(ExpandableTableGwtTestGinjector.class);
	}

	/**
	 * Test to ensure the components for the table are not null.
	 */
	public void testComponents() {
		assertNotNull(injector.mainPanel());
		assertNotNull(injector.addButton());
		assertNotNull(injector.removeButtonProvider());
	}

	/**
	 * Test adding, getting and remove from the {@link ExpandableTable} we'll
	 * create from the GIN variables.
	 */
	public void testAddGetRemove() {

		// create the expandable table.
		ExpandableTable<TextBox> expandable = new ExpandableFlexTable<TextBox>(
				injector.mainPanel(), injector.addButton(),
				injector.removeButtonProvider());

		// add two items to it.
		expandable.add(new TextBox());
		expandable.add(new TextBox());

		// check that we have a list with two textboxes returned.
		assertEquals(2, expandable.getWidgets().size());

		// remove and check we have no widgets.
//		expandable.remove(0);
//		expandable.remove(0);
//		assertEquals(0, expandable.getWidgets().size());

	}

	@Override
	public String getModuleName() {
		return "org.gwtaf.GwtAfWidgetsTesting";
	}
}
