/*
 * Copyright 2008. Mount Sinai Hospital, Toronto, Canada.
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
package org.gwtaf.widgets.generic;

import org.eclipse.jdt.internal.compiler.flow.UnconditionalFlowInfo.AssertionFailedException;

import junit.framework.Assert;

import com.google.gwt.junit.client.GWTTestCase;

/**
 * A GWT Test of the {@link RadioButtonGroup}
 * 
 * @author Sergey Vesselov
 */
public class GwtTestRadioButtonGroup extends GWTTestCase {

	/**
	 * Make sure an assertion is thrown on null arguments.
	 */
	public void testNullArgument() {
		try {
			new RadioButtonGroup(null);
		} catch (AssertionError er) {
			return;
		}
		fail();
	}

	/**
	 * Test empty constructor. Make sure a unique name is generated.
	 */
	public void testEmptyCreation() {
		RadioButtonGroup group = new RadioButtonGroup();
		assertNotNull(group.getGroupName());
	}

	/**
	 * Make sure the provided group name is properly set.
	 */
	public void testNormalConstruction() {
		RadioButtonGroup group = new RadioButtonGroup("myGroup");
		assertTrue(group.getGroupName().equals("myGroup"));
	}

	/**
	 * Make sure all the choices are being set with the right group names.
	 */
	public void testSetChoices() {
		RadioButtonGroup group = new RadioButtonGroup("SomeGroup");
		String choices[] = { "one", "two", "three" };
		group.setChoices(choices);

		// verify nothing is selected
		Assert.assertFalse(group.isChecked());

		// verify that all buttons were created with right text and group.
		for (String choice : choices) {

			// make sure it exists
			Assert.assertNotNull(group.getRadio(choice));

			// with right label.
			Assert.assertTrue(group.getRadio(choice).getText().equals(choice));

			// and right group
			Assert.assertTrue(group.getRadio(choice).getName().equals(
					"SomeGroup"));
		}
	}

	/**
	 * Programatically set choices and make sure the right values are returned.
	 */
	public void testGetValue() {

		// create
		RadioButtonGroup group = new RadioButtonGroup("SomeGroup");
		String choices[] = { "one", "two", "three" };
		group.setChoices(choices);

		// verify nothing is selected
		Assert.assertFalse(group.isChecked());

		// set value on one of them.
		group.getRadio("two").setValue(true);

		// verify the right thing is returned
		Assert.assertTrue(group.getValue().equals("two"));
	}

	/**
	 * Make sure the right values are set.
	 */
	public void testSetValues() {

		// create
		RadioButtonGroup group = new RadioButtonGroup("SomeGroup");
		String choices[] = { "one", "two", "three" };
		group.setChoices(choices);

		// verify nothing is selected
		Assert.assertFalse(group.isChecked());

		// set the value
		group.setValue("two");

		// make sure the radio is actually checked
		Assert.assertTrue(group.getRadio("two").getValue());

		// make sure the right thing is returned.
		Assert.assertTrue(group.getValue().equals("two"));
	}

	/**
	 * Make sure we can set values many times.
	 */
	public void testSetValuesMultiple() {
		// create
		RadioButtonGroup group = new RadioButtonGroup("SomeGroup");
		String choices[] = { "one", "two", "three" };
		String choicesTwo[] = { "hello", "hi", "hey", "hmm" };

		// set once
		group.setChoices(choices);

		// set again with different choices.
		group.setChoices(choicesTwo);

		// make sure the old widgets are gone.
		for (String oldChoice : choices) {
			Assert.assertNull(group.getRadio(oldChoice));
		}

		// make sure the new widgets are in place
		for (String newChoices : choicesTwo) {
			Assert.assertNotNull(group.getRadio(newChoices));
		}
	}

	@Override
	public String getModuleName() {
		return "org.gwtaf.GwtAfWidgetsTesting";
	}
}
