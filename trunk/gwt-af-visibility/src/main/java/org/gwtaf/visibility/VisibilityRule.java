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

import java.util.List;
import java.util.Set;

/**
 * A <code>VisibilityRule</code> is an interface that lays out the structure for
 * visibility rules. These rules are executed and constitute how visibility
 * logic works. A <code>VisibilityRule</code> consists of 3 parts.
 * 
 * <ul>
 * <li>
 * Parent widget - this is the main widget on whom the visibility of the child
 * depends on.</li>
 * <li>
 * Child widget - this is the widget that appears or disappears depending on the
 * state of the parent widget.</li>
 * <li>
 * Triggers - the optional values of the parent widget that would change the
 * visibility of the child widget.</li>
 * </ul>
 * 
 * @author Arthur Kalmenson
 * 
 * @param <P>
 *            the type of the parent widget.
 * @param <C>
 *            the type of the child widget.
 * @param <V>
 *            the value type of the parent widget.
 */
public interface VisibilityRule<P, C, V> {

	/**
	 * Returns the parent widget.
	 * 
	 * @return the parent widget.
	 */
	public P getParentWidget();

	/**
	 * Add a child widget to this rule.
	 * 
	 * @param child
	 *            the child widget.
	 */
	public void addChildWidget(C child);

	/**
	 * Remove the child widget from the rule.
	 * 
	 * @param child
	 *            the child widget to remove.
	 */
	public void removeChildWidget(C child);

	/**
	 * Returns the child widget.
	 * 
	 * @return the child widget.
	 */
	public List<C> getChildWidgets();

	/**
	 * Adds a trigger.
	 * 
	 * @param value
	 *            a trigger.
	 */
	public void addTrigger(V value);

	/**
	 * Returns the list of triggers.
	 * 
	 * @return the list of triggers.
	 */
	public Set<V> getTriggers();

	/**
	 * Executes this <code>VisibilityRule</code>.
	 */
	public void execute();
}
