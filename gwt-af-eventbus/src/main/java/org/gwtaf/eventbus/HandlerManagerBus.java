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
package org.gwtaf.eventbus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.event.shared.GwtEvent.Type;

/**
 * A concrete implementation of {@link EventBus} using the
 * {@link HandlerManager} as the base without any customizations.
 * 
 * @author Arthur Kalmenson
 */
public class HandlerManagerBus extends HandlerManager implements EventBus {

	/**
	 * A {@link Map} with all the {@link EventHandler}s and their associated
	 * {@link HandlerRegistration}s
	 */
	private Map<EventHandler, List<HandlerRegistration>> handlerRegistrationMap;

	/**
	 * Creates a new <code>HandlerManagerBus</code> with a null source.
	 */
	public HandlerManagerBus() {
		super(null);
		handlerRegistrationMap = new HashMap<EventHandler, List<HandlerRegistration>>();
	}

	@Override
	public <H extends EventHandler> HandlerRegistration addHandler(
			Type<H> type, H handler) {

		// retreive the list of registrations for a handler
		List<HandlerRegistration> regList = handlerRegistrationMap.get(handler);
		if (regList == null) {
			regList = new ArrayList<HandlerRegistration>();
			handlerRegistrationMap.put(handler, regList);
		}
		
		// create the registration
		HandlerRegistration registration = super.addHandler(type, handler);
		regList.add(registration);
		return registration;
	}

	public Map<EventHandler, List<HandlerRegistration>> getHandlerRegistrationMap() {
		return handlerRegistrationMap;
	}

}
