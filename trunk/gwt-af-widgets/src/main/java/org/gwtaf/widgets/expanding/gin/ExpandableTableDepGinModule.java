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
package org.gwtaf.widgets.expanding.gin;

import org.gwtaf.widgets.expanding.AddButton;
import org.gwtaf.widgets.expanding.ExpandableTable;
import org.gwtaf.widgets.expanding.LabelAddButton;
import org.gwtaf.widgets.expanding.LabelRemoveButton;
import org.gwtaf.widgets.expanding.RemoveButton;

import com.google.gwt.inject.client.AbstractGinModule;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.inject.Provides;

/**
 * The {@link AbstractGinModule} that binds the components need for the
 * {@link ExpandableTable}.
 * 
 * @author Arthur Kalmenson
 */
public class ExpandableTableDepGinModule extends AbstractGinModule {

	@Override
	protected void configure() {
		bind(RemoveButton.class).annotatedWith(ExpandableTableDep.class).to(
				LabelRemoveButton.class);
		bind(AddButton.class).annotatedWith(ExpandableTableDep.class).to(
				LabelAddButton.class);
	}

	/**
	 * Returns the {@link FlexTable} to be used by {@link ExpandableTable}
	 * implementations.
	 * 
	 * @return the {@link FlexTable} to be used by {@link ExpandableTable}
	 *         implementations.
	 */
	@Provides
	@ExpandableTableDep
	public FlexTable mainPanelProvider() {
		FlexTable mainPanel = new FlexTable();
		// TODO: removing this line breaks tests. Instantiates 0'th row? NEEDS
		// FIX
		mainPanel.getRowFormatter().setVerticalAlign(0,
				HasVerticalAlignment.ALIGN_MIDDLE);
		return mainPanel;
	}
}
