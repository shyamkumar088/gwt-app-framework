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
package org.gwtaf.widgets;

/**
 * * An interface representing the Presenter in the Model-View-Presenter
 * pattern.
 * 
 * @author Arthur Kalmenson
 * 
 * @param <V>
 *            the type of view.
 * @param <M>
 *            the type of model.
 */
public interface Presenter<V extends View, M> {

	/**
	 * Returns the View associated with this Presenter.
	 * 
	 * @return the View associated with this Presenter.
	 */
	public V getView();

	/**
	 * Returns the Model of this Presenter.
	 * 
	 * @return the Model of this Presenter.
	 */
	public M getModel();

	/**
	 * Sets the new Model for this Presenter.
	 * 
	 * @param model
	 *            the new Model for this Presenter.
	 */
	public void setModel(M model);
}
