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

import org.gwtaf.command.shared.Action;

/**
 * The {@link Action} to login a user. Contains the username and password.
 * 
 * @author Arthur Kalmenson
 */
public class LoginUser implements Action<LoginUserResponse> {

	private String username, password;

	public LoginUser () {
		
	}
	
	/**
	 * Creates a new <code>LoginUser</code> with the given username and
	 * password.
	 * 
	 * @param username
	 *            the username.
	 * @param password
	 *            the password.
	 */
	public LoginUser(String username, String password) {
		this.username = username;
		this.password = password;
	}

	/**
	 * Returns the username.
	 * 
	 * @return the username.
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * Returns the password.
	 * 
	 * @return the password.
	 */
	public String getPassword() {
		return password;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
