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

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.event.shared.GwtEvent.Type;

/**
 * An interface that represents an EventBus in the event bus pattern Ray Ryan
 * presented at Google I/O 2009. This interface just takes the important methods
 * contained in {@link HandlerManager} and adds them to an interface.
 * 
 * @author Arthur Kalmenson
 */
public interface EventBus {

	/**
	 * Adds a handle.
	 * 
	 * @param <H>
	 *            The type of handler
	 * @param type
	 *            the event type associated with this handler
	 * @param handler
	 *            the handler
	 * @return the handler registration, can be stored in order to remove the
	 *         handler later
	 */
	<H extends EventHandler> HandlerRegistration addHandler(
			GwtEvent.Type<H> type, final H handler);

	/**
	 * Fires the given event to the handlers listening to the event's type.
	 * 
	 * Note, any subclass should be very careful about overriding this method,
	 * as adds/removes of handlers will not be safe except within this
	 * implementation.
	 * 
	 * @param event
	 *            the event
	 */
	void fireEvent(GwtEvent<?> event);

	/**
	 * Gets the handler at the given index.
	 * 
	 * @param <H>
	 *            the event handler type
	 * @param index
	 *            the index
	 * @param type
	 *            the handler's event type
	 * @return the given handler
	 */
	<H extends EventHandler> H getHandler(GwtEvent.Type<H> type, int index);

	/**
	 * Gets the number of handlers listening to the event type.
	 * 
	 * @param type
	 *            the event type
	 * @return the number of registered handlers
	 */
	int getHandlerCount(Type<?> type);

	/**
	 * Does this handler manager handle the given event type?
	 * 
	 * @param e
	 *            the event type
	 * @return whether the given event type is handled
	 */
	boolean isEventHandled(Type<?> e);
}
