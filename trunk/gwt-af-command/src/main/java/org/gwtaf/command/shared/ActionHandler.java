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
package org.gwtaf.command.shared;

/**
 * A class that executes specific {@link Action}s and returns the
 * {@link Response} to that action.
 * 
 * @author Arthur Kalmenson
 * 
 * @param <A>
 *            the {@link Action} being handled.
 * @param <R>
 *            the {@link Response} to that Action.
 */
public interface ActionHandler<A extends Action<R>, R extends Response> {

	/**
	 * Executes the {@link Action} and returns a {@link Response} to that
	 * action.
	 * 
	 * @param action
	 *            the {@link Action} to execute.
	 * @return the {@link Response} returned.
	 */
	R execute(A action);
}
