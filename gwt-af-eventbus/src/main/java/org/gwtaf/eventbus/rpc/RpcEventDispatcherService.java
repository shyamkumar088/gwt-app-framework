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

import org.gwtaf.command.rpc.CommandService;
import org.gwtaf.command.rpc.CommandServiceAsync;
import org.gwtaf.command.shared.Action;
import org.gwtaf.command.shared.Response;
import org.gwtaf.eventbus.event.RpcEvent;
import org.gwtaf.eventbus.event.RpcEventHandler;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * The service responsible for dispatching RPCs from {@link RpcEvent}s
 * 
 * @author Jason Kong
 * @param <C>
 *            the {@link CommandServiceAsync} to call remotely
 * 
 */
public class RpcEventDispatcherService<C extends CommandServiceAsync>
		implements RpcEventHandler {

	/**
	 * The {@link CommandService} that this {@link RpcEventDispatcherService}
	 * makes RPC's to
	 */
	private C commandService;

	public RpcEventDispatcherService(C commandService) {
		this.commandService = commandService;
	}

	/**
	 * Executes the specified {@link Action}/{@link Response} pair
	 * 
	 * @param <R>
	 *            the {@link Response} of the given {@link Action}
	 * @param action
	 *            the {@link Action} to execute an RPC for
	 * @param callback
	 *            what to do when the RPC is finished calling
	 */
	public <A extends Action<R>, R extends Response> void execute(
			Action<R> action, AsyncCallback<R> callback) {
		commandService.execute(action, callback);
	}

	public <A extends Action<R>, S extends AsyncCallback<R>, R extends Response> void onRpcEventThrown(
			RpcEvent<A, S, R> rpcEvent) {
		commandService.execute(rpcEvent.getAction(), rpcEvent
				.getAsyncCallback());
	}

}
