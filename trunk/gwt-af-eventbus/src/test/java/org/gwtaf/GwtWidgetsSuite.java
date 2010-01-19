package org.gwtaf;

import org.gwtaf.eventbus.place.GwtTestPlaceManager;

import junit.framework.Test;
import junit.framework.TestCase;

import com.google.gwt.junit.client.GWTTestCase;
import com.google.gwt.junit.tools.GWTTestSuite;

/**
 * A {@link GWTTestSuite} to run all the {@link GWTTestCase}s for the
 * gwt-af-eventbus project.
 * 
 * @author Arthur Kalmenson
 */
public class GwtWidgetsSuite extends TestCase {
	public static Test suite() {
		GWTTestSuite suite = new GWTTestSuite(
				"GWTTestSuite for gwt-af-eventbus.");

		suite.addTestSuite(GwtTestPlaceManager.class);
		return suite;
	}
}
