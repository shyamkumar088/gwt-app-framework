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

import org.gwtaf.eventbus.event.GenericEvent;

import com.google.inject.Inject;

/**
 * An event that is used throughout the system to request the currently logged
 * in user.
 * 
 * @author Arthur Kalmenson
 */
public class RequestLoggedInUserEvent extends
		GenericEvent<RequestLoggedInUserEventHandler> {

	/**
	 * Create a new <code>RequestLoggedInUserEvent</code> with the injected
	 * type.
	 * 
	 * @param type
	 *            the type for this event.
	 */
	@Inject
	public RequestLoggedInUserEvent(Type<RequestLoggedInUserEventHandler> type) {
		super(type);
	}

	@Override
	protected void dispatch(RequestLoggedInUserEventHandler handler) {
		handler.onGetLoggedInUser(this);
	}
}
