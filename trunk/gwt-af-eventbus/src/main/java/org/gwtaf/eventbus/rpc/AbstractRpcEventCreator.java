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
package org.gwtaf.eventbus.rpc;

import org.gwtaf.command.shared.Action;
import org.gwtaf.command.shared.Response;
import org.gwtaf.eventbus.EventBus;
import org.gwtaf.eventbus.event.ErrorEvent;
import org.gwtaf.eventbus.event.RpcEvent;
import org.gwtaf.eventbus.event.RpcEventHandler;
import org.gwtaf.eventbus.event.RpcResponseEvent;

import com.google.gwt.event.shared.GwtEvent.Type;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Provider;

/**
 * An abstract implementation of {@link RpcEventCreatorService} that uses the
 * default mechanism for creating events and firing off those events as well as
 * the default response
 * 
 * @author Jason Kong
 * 
 */
public abstract class AbstractRpcEventCreator implements RpcEventCreatorService {

	/**
	 * The {@link EventBus} used for event communication
	 */
	protected EventBus eventBus;

	/**
	 * The {@link Provider} of {@link ErrorEvent}s
	 */
	protected Provider<ErrorEvent> errorEventProvider;

	/**
	 * The {@link Type} associated with something that handles {@link RpcEvent}s
	 */
	protected Type<RpcEventHandler> rpcHandlerType;

	/**
	 * Constructor that all extending classes will have to call. For now, simply
	 * ensures that all the fields in the abstract classes exist
	 * 
	 * @param eventBus
	 *            the {@link EventBus}
	 * @param errorEventProvider
	 *            the {@link Provider} of {@link ErrorEvent}s
	 * @param rpcHandlerType
	 *            the {@link Type} associated with rpc event handler
	 */
	public AbstractRpcEventCreator(EventBus eventBus,
			Provider<ErrorEvent> errorEventProvider,
			Type<RpcEventHandler> rpcHandlerType) {

		this.eventBus = eventBus;
		this.errorEventProvider = errorEventProvider;
		this.rpcHandlerType = rpcHandlerType;

	}

	/**
	 * Performs the standard RPC firing. Takes in an {@link Action}, builds the
	 * default {@link AsyncCallback} (using the specified RpcResponseEvent
	 * provider) and fires off the {@link RpcEvent}
	 * 
	 * @param <R>
	 *            The {@link Response} type
	 * @param <A>
	 *            The {@link Action} type
	 * @param action
	 *            The actual {@link Action} command to execute
	 * @param provider
	 *            the {@link Provider} for getting {@link RpcResponseEvent}s to
	 *            fire back on callback success
	 */
	protected <R extends Response, A extends Action<R>> void fireStandardRpc(
			A action, final Provider<RpcResponseEvent<R>> provider) {

		// build a callback
		AsyncCallback<R> callback = new AsyncCallback<R>() {

			public void onFailure(Throwable arg0) {
				System.out.println(arg0);
				eventBus.fireEvent(errorEventProvider.get());
			}

			public void onSuccess(R response) {
				RpcResponseEvent<R> rpcResponseEvent = provider.get();
				rpcResponseEvent.setResponse(response);
				eventBus.fireEvent(rpcResponseEvent);
			}

		};

		// fire off the rpc event
		RpcEvent<A, AsyncCallback<R>, R> rpcEvent = new RpcEvent<A, AsyncCallback<R>, R>(
				rpcHandlerType);
		rpcEvent.setActionAndCallback(action, callback);
		eventBus.fireEvent(rpcEvent);
	}
}
