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
package org.gwtaf.widgets.expanding;

import org.gwtaf.widgets.expanding.gin.ExpandableTableGinModule;

import com.google.gwt.inject.client.GinModules;
import com.google.gwt.inject.client.Ginjector;
import com.google.gwt.junit.client.GWTTestCase;

/**
 * Run an integration test on the {@link ExpandableTablePresenter} to ensure
 * that all the pieces can talk to each other correctly.
 * 
 * @author Arthur Kalmenson
 */
public class GwtTestExpandableTablePresenter extends GWTTestCase {

	/**
	 * The test {@link Ginjector} we'll use to test injections for the
	 * {@link ExpandableTablePresenter} implementation.
	 * 
	 * @author Arthur Kalmenson
	 */
	@GinModules(ExpandableTableGinModule.class)
	public static interface ExpandableTableGwtTestGinjector extends Ginjector {
		
	}
	
	public void testExample() {
		assertTrue(true);
	}

	@Override
	public String getModuleName() {
		return "org.gwtaf.GwtAfWidgetsTesting";
	}
}
