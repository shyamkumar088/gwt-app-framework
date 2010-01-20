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
package org.gwtaf.security.client.cache;

import org.gwtaf.security.domain.User;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * A cache for the client side {@link User} to prevent going to the server each
 * time we want the current user.
 * 
 * @author Arthur Kalmenson
 */
public interface GwtUserServiceCache {

	/**
	 * Returns the current authenticated {@link User} if they are in the cache
	 * or asks the server if they aren't.
	 * 
	 * @param callback
	 *            the {@link AsyncCallback} that gets called when the user is
	 *            known.
	 */
	public void getCurrentUser(AsyncCallback<User> callback);

	/**
	 * Clears the cache of the current user.
	 */
	public void clear();

	/**
	 * Sets the number of seconds before a {@link User} is automatically removed
	 * from cache and has to be re-authenticated with the server.
	 * 
	 * @param timeOutSeconds
	 *            the number of seconds.
	 */
	public void setCacheTimeout(Integer timeOutSeconds);
}