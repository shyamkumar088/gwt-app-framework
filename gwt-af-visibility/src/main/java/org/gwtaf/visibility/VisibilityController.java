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

package org.gwtaf.visibility;

/**
 * The <code>VisibilityController</code> provides a hierarchical structure to
 * the various ways of storing and executing {@link VisibilityRule}s. It
 * provides a common <code>executeAll()</code> interface.
 * 
 * @author Arthur Kalmenson
 */
public interface VisibilityController {

	/**
	 * Executes all the containing {@link VisibilityRule} in this controller.
	 */
	public void executeAll();
	
	/**
	 * Returns the number of {@link VisibilityRule}s in this controller.
	 */
	public int size();

	/**
	 * Adds a {@link VisibilityRule}.
	 * 
	 * @param rule
	 *            a {@link VisibilityRule}.
	 */
	public void addRule(VisibilityRule<?, ?, ?> rule);
}
