package org.gwtaf.command.server.report;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;

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

		return fieldToPath;
	}

	private void processClass(String packageRestriction, Class c, String currentDotPath) {

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

			// if we are dealing with a Something<OurObject> use "OurObject" instead
			// of "Something"
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
}
