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
package org.gwtaf.security.event;

import org.gwtaf.eventbus.event.GenericEvent;

import com.google.gwt.event.shared.GwtEvent;
import com.google.inject.Inject;

/**
 * A {@link GwtEvent} that indicates that there was an authorization error
 * allowing the user to access a particular part of the application.
 * 
 * @author Arthur Kalmenson
 */
public class AuthorizationErrorEvent extends
		GenericEvent<AuthorizationErrorEventHandler> {

	/**
	 * Create a new <code>AuthorizationErrorEvent</code> with the injected type.
	 * 
	 * @param type
	 */
	@Inject
	public AuthorizationErrorEvent(Type<AuthorizationErrorEventHandler> type) {
		super(type);
	}

	@Override
	protected void dispatch(AuthorizationErrorEventHandler handler) {
		handler.onAuthorizationError(this);
	}
}
