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

import java.util.List;

import org.gwtaf.widgets.View;

import com.google.gwt.event.dom.client.HasClickHandlers;

/**
 * This interface provides the methods that would be required by a expandable
 * table.
 * 
 * @author Arthur Kalmenson
 * 
 * @param <T>
 *            the type of widget this table will replicate.
 */
public interface ExpandableTable<T> extends View, HasClickHandlers {

	/**
	 * Adds a new row to the dynamic table.
	 */
	void add(T widget);

	/**
	 * Removes the row with the given {@link RemoveButton} and returns the
	 * containing widget of type T removed.
	 * 
	 * @param removeButton
	 *            the remove button.
	 * @return the containing widget of type T removed.
	 */
	T remove(RemoveButton removeButton);

	/**
	 * Clears the widgets in this <code>ExpandableTable</code>.
	 */
	void clear();

	/**
	 * Returns a list of the widget type T that this
	 * <code>ExpandableTable</code> is replicating.
	 * 
	 * @return an array of an array of the widget type T that this
	 *         <code>ExpandableTable</code> is replicating.
	 */
	List<T> getWidgets();

	/**
	 * Returns the {@link AddButton} used by this <code>ExpandableTable</code>.
	 * 
	 * @return the {@link AddButton} used by this <code>ExpandableTable</code>.
	 */
	AddButton getAddButton();

	/**
	 * Returns the list of {@link RemoveButton}s.
	 * 
	 * @return the list of {@link RemoveButton}s.
	 */
	List<RemoveButton> getRemoveButtons();

	/**
	 * Hides the remove buttons where information should not be deleted by the
	 * user.
	 */
	void hideRemoveButtons();

	/**
	 * Marks the odd rows with a css style.
	 */
	void markOdd();

}
