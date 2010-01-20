/*
 * Copyright 2010. Mount Sinai Hospital, Toronto, Canada.
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
package org.gwtaf.security.event;

import org.gwtaf.eventbus.event.GenericPayloadEvent;
import org.gwtaf.security.domain.User;

import com.google.gwt.event.shared.GwtEvent;
import com.google.inject.Inject;

/**
 * A {@link GwtEvent} that represents a response to the request for the current
 * {@link User}. It contains the current {@link User} and null if there isn't
 * one.
 * 
 * @author Arthur Kalmenson
 */
public class ReturnedCurrentUserEvent extends
		GenericPayloadEvent<User, ReturnedCurrentUserEventHandler> {

	/**
	 * Creates a new <code>ReturnedCurrentUserEvent</code> with the inject Type.
	 * 
	 * @param type
	 *            the type for this event.
	 */
	@Inject
	public ReturnedCurrentUserEvent(Type<ReturnedCurrentUserEventHandler> type) {
		super(type);
	}

	@Override
	protected void dispatch(ReturnedCurrentUserEventHandler handler) {
		handler.onReturnedCurrentUser(this);
	}
}
