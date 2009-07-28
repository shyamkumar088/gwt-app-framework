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
package org.gwtaf.visibility.rule;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.gwtaf.visibility.VisibilityRule;

import com.google.gwt.user.client.ui.Widget;

/**
 * An abstract implementation of {@link VisibilityRule} to take care of
 * repetitive aspects of creating new {@link VisibilityRule}s.
 * 
 * @author Arthur Kalmenson
 * 
 * @param <P>
 *            the parent widget.
 * @param <C>
 *            the child widget.
 * @param <V>
 *            the value we compare the parent widget to.
 */
public abstract class AbstractVisibilityRule<P extends Widget, C extends Widget, V>
		implements VisibilityRule<P, C, V> {

	/**
	 * The parent widget.
	 */
	private P parent;

	/**
	 * The child widgets.
	 */
	private List<C> children = new ArrayList<C>();

	/**
	 * The list of triggers that would show the child widget.
	 */
	private Set<V> triggers = new HashSet<V>();

	/**
	 * Create a new <code>AbstractVisibilityRule</code> with the given parent.
	 * 
	 * @param parent
	 *            the parent widget.
	 */
	public AbstractVisibilityRule(P parent) {
		if (parent == null) {
			throw new IllegalArgumentException("All constructor arguments to "
					+ getClass().getName() + " must be instantiated.");
		}

		this.parent = parent;
	}

	public P getParentWidget() {
		return parent;
	}

	public void addChildWidget(C child) {
		if (child == null) {
			throw new IllegalArgumentException(getClass().getName()
					+ ": Child widget cannot be null.");
		}
		children.add(child);
	}

	public void removeChildWidget(C child) {
		children.remove(child);
	}

	public List<C> getChildWidgets() {
		return children;
	}

	public void addTrigger(V value) {
		if (value == null) {
			throw new IllegalArgumentException(getClass().getName()
					+ ": Trigger cannot be null.");
		}
		triggers.add(value);

	}

	public Set<V> getTriggers() {
		return triggers;
	}

	/**
	 * Set the visibility of the children widgets to <code>visible</code>.
	 * 
	 * @param visible
	 *            the visibility to set for the children.
	 */
	protected void setChildrenVisibility(boolean visible) {
		for (C child : children) {
			child.setVisible(visible);
		}
	}
}
