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

import org.gwtaf.command.rpc.CommandService;
import org.gwtaf.command.shared.Action;
import org.gwtaf.command.shared.ActionHandler;
import org.gwtaf.command.shared.NoActionHandlerFoundException;
import org.gwtaf.command.shared.Response;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * The {@link RemoteServiceServlet} that dispatches {@link Action}s to the
 * appropriate {@link ActionHandler}.
 * 
 * @author Arthur Kalmenson
 */
@SuppressWarnings("serial")
public class CommandDispatcherServlet extends RemoteServiceServlet implements
		CommandService {

	/**
	 * The {@link ActionHandlerRegistry} that has the {@link ActionHandler}s for
	 * given {@link Action}.
	 */
	@Autowired
	private ActionHandlerRegistry actionHandlerRegistry;

	public <R extends Response> R execute(Action<R> action) {

		// check that the registry is set.
		assert actionHandlerRegistry != null;

		// get the required handler.
		ActionHandler<Action<R>, R> handler =
				actionHandlerRegistry.findHandler(action);
		R response = null;

		// if it's not null, execute and save the Response to return, otherwise
		// thrown a NoActionHandlerFoundException.
		if (handler != null) {
			response = handler.execute(action);
		} else {
			throw new NoActionHandlerFoundException(getClass().getName()
					+ ": No ActionHandler was found for the following Action: "
					+ action);
		}

		return response;
	}

	/**
	 * Set the {@link ActionHandlerRegistry}.
	 * 
	 * @param actionHandlerRegistry
	 *            the new {@link ActionHandlerRegistry}.
	 */
	public void setActionHandlerRegistry(
			ActionHandlerRegistry actionHandlerRegistry) {
		this.actionHandlerRegistry = actionHandlerRegistry;
	}
}
