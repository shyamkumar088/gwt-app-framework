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
package org.gwtaf.security.command;

import org.gwtaf.command.shared.Response;
import org.gwtaf.security.domain.User;

/**
 * The {@link Response} for {@link LoginUser} action.
 * 
 * @author Arthur Kalmenson
 */
public class LoginUserResponse implements Response {

	private final User authenticatedUser;

	/**
	 * Creates a new <code>LoginUserResponse</code> with the given {@link User}.
	 * 
	 * @param authenticatedUser
	 *            the authenticated user.
	 */
	public LoginUserResponse(User authenticatedUser) {
		this.authenticatedUser = authenticatedUser;
	}

	/**
	 * Returns the authenticated user.
	 * 
	 * @return the authenticated user.
	 */
	public User getAuthenticatedUser() {
		return authenticatedUser;
	}
}
