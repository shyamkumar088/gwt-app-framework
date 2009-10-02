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

import java.util.HashMap;
import java.util.Map;

import org.gwtaf.command.shared.Action;
import org.gwtaf.command.shared.ActionHandler;
import org.gwtaf.command.shared.Response;
import org.springframework.stereotype.Component;

/**
 * A concrete implementation of the {@link ActionHandlerRegistry}.
 * 
 * @author Arthur Kalmenson
 */
@Component
public class ActionHandlerRegistryImpl implements ActionHandlerRegistry {

	/**
	 * The map of the {@link Action} class to the {@link ActionHandler} that
	 * will handle that action.
	 */
	private Map<Class<Action<?>>, ActionHandler<?, ?>> actionToHandlerMap =
			new HashMap<Class<Action<?>>, ActionHandler<?, ?>>();

	@SuppressWarnings("unchecked")
	public <A extends Action<R>, R extends Response> void addHandler(Class<A> action,
			ActionHandler<A, R> handler) {
		actionToHandlerMap.put((Class<Action<?>>) action, handler);
	}

	public <A extends Action<R>, R extends Response> void removeHandler(Class<A> action) {
		actionToHandlerMap.remove(action);
	}

	@SuppressWarnings("unchecked")
	public <A extends Action<R>, R extends Response> ActionHandler<A, R> findHandler(
			A action) {

		// generics stuff makes me cast this since it's stored as
		// ActionHandler<?, ?>, but we're pulling it out differently.
		return (ActionHandler<A, R>) actionToHandlerMap.get(action.getClass());
	}

	public void clearHandlers() {
		actionToHandlerMap.clear();
	}
}
