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

import static org.mockito.Mockito.*;

import org.gwtaf.command.shared.Action;
import org.gwtaf.command.shared.ActionHandler;
import org.gwtaf.command.shared.NoActionHandlerFoundException;
import org.gwtaf.command.shared.Response;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Test the {@link CommandDispatcherServlet}.
 * 
 * @author Arthur Kalmenson
 */
public class CommandDispatcherServletTest {

	/**
	 * Mock of the {@link Action}.
	 */
	@Mock
	private Action<Response> action;

	/**
	 * Mock of the {@link ActionHandler}.
	 */
	@Mock
	private ActionHandler<Action<Response>, Response> actionHandler;

	/**
	 * Mock of the {@link ActionHandlerRegistry}.
	 */
	@Mock
	private ActionHandlerRegistry actionHandlerRegistryMock;

	/**
	 * The {@link CommandDispatcherServlet} we're testing.
	 */
	private CommandDispatcherServlet servlet;

	@BeforeMethod
	public void initBefore() {
		MockitoAnnotations.initMocks(this);

		servlet = new CommandDispatcherServlet();
	}

	/**
	 * Execute with a null {@link ActionHandlerRegistry}. We're expecting an
	 * {@link AssertionError}.
	 */
	@Test(expectedExceptions = AssertionError.class)
	public void executeWithNullRegistry() {
		servlet.execute(action);
	}

	/**
	 * Execute a mock command.
	 */
	@Test
	public void executeCommand() {

		// set up the mocks.
		when(actionHandlerRegistryMock.findHandler(action)).thenReturn(
				actionHandler);

		// execute the command.
		servlet.setActionHandlerRegistry(actionHandlerRegistryMock);
		servlet.execute(action);

		// verify the behaviour.
		verify(actionHandler).execute(action);
	}

	/**
	 * Test having the registry return a null {@link ActionHandler}. We're
	 * expecting a {@link NoActionHandlerFoundException} to be thrown.
	 */
	@Test(expectedExceptions = NoActionHandlerFoundException.class)
	public void executeWithNull() {

		// set up the mock.
		when(actionHandlerRegistryMock.findHandler(action)).thenReturn(null);

		// execute the command.
		servlet.setActionHandlerRegistry(actionHandlerRegistryMock);
		servlet.execute(action);
	}
}
