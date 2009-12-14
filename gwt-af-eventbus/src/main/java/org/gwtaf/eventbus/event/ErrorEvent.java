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
package org.gwtaf.eventbus.event;

import com.google.gwt.event.shared.GwtEvent;
import com.google.inject.Inject;

/**
 * A {@link GwtEvent} that represents and error in the system.
 * 
 * @author Arthur Kalmenson
 */
public class ErrorEvent extends GwtEvent<ErrorEventHandler> {

	/**
	 * The type of the event.
	 */
	private Type<ErrorEventHandler> type;

	/**
	 * Creates a new <code>ErrorEvent</code> with the given type.
	 * 
	 * @param type
	 *            the type that will be used to handle the event.
	 */
	@Inject
	public ErrorEvent(Type<ErrorEventHandler> type) {
		this.type = type;
	}

	@Override
	public Type<ErrorEventHandler> getAssociatedType() {
		return type;
	}

	@Override
	protected void dispatch(ErrorEventHandler handler) {
		handler.onError(this);
	}
}
