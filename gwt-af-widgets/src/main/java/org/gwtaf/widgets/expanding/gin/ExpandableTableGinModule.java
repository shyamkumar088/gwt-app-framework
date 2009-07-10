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

import org.gwtaf.widgets.expanding.ExpandableTable;
import org.gwtaf.widgets.expanding.LabelRemoveButton;
import org.gwtaf.widgets.expanding.RemoveButton;
import org.gwtaf.widgets.expanding.ExpandableFlexTable.ExpandableFlexTableConstants;

import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.inject.client.AbstractGinModule;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.Label;
import com.google.inject.Provides;

/**
 * The {@link AbstractGinModule} that binds the components need for the
 * {@link ExpandableTable}.
 * 
 * @author Arthur Kalmenson
 */
public class ExpandableTableGinModule extends AbstractGinModule {

	@Override
	protected void configure() {
		bind(RemoveButton.class).to(LabelRemoveButton.class);
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
		mainPanel.getRowFormatter().setVerticalAlign(0,
				HasVerticalAlignment.ALIGN_TOP);
		return mainPanel;
	}

	/**
	 * Returns the {@link HasClickHandlers} to be used by
	 * {@link ExpandableTable} implementations as an add button.
	 * 
	 * @return the {@link HasClickHandlers} to be used by
	 *         {@link ExpandableTable} implementations as an add button.
	 */
	@Provides
	@ExpandableTableDep
	public HasClickHandlers addButtonProvider(
			ExpandableFlexTableConstants constants) {

		return new Label(constants.addLabel());
	}
}
