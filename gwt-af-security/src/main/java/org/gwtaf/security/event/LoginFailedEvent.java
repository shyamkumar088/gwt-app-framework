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

import org.gwtaf.eventbus.event.GenericPayloadEvent;

import com.google.inject.Inject;

/**
 * An event representing a failed login attempt and contains the error message
 * to display to the user.
 * 
 * @author Arthur Kalmenson
 */
public class LoginFailedEvent extends
		GenericPayloadEvent<String, LoginFailedEventHandler> {

	/**
	 * Creates a new <code>LoginFailedEvent</code> with the injected type.
	 * 
	 * @param type
	 *            the type of this event.
	 */
	@Inject
	public LoginFailedEvent(Type<LoginFailedEventHandler> type) {
		super(type);
	}

	@Override
	protected void dispatch(LoginFailedEventHandler handler) {
		handler.onLoginFailed(this);
	}
}
