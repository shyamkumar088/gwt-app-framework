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
 * A {@link Response} to the {@link GetLoggedInUser} command, that contains the
 * currently logged in user, or null if no such user exists.
 * 
 * @author Arthur Kalmenson
 */
public class GetLoggedInUserResponse implements Response {

	private User currentUser = null;

	/**
	 * Creates a new <code>GetCurrentUserResponse</code> with a null current
	 * user.
	 */
	public GetLoggedInUserResponse() {
		
	}

	/**
	 * Creates a new <code>GetCurrentUserResponse</code> with the given
	 * {@link User}.
	 * 
	 * @param currentUser
	 *            the current user.
	 */
	public GetLoggedInUserResponse(User currentUser) {
		this.currentUser = currentUser;
	}

	/**
	 * Returns the current user.
	 * 
	 * @return the current user.
	 */
	public User getCurrentUser() {
		return currentUser;
	}

	public void setCurrentUser(User currentUser) {
		this.currentUser = currentUser;
	}
}
