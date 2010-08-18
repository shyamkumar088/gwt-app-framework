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
package org.gwtaf.widgets.search;

import com.google.gwt.gen2.table.client.FixedWidthGrid;
import com.google.gwt.gen2.table.event.client.CellHighlightEvent;
import com.google.gwt.gen2.table.event.client.CellHighlightHandler;
import com.google.gwt.gen2.table.event.client.CellUnhighlightEvent;
import com.google.gwt.gen2.table.event.client.CellUnhighlightHandler;
import com.google.gwt.gen2.table.event.client.TableEvent.Cell;
import com.google.gwt.user.client.Event;

public class SearchDataGrid extends FixedWidthGrid {

	private int lastRowIndex;
	private int lastCellIndex;
	private CellClickedHandler clickHandler;

	public SearchDataGrid() {
		setUp();
	}

	public SearchDataGrid(int rows, int cols) {
		super(rows, cols);
		setUp();
	}

	private void setUp() {
		
		this.sinkEvents(Event.ONCLICK);
		
		addCellHighlightHandler(new CellHighlightHandler() {
			public void onCellHighlight(CellHighlightEvent event) {
				Cell cell = event.getValue();
				registerHighlight(cell.getRowIndex(), cell.getCellIndex());
			}
		});
		addCellUnhighlightHandler(new CellUnhighlightHandler() {
			public void onCellUnhighlight(CellUnhighlightEvent event) {
				unHighlightRow(event.getValue().getRowIndex());
			}
		});
	}

	protected void unHighlightRow(int rowIndex) {
		getRowFormatter().removeStyleName(rowIndex, "HighlighedRow");
		
		// put back odd row if needed
		if (rowIndex %2 == 1) {
			getRowFormatter().addStyleName(lastRowIndex, "gwtaf-oddRow");
		}
	}

	public void onBrowserEvent(Event event) {
		super.onBrowserEvent(event);
		
		if (event.getType().equals("click"))  {
			clickHandler.onCellClicked(lastRowIndex, lastCellIndex);
		}
	}

	protected void registerHighlight(int rowIndex, int cellIndex) {

		this.lastRowIndex = rowIndex;
		this.lastCellIndex = cellIndex;
		
		// remove odd row if needed
		if (rowIndex %2 == 1) {
			getRowFormatter().removeStyleName(lastRowIndex, "gwtaf-oddRow");
		}
		
		// add highlight to new row.
		getRowFormatter().addStyleName(lastRowIndex, "HighlighedRow");
	}

	public void addCellClickHandler(CellClickedHandler clickHandler) {
		this.clickHandler = clickHandler;
	}
}
