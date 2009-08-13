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
package org.gwtaf.widgets.expanding.event;

import org.gwtaf.widgets.Presenter;
import org.gwtaf.widgets.expanding.ExpandableTable;

import com.google.gwt.event.shared.GwtEvent;
import com.google.inject.Inject;

/**
 * A {@link GwtEvent} indicating that a Presenter has been removed. It is thrown
 * when a Presenter is removed from an {@link ExpandableTable}.
 * 
 * @author Arthur Kalmenson
 * 
 * @param <P>
 *            the type of Presenter created.
 */
public class PresenterRemovedEvent<P extends Presenter<?, ?>> extends
		GwtEvent<PresenterRemovedHandler<P>> {

	/**
	 * Handler type.
	 */
	private Type<PresenterRemovedHandler<P>> type;

	/**
	 * The presenter we're using.
	 */
	private P presenter;

	/**
	 * Create a new <code>PresenterRemovedEvent</code> with the given
	 * {@link Type}.
	 * 
	 * @param type
	 *            the Type to use as the associated type.
	 */
	@Inject
	public PresenterRemovedEvent(Type<PresenterRemovedHandler<P>> type) {
		this.type = type;
	}

	public P getPresenter() {
		return presenter;
	}

	public void setPresenter(P presenter) {
		this.presenter = presenter;
	}

	@Override
	protected void dispatch(PresenterRemovedHandler<P> handler) {
		handler.onPresenterRemoved(this);
	}

	@Override
	public Type<PresenterRemovedHandler<P>> getAssociatedType() {
		return type;
	}

}
