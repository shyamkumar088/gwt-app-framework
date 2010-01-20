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
package org.gwtaf.eventbus.event;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

/**
 * A simple abstract generic {@link GwtEvent} that takes care of storing a give
 * {@link Type}.
 * 
 * @author Arthur Kalmenson
 * 
 * @param <H>
 *            the {@link EventHandler} for this event.
 */
public abstract class GenericEvent<H extends EventHandler> extends GwtEvent<H> {

	private final Type<H> type;

	/**
	 * Creates a new <code>GenericEvent</code> with the given type.
	 * 
	 * @param type
	 *            the {@link Type} for this event.
	 */
	public GenericEvent(Type<H> type) {
		this.type = type;
	}

	@Override
	public Type<H> getAssociatedType() {
		return type;
	}
}
