/*
 * Copyright 2009. Mount Sinai Hospital, Toronto, Canada.
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

import org.gwtaf.command.shared.Action;
import org.gwtaf.command.shared.Response;

import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;

/**
 * A {@link GwtEvent} representing when an RPC is requested from somewhere in
 * the application
 * 
 * @author Jason Kong
 * 
 */
public class RpcEvent<A extends Action<R>, S extends AsyncCallback<R>, R extends Response>
		extends GwtEvent<RpcEventHandler> {

	/**
	 * The handler type that's registered with the eventbus
	 */
	private Type<RpcEventHandler> type;

	/**
	 * The {@link Action} of this {@code RpcEvent}
	 */
	private A action;

	/**
	 * The {@link AsyncCallback} of this {@code RpcEvent}
	 */
	private S callback;

	/**
	 * Creates a new <code>ApplicationStartedEvent</code> with the give
	 * {@link Type}.
	 * 
	 * @param type
	 *            the type of handler that handles this event.
	 */
	@Inject
	public RpcEvent(Type<RpcEventHandler> type) {
		this.type = type;
	}

	public void setActionAndCallback(A action, S callback) {
		this.action = action;
		this.callback = callback;
	}

	public A getAction() {
		return action;
	}

	public S getAsyncCallback() {
		return callback;
	}

	@Override
	protected void dispatch(RpcEventHandler handler) {
		handler.onRpcEventThrown(this);
	}

	@Override
	public Type<RpcEventHandler> getAssociatedType() {
		return type;
	}

}
