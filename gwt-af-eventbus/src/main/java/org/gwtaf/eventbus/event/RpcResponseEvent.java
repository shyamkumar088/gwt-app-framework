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
package org.gwtaf.eventbus.event;

import org.gwtaf.command.shared.Response;

import com.google.gwt.event.shared.GwtEvent;
import com.google.inject.Inject;

/**
 * A {@link GwtEvent} for when RPCs respond
 * 
 * @author Jason Kong
 * 
 * @param <R>
 *            The {@link Response} associated with this {@code RpcResponseEvent}
 */
public class RpcResponseEvent<R extends Response> extends
		GwtEvent<RpcResponseEventHandler<R>> {

	/**
	 * The {@link Response} that is returned by the RPC and sent along with this
	 * {@code RpcResponseEvent}
	 */
	private R response;

	/**
	 * The {@link Type} associated with this Event
	 */
	private Type<RpcResponseEventHandler<R>> type;

	/**
	 * Creates a new {@link RpcResponseEvent} with the given type
	 * 
	 * @param type
	 *            the {@link Type} of this event
	 */
	@Inject
	public RpcResponseEvent(Type<RpcResponseEventHandler<R>> type) {
		this.type = type;
	}

	public void setResponse(R response) {
		this.response = response;
	}

	public R getResponse() {
		return response;
	}

	@Override
	protected void dispatch(RpcResponseEventHandler<R> arg0) {
		arg0.onRpcResponseEventThrown(this);
	}

	@Override
	public Type<RpcResponseEventHandler<R>> getAssociatedType() {
		return type;
	}

}
