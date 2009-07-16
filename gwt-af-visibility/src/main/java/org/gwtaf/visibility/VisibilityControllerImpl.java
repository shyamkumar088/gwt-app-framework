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

import java.util.ArrayList;
import java.util.List;

/**
 * Concrete implementation of {@link FlatVisibilityController}. Rules are
 * executed in no particular order.
 * 
 * @author Arthur Kalmenson
 */
public class VisibilityControllerImpl implements VisibilityController {

	/**
	 * The list of {@link VisibilityRule}s.
	 */
	private List<VisibilityRule<?, ?, ?>> rules = new ArrayList<VisibilityRule<?, ?, ?>>();

	public void addRule(VisibilityRule<?, ?, ?> rule) {
		if (rule == null) {
			throw new IllegalArgumentException(getClass().getName()
					+ ": rule cannot be null.");
		}
		rules.add(rule);
	}

	public void executeAll() {
		
		// cycle through the rules and execute them.
		for (VisibilityRule<?, ?, ?> rule : rules) {
			rule.execute();
		}
	}

	public int size() {
		return rules.size();
	}
}
