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
package org.gwtaf.widgets;

import com.google.gwt.user.client.ui.Widget;

/**
 * Creates a {@link UiBundle} object containing references to a {@link Widget}
 * and it's respective {@link Widget} label descriptor.
 * 
 * @author Arthur Kalmenson
 * 
 * @param <L>
 *            The type of the label descriptor to use.
 * @param <W>
 *            The type of the widget to use.
 */
public class UiBundle<L extends Widget, W extends Widget> {

	/**
	 * The label widget.
	 */
	private L label;

	/**
	 * The actual widget.
	 */
	private W widget;

	/**
	 * Empty constructor.
	 */
	public UiBundle() {
	}

	/**
	 * Creates a new {@link UiBundle} given a widget and it's respective label
	 * widget.
	 * 
	 * @param label
	 *            the label widget
	 * @param widget
	 *            the actual widget
	 */
	public UiBundle(L label, W widget) {
		this.label = label;
		this.widget = widget;
	}

	public L getLabel() {
		return label;
	}

	public void setLabel(L label) {
		this.label = label;
	}

	public W getWidget() {
		return widget;
	}

	public void setWidget(W widget) {
		this.widget = widget;
	}
}
