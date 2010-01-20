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
 */package org.gwtaf.eventbus.event;

import com.google.gwt.event.shared.EventHandler;

/**
 * An extension of {@link GenericEvent} that also holds a payload.
 * 
 * @author Arthur Kalmenson
 * 
 * @param <T>
 *            the payload type.
 * @param <H>
 *            the {@link EventHandler} type.
 */
public abstract class GenericPayloadEvent<T, H extends EventHandler> extends
		GenericEvent<H> {

	private T payload;

	/**
	 * Creates a new <code>GenericPayloadEvent</code> with the given type.
	 * 
	 * @param type
	 *            the {@link Type} for this event.
	 */
	public GenericPayloadEvent(Type<H> type) {
		super(type);
	}

	/**
	 * Returns the payload for this event.
	 * 
	 * @return the payload for this event.
	 */
	public T getPayload() {
		return payload;
	}

	/**
	 * Sets the payload for this event.
	 * 
	 * @param payload
	 *            the payload for this event.
	 */
	public void setPayload(T payload) {
		this.payload = payload;
	}
}
