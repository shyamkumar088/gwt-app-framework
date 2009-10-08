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

import com.google.gwt.event.shared.EventHandler;

/**
 * An {@link EventHandler} for handling {@link RpcResponseEvent}s
 * 
 * @author Jason Kong
 * 
 * @param <R>
 *            The {@link Response} type associated with the
 *            {@link RpcResponseEvent} that this handler handles
 */
public interface RpcResponseEventHandler<R extends Response> extends
		EventHandler {

	/**
	 * Handles when an {@link RpcEvent} is thrown
	 * 
	 * @param rpcEvent
	 *            the thrown {@link RpcEvent}
	 */
	public void onRpcResponseEventThrown(RpcResponseEvent<R> rpcEvent);

}
