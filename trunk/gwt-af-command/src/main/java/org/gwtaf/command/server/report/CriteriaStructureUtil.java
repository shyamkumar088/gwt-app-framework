package org.gwtaf.command.server.report;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.gwtaf.command.server.report.CriteriaRequest.Comparators;
import org.hibernate.Criteria;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

/**
 * This class will parse a Domain Object structure using reflections and store
 * Hibernate Criteria dot paths of all fields within the object.
 * 
 * For example:
 * 
 * <code> 
 *  class DomainObjectA
 *  	String fieldOne;
 *  	Integer fieldTwo;
 *  	DomainObjectB b;
 *  
 *  class DomainObjectB
 *  	Integer id;
 *  	DomainObjectC c;
 *  
 *  class DomainObjectC
 *  	String name;
 * </code>
 * 
 * Will result in a <String, String> HashMap:
 * 
 * <code>
 * --------------------------------------------------------
 * 		[Class].[field]			| 	Criteria dot expression
 * --------------------------------------------------------
 * 		DomainObjectA.fieldOne	|	fieldOne
 * 		DomainObjectA.fieldTwo	|	fieldTwo
 * 		DomainObjectB.id		|	b.id
 * 		DomainObjectC.name		| 	b.c.name
 * --------------------------------------------------------
 * </code>
 * 
 * 
 * Additionally, a second "humanly readable" map can be created. This will map
 * humanly readable description of fields to the [Class].[field] mapping.
 * 
 * For example above:
 * 
 * <code>
 * --------------------------------------------------------
 * 		Description						|	[Class].[field]			
 * --------------------------------------------------------
 * 		Field One from Domain Object A	|	DomainObjectA.fieldOne		
 * 		Field Two from Domain Object A	|	DomainObjectA.fieldTwo	
 * 		Id from Domain Object B			|	DomainObjectB.id		
 * 		Name from Domain Object C		|	DomainObjectC.name		
 * --------------------------------------------------------
 * </code>
 * 
 * The humanly readable description will be used primarily for suggest search.
 * 
 * For the sake of usability, additional rules can be added to manually to the
 * second map to enhance the existing description, for example:
 * 
 * <code>
 * --------------------------------------------------------
 * 		Description						|	[Class].[field]			
 * --------------------------------------------------------
 * 		Full Name						|	DomainObjectC.name	
 * 		Customer's name					|	DomainObjectC.name		
 * -------------------------------------------------------- 
 * </code>
 * 
 * The two description strings above would both link to 'b.c.name' Criteria dot
 * path as per first map.
 * 
 * @author Sergey Vesselov
 */
public class CriteriaStructureUtil {

	private HashMap<String, String> fieldToPath;
	private HashMap<String, String> descriptionToField;

	private ArrayList<String> instantiatedAliases = new ArrayList<String>();

	/**
	 * Parses the structure of the object, constructing a Criteria dot path for
	 * each field as described above.
	 * 
	 * @param obj
	 *            the object to parse.
	 * @param packageRestriction
	 *            restricts the parsing only to classes within the provided
	 *            package. This prevents us parsing classes like "Integer".
	 * @return map of [Class].[field] to a criteria dot path.
	 */
	public HashMap<String, String> parseStructure(Object obj,
			String packageRestriction) {

		// our map
		fieldToPath = new HashMap<String, String>();

		// process the class
		processClass(packageRestriction, obj.getClass(), "");

		// create the human-readable look up names
		createLookUpNames();

		return fieldToPath;
	}

	private void processClass(String packageRestriction, Class c,
			String currentDotPath) {

		String className = c.getSimpleName();

		// walk through all the fields
		Field[] fields = c.getDeclaredFields();
		for (Field f : fields) {

			// field name
			String fieldName = f.getName();
			Class fieldClass = f.getType();

			// we're not interested in 'this'
			if (fieldName.equals("this$0")) {
				continue;
			}

			// if we are dealing with a Something<OurObject> use "OurObject"
			// instead of "Something"
			if (f.getGenericType() instanceof ParameterizedType) {
				ParameterizedType type = (ParameterizedType) f.getGenericType();
				Type[] rawTypes = type.getActualTypeArguments();
				if (rawTypes.length > 0) {
					fieldClass = (Class) rawTypes[0];
				}
			}

			// compute current dot path.
			String newDotPath = createDotPath(currentDotPath, fieldName);

			// scan the field if it's in the right package
			if (fieldClass.getName().startsWith(packageRestriction)) {
				processClass(packageRestriction, fieldClass, newDotPath);
			}
			// if it's a non-scannable field, put it in the map.
			else {
				String fullName = className + "." + fieldName;
				fieldToPath.put(fullName, newDotPath);
			}
		}
	}

	/**
	 * Creates a criteria dot path given an existing path, a class name and
	 * field name.
	 */
	private String createDotPath(String dotPath, String fieldName) {
		if (dotPath.length() == 0) {
			return fieldName;
		}
		return dotPath + "." + fieldName;
	}

