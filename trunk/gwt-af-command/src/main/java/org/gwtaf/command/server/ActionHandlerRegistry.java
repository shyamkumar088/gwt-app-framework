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
package org.gwtaf.command.server;

import org.gwtaf.command.shared.Action;
import org.gwtaf.command.shared.ActionHandler;
import org.gwtaf.command.shared.Response;

/**
 * A class to register {@link ActionHandler} for specific {@link Action}s. Code
 * was used from David Peterson's gwt-dispatch ActionHandlerRegistry and
 * ClassActionHandlerRegistry interfaces. It can be found here:
 * http://code.google.com/p/gwt-dispatch/
 * 
 * @author Arthur Kalmenson
 * @author David Peterson
 */
public interface ActionHandlerRegistry {

	/**
	 * Registers the specified {@link ActionHandler} to handle a specific
	 * {@link Action}.
	 * 
	 * @param <A>
	 *            the {@link Action} type.
	 * @param <R>
	 *            the {@link Response} type.
	 * @param action
	 *            The action the handler handles.
	 * @param handler
	 *            The handler.
	 */
	public <A extends Action<R>, R extends Response> void addHandler(A action,
			ActionHandler<A, R> handler);

	/**
	 * Removes any registration of the specified {@link Action}.
	 * 
	 * @param <A>
	 *            the {@link Action} type.
	 * @param <R>
	 *            the {@link Response} type.
	 * @param action
	 *            The action the handler handles.
	 */
	public <A extends Action<R>, R extends Response> void removeHandler(
			A action);

	/**
	 * Searches the registry and returns the first handler which supports the
	 * specified action, or <code>null</code> if none is available.
	 * 
	 * @param action
	 *            The action.
	 * @return The handler.
	 */
	public <A extends Action<R>, R extends Response> ActionHandler<A, R> findHandler(
			A action);

	/**
	 * Clears all registered handlers from the registry.
	 */
	public void clearHandlers();
}
