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
package org.gwtaf.security.cache;

import org.gwtaf.eventbus.EventBus;
import org.gwtaf.security.domain.User;
import org.gwtaf.security.event.RequestCurrentUserEvent;
import org.gwtaf.security.event.RequestCurrentUserEventHandler;
import org.gwtaf.security.event.RequestLoggedInUserEvent;
import org.gwtaf.security.event.ReturnedCurrentUserEvent;
import org.gwtaf.security.event.ReturnedLoggedInUserEvent;
import org.gwtaf.security.event.ReturnedLoggedInUserEventHandler;
import org.gwtaf.security.gin.annotation.SecurityLogin;

import com.google.gwt.event.shared.GwtEvent.Type;
import com.google.gwt.user.client.Timer;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

/**
 * A concrete implementation of the {@link UserCache}. We make it a
 * {@link Singleton} because it only makes sense to have one user cache.
 * 
 * @author Arthur Kalmenson
 */
@Singleton
public class UserCacheImpl implements UserCache {

	/**
	 * The {@link Timer} that fires when the timeout is reached.
	 */
	private Timer timeoutTimer;

	/**
	 * The timeout in seconds before userCache are removed from the cache.
	 */
	private final Integer timeout;

	private User userInCache;

	private EventBus eventBus;
	private Provider<RequestLoggedInUserEvent> requestLoggedInUserProvider;
	private Provider<ReturnedCurrentUserEvent> returnedCurrentUserProvider;

	/**
	 * Creates a new <code>GwtUserServiceCacheImpl</code> with the given
	 * {@link GwtUserServiceAsync} and the timeout for {@link User}s in cache.
	 * 
	 * @param timeout
	 *            the timeout for objects in the cache.
	 */
	@Inject
	public UserCacheImpl(@SecurityLogin Integer timeOut,
			EventBus eventBus,
			Type<ReturnedLoggedInUserEventHandler> returnedLoggedInUserEventType,
			Type<RequestCurrentUserEventHandler> requestCurrentUserEventType,
			Provider<RequestLoggedInUserEvent> requestLoggedInUserProvider,
			Provider<ReturnedCurrentUserEvent> returnedCurrentUserProvider) {
		this.timeout = timeOut;
		this.eventBus = eventBus;
		this.requestLoggedInUserProvider = requestLoggedInUserProvider;
		this.returnedCurrentUserProvider = returnedCurrentUserProvider;
		
		eventBus.addHandler(returnedLoggedInUserEventType, this);
		eventBus.addHandler(requestCurrentUserEventType, this);
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

	protected void clear() {
		userInCache = null;
	}

	public void onGetCurrentUser(RequestCurrentUserEvent event) {

		// if the cache isn't empty, fire off the returning event. Otherwise, we
		// need to fire a RequestLoggedInUserEvent and let that handler fire the
		// returning event.
		if (userInCache != null) {
			ReturnedCurrentUserEvent returnedCurrentUserEvent =
					returnedCurrentUserProvider.get();
			returnedCurrentUserEvent.setPayload(userInCache);
			eventBus.fireEvent(returnedCurrentUserEvent);
		} else {
			RequestLoggedInUserEvent requestLoggedInUserEvent =
					requestLoggedInUserProvider.get();
			eventBus.fireEvent(requestLoggedInUserEvent);
		}
	}

	public void onReturnedLoggedInUser(ReturnedLoggedInUserEvent event) {
		cacheUser(event.getPayload());
		ReturnedCurrentUserEvent returnedCurrentUserEvent =
				returnedCurrentUserProvider.get();
		returnedCurrentUserEvent.setPayload(userInCache);
		eventBus.fireEvent(returnedCurrentUserEvent);
	}
}
