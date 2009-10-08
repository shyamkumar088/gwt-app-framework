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

import org.gwtaf.command.shared.Action;
import org.gwtaf.command.shared.Response;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * The {@link EventHandler} for {@link RpcEvent}s. The implementing class
 * handles RpcEvents (of any type), so as such the RpcEventHandler interface is
 * not typed. The interface method however, will be typed.
 * 
 * @author Jason Kong
 * 
 */
public interface RpcEventHandler extends EventHandler {

	/**
	 * Handles when an {@link RpcEvent} is thrown
	 * 
	 * @param rpcEvent
	 *            the thrown {@link RpcEvent}
	 */
	public <A extends Action<R>, C extends AsyncCallback<R>, R extends Response> void onRpcEventThrown(
			RpcEvent<A, C, R> rpcEvent);
}
