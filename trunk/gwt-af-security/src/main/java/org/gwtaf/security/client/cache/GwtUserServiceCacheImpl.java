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

package org.gwtaf.security.client.cache;

import org.gwtaf.security.client.gin.annotation.SecurityLogin;
import org.gwtaf.security.domain.User;

import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * A concrete implementation of the {@link GwtUserServiceCache}. We make it a
 * {@link Singleton} because it only makes sense to have one user cache.
 * 
 * @author Arthur Kalmenson
 */
@Singleton
public class GwtUserServiceCacheImpl implements GwtUserServiceCache {

	/**
	 * The {@link Timer} that fires when the timeout is reached.
	 */
	private Timer timeoutTimer;

	/**
	 * The timeout in seconds before userCache are removed from the
	 */
	private Integer timeout;

	/**
	 * The User we're keeping in cache.
	 */
	private User userInCache;

	/**
	 * Creates a new <code>GwtUserServiceCacheImpl</code> with the given
	 * {@link GwtUserServiceAsync} and the timeout for {@link User}s in cache.
	 * 
	 * @param timeout
	 *            the timeout for objects in the cache.
	 */
	@Inject
	public GwtUserServiceCacheImpl(
			@SecurityLogin Integer timeOut) {
		this.timeout = timeOut;
	}

	public void getCurrentUser(final AsyncCallback<User> callback) {

		// if the current user is null, we need to ask the server, otherwise
		// just return the current user.
		if (userInCache == null) {


		} else {
			callback.onSuccess(userInCache);
		}
	}

	/**
	 * Caches the given user for the given timeout.
	 * 
	 * @param user
	 *            the {@link User} to cache.
	 */
	protected void cacheUser(User user) {

		// save the user.
		this.userInCache = user;

		// create the timer to remove the user after the timeout time.
		timeoutTimer = new Timer() {
			@Override
			public void run() {
				clear();
			}
		};

		// schedule the timer.
		timeoutTimer.scheduleRepeating(timeout);
	}

	public void clear() {
		userInCache = null;
	}

	public void setCacheTimeout(Integer timeOutSeconds) {
		this.timeout = timeOutSeconds;
	}
}
