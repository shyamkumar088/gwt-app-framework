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
package org.gwtaf.eventbus.event;

import com.google.gwt.event.shared.GwtEvent;
import com.google.inject.Inject;

/**
 * An event representing the start of an application. Usually fired to the event
 * bus in the entry point.
 * 
 * @author Arthur Kalmenson
 */
public class ApplicationStartedEvent extends
		GwtEvent<ApplicationStartedHandler> {

	/**
	 * The handler type that's injected. This is used to register with the event
	 * bus.
	 */
	private Type<ApplicationStartedHandler> type;

	/**
	 * Creates a new <code>ApplicationStartedEvent</code> with the give
	 * {@link Type}.
	 * 
	 * @param type
	 *            the type of handler that handles this event.
	 */
	@Inject
	public ApplicationStartedEvent(Type<ApplicationStartedHandler> type) {
		this.type = type;
	}

	@Override
	protected void dispatch(ApplicationStartedHandler handler) {
		handler.onApplicationStarted(this);
	}

	@Override
	public Type<ApplicationStartedHandler> getAssociatedType() {
		return type;
	}
}
