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

import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.Set;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.google.gwt.junit.GWTMockUtilities;
import com.google.gwt.user.client.ui.Widget;

/**
 * Test the {@link AbstractVisibilityRule} class.
 * 
 * @author Arthur Kalmenson
 */
public class AbstractVisibilityRuleTest {

	/**
	 * A simple implementation of {@link AbstractVisibilityRule} for testing
	 * purposes.
	 * 
	 * @author Arthur Kalmenson
	 */
	private class AbstractVisibilityRuleImpl extends
			AbstractVisibilityRule<Widget, Widget, String> {

		public AbstractVisibilityRuleImpl(Widget parent) {
			super(parent);
		}

		public void execute() {
			throw new UnsupportedOperationException();
		}
	}

	/**
	 * A mock of Widget.
	 */
	@Mock
	private Widget widgetMock;

	/**
	 * The rule we're testing.
	 */
	private AbstractVisibilityRule<Widget, Widget, String> rule;

	@AfterClass
	public void rearmGwt() {
		GWTMockUtilities.restore();
	}

	@BeforeClass
	public void beforeClass() {
		GWTMockUtilities.disarm();
	}

	@BeforeMethod
	public void initBefore() {
		MockitoAnnotations.initMocks(this);
		rule = new AbstractVisibilityRuleImpl(widgetMock);
	}

	/**
	 * Test creating {@link ShowIfGroupChecked} with a null parent. We expect an
	 * {@link IllegalArgumentException}.
	 */
	@Test(expectedExceptions = IllegalArgumentException.class)
	public void nullParent() {
		rule = new AbstractVisibilityRuleImpl(null);
	}

	/**
	 * Check that the parent is set correctly.
	 */
	@Test
	public void checkParent() {
		Assert.assertEquals(rule.getParentWidget(), widgetMock);
	}

	/**
	 * Add a null child to the rule. We expect an
	 * {@link IllegalArgumentException} to be thrown.
	 */
	@Test(expectedExceptions = IllegalArgumentException.class)
	public void addNullChild() {
		rule.addChildWidget(null);
	}

	/**
	 * Test getting the widgets when the rule was just created. We expect an
	 * empty list.
	 */
	@Test
	public void getEmptyChildWidgets() {
		Assert.assertEquals(rule.getChildWidgets().size(), 0);
	}

	/**
	 * Add some child widgets and get them. Ensure we get them back.
	 */
	@Test
	public void getAndRemoveChildWidgets() {

		// mock some Widgets out and add them.
		Widget widget1 = mock(Widget.class);
		Widget widget2 = mock(Widget.class);
		rule.addChildWidget(widget1);
		rule.addChildWidget(widget2);

		// make sure we get them back.
		Assert.assertEquals(rule.getChildWidgets(), Arrays.asList(widget1,
				widget2));

		// remove one of the widgets and check the result.
		rule.removeChildWidget(widget2);
		Assert.assertEquals(rule.getChildWidgets(), Arrays.asList(widget1));
	}

	/**
	 * Try adding a null trigger. We expect an {@link IllegalArgumentException}.
	 */
	@Test(expectedExceptions = IllegalArgumentException.class)
	public void addNullTrigger() {
		rule.addTrigger(null);
	}

	/**
	 * Add some triggers and make sure we get them back.
	 */
	@Test
	public void addAndGetTriggers() {

		// add the triggers.
		rule.addTrigger("test");
		rule.addTrigger("test1");
		rule.addTrigger("test2");

		// check that they're there.
		Set<String> triggers = rule.getTriggers();
		for (String trigger : Arrays.asList("test", "test1", "test2")) {
			Assert.assertTrue(triggers.contains(trigger));
		}

		// check the size.
		Assert.assertEquals(triggers.size(), 3);
	}

	/**
	 * Test to ensure the visibility for the child widgets is set.
	 */
	@Test
	public void setVisibility() {

		// add some child widgets.
		Widget widget1 = mock(Widget.class);
		Widget widget2 = mock(Widget.class);
		rule.addChildWidget(widget1);
		rule.addChildWidget(widget2);

		// set their visibility and verify.
		rule.setChildrenVisibility(true);

		verify(widget1).setVisible(true);
		verify(widget2).setVisible(true);
	}
}
