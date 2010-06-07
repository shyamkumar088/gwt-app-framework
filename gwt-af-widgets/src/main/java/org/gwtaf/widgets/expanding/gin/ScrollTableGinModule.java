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

import com.google.gwt.gen2.table.client.FixedWidthFlexTable;
import com.google.gwt.gen2.table.client.FixedWidthGrid;
import com.google.gwt.gen2.table.client.ScrollTable;
import com.google.gwt.inject.client.AbstractGinModule;
import com.google.inject.Provides;

/**
 * Gin module for dynamic search results table.
 * 
 * @author Sergey Vesselov
 */
public class ScrollTableGinModule extends AbstractGinModule {

	@Override
	protected void configure() {

	}

	/**
	 * Small hack to bypass Gin complaining about no default constructors of
	 * ScrollTable.
	 * 
	 * @return dummy {@link ScrollTable}
	 */
	@Provides
	public ScrollTable scrollTableProvider() {
		FixedWidthFlexTable headingsTable = new FixedWidthFlexTable();
		FixedWidthGrid grid = new FixedWidthGrid(1, 1);
		ScrollTable table = new ScrollTable(grid, headingsTable);
		return table;
	}

}
