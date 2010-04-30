package org.gwtaf.command.server.report;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.gwtaf.command.server.report.CriteriaRequest.Comparators;
import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Projection;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import static org.mockito.Mockito.*;

/**
 * Test for the CriteriaDoParser.
 * 
 * @author serge
 */
public class CriteriaDoParserTest {

	@Mock
	Criteria crit;

	@BeforeTest
	public void beforeTest() {
		MockitoAnnotations.initMocks(this);
	}

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

	@Test
	public void testFillCriteria() {
		CriteriaStructureUtil parser = new CriteriaStructureUtil();
		parser.parseStructure(new ObjectA(), "org.gwtaf.command.");

		// create some criteria
		List<CriteriaRequest> requests = new ArrayList<CriteriaRequest>();
		requests.add(new CriteriaRequest("ObjectA.id", Comparators.LESS_THAN,
				"34"));
		requests.add(new CriteriaRequest("ObjectB.name", Comparators.CONTAINS,
				"stuff"));
		requests.add(new CriteriaRequest("ObjectC.endOfTheWorld",
				Comparators.ON, "2095-01-01"));

		// fill the criteria.
		parser.fillCriteria(crit, requests);

		// verify all aliases were joined
		verify(crit).createAlias("b", "b");
		verify(crit).createAlias("b.endOfTheWorldCounter",
				"endOfTheWorldCounter");
		verify(crit, times(2)).createAlias(anyString(), anyString());

		// verify we append the projections
		verify(crit).setProjection((Projection) anyObject());

		// verify that the where clauses we added
		verify(crit, times(3)).add((Criterion) anyObject());
	}

	@Test
	public void testRegexReplacement() {
		String field = "thisIsATest";
		String result = field.replaceAll("([A-Z])", " $1");
		Assert.assertEquals("this Is A Test", result);
	}

	@Test
	public void testDescriptionGeneration() {
		CriteriaStructureUtil parser = new CriteriaStructureUtil();
		Assert.assertTrue(parser.constructDescription("PatientInfo.lastName")
				.equals("Last Name from Patient Info"));

		Assert.assertTrue(parser.constructDescription(
				"SomeReallyLongNonsense." + "aFieldWhichIsFarTooLongInName")
				.equals(
						"A Field Which Is Far Too Long In "
								+ "Name from Some Really Long Nonsense"));
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
