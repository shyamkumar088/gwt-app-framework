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
package org.gwtaf.command.rpc;

import org.gwtaf.command.shared.Action;
import org.gwtaf.command.shared.Response;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * The GWT {@link RemoteService} that executes {@link Action} items.
 * 
 * @author Arthur Kalmenson
 */
@RemoteServiceRelativePath("../gwt-rpc/org.gwtaf.command.rpc.CommandService")
public interface CommandService extends RemoteService {

	/**
	 * Execute the given {@link Action} and return a corresponding
	 * {@link Response}.
	 * 
	 * @param <R>
	 *            the type of {@link Response}.
	 * @param action
	 *            the {@link Action} to execute.
	 * @return a {@link Response} to the {@link Action} being executed.
	 */
	<R extends Response> R execute(Action<R> action);
}
