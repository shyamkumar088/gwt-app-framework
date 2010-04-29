package org.gwtaf.command.server.report;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Test for the CriteriaDoParser.
 * 
 * @author serge
 */
public class CriteriaDoParserTest {

	@Test
	public void parsingTest() {

		// parse our test data
		CriteriaStructureUtil parser = new CriteriaStructureUtil();
		parser.parseStructure(new ObjectA(), "org.gwtaf.command.");

		// all keys are entered
		HashMap<String, String> map = parser.getMap();
		Assert.assertTrue(map.containsKey("ObjectA.id"));
		Assert.assertTrue(map.containsKey("ObjectA.name"));
		Assert.assertTrue(map.containsKey("ObjectA.expires"));
		Assert.assertFalse(map.containsKey("ObjectA.b"));
		Assert.assertTrue(map.containsKey("ObjectB.name"));
		Assert.assertFalse(map.containsKey("ObjectB.endOfTheWorldCounter"));
		Assert.assertTrue(map.containsKey("ObjectC.endOfTheWorld"));

		// verify that keys are correct
		Assert.assertTrue(map.get("ObjectA.id").equals("id"));
		Assert.assertTrue(map.get("ObjectA.name").equals("name"));
		Assert.assertTrue(map.get("ObjectB.name").equals("b.name"));
		Assert.assertTrue(map.get("ObjectC.endOfTheWorld").equals(
				"b.endOfTheWorldCounter.endOfTheWorld"));
	}

	/**
	 * Class for testing.
	 * 
	 * @author serge
	 */
	private class ObjectA {
		private Integer id;
		private String name;
		private Date expires;
		List<ObjectB> b;
	}

	private class ObjectB {
		private String name;
		private ObjectC endOfTheWorldCounter;
	}

	private class ObjectC {
		private Date endOfTheWorld;
	}
}