	public HashMap<String, String> getMap() {
		return fieldToPath;
	}

	/**
	 * Helper for creating human-readable field descriptors.
	 * 
	 * This will go through Strings like : PatientInfo.lastName
	 * 
	 * and turn them into strings like: Last Name from Patient Info.
	 */
	private void createLookUpNames() {

		descriptionToField = new HashMap<String, String>();

		Iterator<String> it = fieldToPath.keySet().iterator();
		while (it.hasNext()) {
			String classField = it.next();
			String fullDescription = constructDescription(classField);
			descriptionToField.put(fullDescription, classField);
		}
	}

	protected String constructDescription(String classField) {
		// isolate the field
		String[] split = classField.split("\\.");
		String field = split[1];
		String className = split[0];

		// break apart camel notation
		field = field.replaceAll("([A-Z])", " $1");
		className = className.replaceAll("([A-Z])", " $1");

		// bring to upper case the first character.
		String caps = field.toUpperCase();
		field = field.replaceAll("^.", "" + caps.charAt(0));

		// store the full description.
		String fullDescription = field.trim() + " from " + className.trim();
		return fullDescription;
	}

	/**
	 * Fills a criteria based on the given list of filtering requests
	 * 
	 * @param crit
	 *            the criteria to fill.
	 * @param requests
	 *            requests
	 */
	public void fillCriteria(Criteria crit, List<CriteriaRequest> requests) {

		// clear old aliases
		instantiatedAliases.clear();

		// projection list for the data
		ProjectionList projList = Projections.projectionList();

		for (CriteriaRequest request : requests) {
			String path = fieldToPath.get(request.getField());

			// requested unknown field.
			if (path == null) {
				continue;
			}

			parseDotPath(crit, path, request, projList);
		}

		// assign the projection list
		crit.setProjection(projList);
	}

	/**
	 * Helper for stuffing aliases, projections and restrictions into a criteria
	 * for a single request.
	 */
	private void parseDotPath(Criteria crit, String dotPath,
			CriteriaRequest request, ProjectionList projList) {

		String[] tokens = dotPath.split("\\.");
		if (tokens.length == 0) {
			tokens = new String[] { dotPath };
		}

		// handle aliases.
		String previousToken = null;
		for (int i = 0; i < tokens.length - 1; i++) {

			// if it's the first
			String regPath = (previousToken == null) ? tokens[i]
					: previousToken + "." + tokens[i];

			// register the alias
			if (!instantiatedAliases.contains(tokens[i])) {
				instantiatedAliases.add(tokens[i]);
				crit.createAlias(regPath, tokens[i]);
			}

			// store old token
			previousToken = tokens[i];
		}

		// handle projection
		String field = tokens[tokens.length - 1];
		String fieldProjection = (previousToken == null) ? field
				: previousToken + "." + field;

		// project the field
		projList.add(Projections.property(fieldProjection));

		// handle restrictions
		createRestriction(crit, fieldProjection, request);
	}

	/**
	 * Helper for creating restrictions of the fillCriteria() method.
	 */
	private void createRestriction(Criteria crit, String fieldProjection,
			CriteriaRequest request) {
		Comparators comp = request.getComparator();
		String data = request.getData();

		// handle all the humanly readable comparators into restrictions
		if (comp == Comparators.EQUAL || comp == Comparators.ON
				|| comp == Comparators.IS) {
			crit.add(Restrictions.eq(fieldProjection, data));
		} else if (comp == Comparators.NOT_EQUAL || comp == Comparators.NOT_ON
				|| comp == Comparators.IS_NOT) {
			crit.add(Restrictions.ne(fieldProjection, data));
		} else if (comp == Comparators.AFTER || comp == Comparators.MORE_THAN) {
			crit.add(Restrictions.gt(fieldProjection, data));
		} else if (comp == Comparators.MORE_THAN_OR_EQUAL) {
			crit.add(Restrictions.ge(fieldProjection, data));
		} else if (comp == Comparators.BEFORE || comp == Comparators.LESS_THAN) {
			crit.add(Restrictions.lt(fieldProjection, data));
		} else if (comp == Comparators.LESS_THAN_OR_EQUAL) {
			crit.add(Restrictions.le(fieldProjection, data));
		} else if (comp == Comparators.CONTAINS) {
			crit.add(Restrictions.like(fieldProjection, "%" + data + "%"));
		} else if (comp == Comparators.DOES_NOT_CONTAIN) {
			crit.add(Restrictions.not(Restrictions.like(fieldProjection, "%"
					+ data + "%")));
		} else if (comp == Comparators.STARTS_WITH) {
			crit.add(Restrictions.like(fieldProjection, data + "%"));
		} else if (comp == Comparators.ENDS_WITH) {
			crit.add(Restrictions.like(fieldProjection, "%" + data));
		}
	}
}
