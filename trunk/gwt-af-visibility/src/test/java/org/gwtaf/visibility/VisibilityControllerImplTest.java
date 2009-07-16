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

import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Test {@link VisibilityControllerImpl}.
 * 
 * @author Arthur Kalmenson
 */
public class VisibilityControllerImplTest {

	/**
	 * Mock of the {@link VisibilityRule}.
	 */
	@Mock
	private VisibilityRule<?, ?, ?> ruleMock;

	/**
	 * The {@link FlatVisibilityController} we're exercising.
	 */
	private VisibilityController controller;

	@BeforeMethod
	public void setUpBeforeTests() {
		MockitoAnnotations.initMocks(this);
		controller = new VisibilityControllerImpl();
	}

	/**
	 * Try adding a null rule to the controller. We expecte an
	 * {@link IllegalArgumentException} to be thrown.
	 */
	@Test(expectedExceptions = IllegalArgumentException.class)
	public void addNullRule() {
		controller.addRule(null);
	}

	/**
	 * Execute an empty controller. We don't expect any exceptions to be thrown.
	 */
	@Test
	public void executeEmptyController() {

		// insure it has 0 rules.
		Assert.assertEquals(controller.size(), 0);
		controller.executeAll();

		// insure it still has 0 rules.
		Assert.assertEquals(controller.size(), 0);
	}

	/**
	 * Add a single rule to the controller and execute it. Insure that the
	 * execute method of the rule is called.
	 */
	@Test
	public void singleRuleExecute() {

		// add the rule and execute.
		controller.addRule(ruleMock);
		Assert.assertEquals(controller.size(), 1);
		controller.executeAll();
		Assert.assertEquals(controller.size(), 1);

		// verify that the rule's execute method was called.
		verify(ruleMock).execute();
	}

	/**
	 * Add multiple rules to the controller and execute it. Insure that all the
	 * rules have their execute method called.
	 */
	@Test
	public void multipleRuleExecute() {

		// create a list of VisibilityRules and add them to the controller.
		List<VisibilityRule<?, ?, ?>> rules = createMockRules(5);
		for (VisibilityRule<?, ?, ?> rule : rules) {
			controller.addRule(rule);
		}

		// execute the controller.
		Assert.assertEquals(controller.size(), 5);
		controller.executeAll();
		Assert.assertEquals(controller.size(), 5);

		// insure all the visibility rules were executed.
		for (VisibilityRule<?, ?, ?> rule : rules) {
			verify(rule).execute();
		}
	}

	/**
	 * Creates <code>number</code> mock {@link VisibilityRule}.
	 * 
	 * @param number
	 *            the number of mock {@link VisibilityRule} to create.
	 * @return a list of mock {@link VisibilityRule}.
	 */
	private List<VisibilityRule<?, ?, ?>> createMockRules(int number) {
		List<VisibilityRule<?, ?, ?>> rules = new ArrayList<VisibilityRule<?, ?, ?>>();

		for (int i = 0; i < number; i++) {
			VisibilityRule<?, ?, ?> rule = mock(VisibilityRule.class);
			rules.add(rule);
		}

		return rules;
	}
}
