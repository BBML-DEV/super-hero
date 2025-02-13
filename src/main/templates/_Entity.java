// $LastChangedRevision: 4733 $ DO NOT EDIT.  Make changes to ${entity.classNameWithOptionalPackage}.java instead.
#if ($entity.superclassPackageName)
package $entity.superclassPackageName;

#end
import er.extensions.eof.*;
import er.extensions.foundation.*;
import com.webobjects.eoaccess.*;
import com.webobjects.eocontrol.*;
import com.webobjects.foundation.*;
import java.math.*;
import java.util.*;
import org.apache.log4j.Logger;

@SuppressWarnings("all")
public abstract class ${entity.prefixClassNameWithoutPackage} extends #if ($entity.partialEntitySet)er.extensions.partials.ERXPartial<${entity.partialEntity.className}>#elseif ($entity.parentSet)${entity.parent.classNameWithDefault}#elseif ($ERXGenericRecord)${ERXGenericRecord}#elseif ($entity.userInfo.parentClass)${entity.userInfo.parentClass}#else ERXGenericRecord#end {
#if ($entity.partialEntitySet)
	public static final String ENTITY_NAME = "$entity.partialEntity.name";
#else
	public static final String ENTITY_NAME = "$entity.name";
#end

// Attribute Keys
#foreach ($attribute in $entity.sortedClassAttributes)
  public static final ERXKey<$attribute.javaClassName> ${attribute.uppercaseUnderscoreName} = new ERXKey<$attribute.javaClassName>("$attribute.name");
#end
  // Relationship Keys
#foreach ($relationship in $entity.sortedClassRelationships)
  public static final ERXKey<$relationship.actualDestination.classNameWithDefault> ${relationship.uppercaseUnderscoreName} = new ERXKey<$relationship.actualDestination.classNameWithDefault>("$relationship.name");
#end

	// Attributes
#foreach ($attribute in $entity.sortedClassAttributes)
	public static final String ${attribute.uppercaseUnderscoreName}_KEY = "$attribute.name";
#end

	// Relationships
#foreach ($relationship in $entity.sortedClassRelationships)
	public static final String ${relationship.uppercaseUnderscoreName}_KEY = "$relationship.name";
#end

  private static Logger logger = Logger.getLogger(${entity.prefixClassNameWithoutPackage}.class);

#if (!$entity.partialEntitySet)
  public $entity.classNameWithOptionalPackage localInstanceIn(EOEditingContext editingContext) {
    $entity.classNameWithOptionalPackage localInstance = ($entity.classNameWithOptionalPackage)EOUtilities.localInstanceOfObject(editingContext, this);
    if (localInstance == null) {
      throw new IllegalStateException("You attempted to localInstance " + this + ", which has not yet committed.");
    }
    return localInstance;
  }

#end
#foreach ($attribute in $entity.sortedClassAttributes)
#if (!$attribute.inherited)
#if ($attribute.userInfo.ERXConstantClassName)
  public $attribute.userInfo.ERXConstantClassName ${attribute.name}() {
    Number value = (Number)storedValueForKey("$attribute.name");
    return ($attribute.userInfo.ERXConstantClassName)value;
  }

  public void set${attribute.capitalizedName}($attribute.userInfo.ERXConstantClassName value) {
    takeStoredValueForKey(value, "$attribute.name");
  }
#else
  public $attribute.javaClassName ${attribute.name}() {
    return ($attribute.javaClassName) storedValueForKey("$attribute.name");
  }

  public void set${attribute.capitalizedName}($attribute.javaClassName value) {
    if (${entity.prefixClassNameWithoutPackage}.logger.isDebugEnabled()) {
    	${entity.prefixClassNameWithoutPackage}.logger.debug( "updating $attribute.name from " + ${attribute.name}() + " to " + value);
    }
    takeStoredValueForKey(value, "$attribute.name");
  }
#end

#end
#end
#foreach ($relationship in $entity.sortedClassToOneRelationships)
#if (!$relationship.inherited)
  public $relationship.actualDestination.classNameWithDefault ${relationship.name}() {
    return ($relationship.actualDestination.classNameWithDefault)storedValueForKey("$relationship.name");
  }

  protected void set${relationship.capitalizedName}Relationship($relationship.actualDestination.classNameWithDefault value) {
    if (${entity.prefixClassNameWithoutPackage}.logger.isDebugEnabled()) {
      ${entity.prefixClassNameWithoutPackage}.logger.debug("updating $relationship.name from " + ${relationship.name}() + " to " + value);
    }
    if (value == null) {
    	$relationship.actualDestination.classNameWithDefault oldValue = ${relationship.name}();
    	if (oldValue != null) {
    		removeObjectFromBothSidesOfRelationshipWithKey(oldValue, "$relationship.name");
      }
    } else {
    	addObjectToBothSidesOfRelationshipWithKey(value, "$relationship.name");
    }
  }

  public void set${relationship.capitalizedName}($relationship.actualDestination.classNameWithDefault value) {
      this.takeStoredValueForKey(value, "$relationship.name");
  }

#end
#end
#foreach ($relationship in $entity.sortedClassToManyRelationships)
#if (!$relationship.inherited)
  public NSArray<${relationship.actualDestination.classNameWithDefault}> ${relationship.name}() {
    return (NSArray<${relationship.actualDestination.classNameWithDefault}>)storedValueForKey("${relationship.name}");
  }

#if (!$relationship.inverseRelationship || $relationship.flattened || !$relationship.inverseRelationship.classProperty)
  public NSArray<${relationship.actualDestination.classNameWithDefault}> ${relationship.name}(EOQualifier qualifier) {
    return ${relationship.name}(qualifier, null);
  }
#else
  public NSArray<${relationship.actualDestination.classNameWithDefault}> ${relationship.name}(EOQualifier qualifier) {
    return ${relationship.name}(qualifier, null, false);
  }

  public NSArray<${relationship.actualDestination.classNameWithDefault}> ${relationship.name}(EOQualifier qualifier, boolean fetch) {
    return ${relationship.name}(qualifier, null, fetch);
  }
#end

  public NSArray<${relationship.actualDestination.classNameWithDefault}> ${relationship.name}(EOQualifier qualifier, NSArray<EOSortOrdering> sortOrderings#if ($relationship.inverseRelationship && !$relationship.flattened && $relationship.inverseRelationship.classProperty), boolean fetch#end) {
    NSArray<${relationship.actualDestination.classNameWithDefault}> results;
#if ($relationship.inverseRelationship && !$relationship.flattened && $relationship.inverseRelationship.classProperty)
    if (fetch) {
      EOQualifier fullQualifier;
#if (${relationship.actualDestination.genericRecord})
      EOQualifier inverseQualifier = new EOKeyValueQualifier("${relationship.inverseRelationship.name}", EOQualifier.QualifierOperatorEqual, this);
#else
      EOQualifier inverseQualifier = new EOKeyValueQualifier(${relationship.actualDestination.classNameWithDefault}.${relationship.inverseRelationship.uppercaseUnderscoreName}_KEY, EOQualifier.QualifierOperatorEqual, this);
#end

      if (qualifier == null) {
        fullQualifier = inverseQualifier;
      }
      else {
        NSMutableArray qualifiers = new NSMutableArray();
        qualifiers.addObject(qualifier);
        qualifiers.addObject(inverseQualifier);
        fullQualifier = new EOAndQualifier(qualifiers);
      }

#if (${relationship.actualDestination.genericRecord})
      EOFetchSpecification fetchSpec = new EOFetchSpecification("${relationship.actualDestination.name}", qualifier, sortOrderings);
      fetchSpec.setIsDeep(true);
      results = (NSArray<${relationship.actualDestination.classNameWithDefault}>)editingContext().objectsWithFetchSpecification(fetchSpec);
#else
      results = ${relationship.actualDestination.classNameWithDefault}.fetch${relationship.actualDestination.pluralName}(editingContext(), fullQualifier, sortOrderings);
#end
    }
    else {
#end
      results = ${relationship.name}();
      if (qualifier != null) {
        results = (NSArray<${relationship.actualDestination.classNameWithDefault}>)EOQualifier.filteredArrayWithQualifier(results, qualifier);
      }
      if (sortOrderings != null) {
        results = (NSArray<${relationship.actualDestination.classNameWithDefault}>)EOSortOrdering.sortedArrayUsingKeyOrderArray(results, sortOrderings);
      }
#if ($relationship.inverseRelationship && !$relationship.flattened && $relationship.inverseRelationship.classProperty)
    }
#end
    return results;
  }

  public void addTo${relationship.capitalizedName}Relationship($relationship.actualDestination.classNameWithDefault object) {
    if (${entity.prefixClassNameWithoutPackage}.logger.isDebugEnabled()) {
      ${entity.prefixClassNameWithoutPackage}.logger.debug("adding " + object + " to ${relationship.name} relationship");
    }
    addObjectToBothSidesOfRelationshipWithKey(object, "${relationship.name}");
  }

  public void removeFrom${relationship.capitalizedName}Relationship($relationship.actualDestination.classNameWithDefault object) {
    if (${entity.prefixClassNameWithoutPackage}.logger.isDebugEnabled()) {
      ${entity.prefixClassNameWithoutPackage}.logger.debug("removing " + object + " from ${relationship.name} relationship");
    }
    removeObjectFromBothSidesOfRelationshipWithKey(object, "${relationship.name}");
  }

  public $relationship.actualDestination.classNameWithDefault create${relationship.capitalizedName}Relationship() {
    EOClassDescription eoClassDesc = EOClassDescription.classDescriptionForEntityName("${relationship.actualDestination.name}");
    EOEnterpriseObject eo = eoClassDesc.createInstanceWithEditingContext(editingContext(), null);
    editingContext().insertObject(eo);
    addObjectToBothSidesOfRelationshipWithKey(eo, "${relationship.name}");
    return ($relationship.actualDestination.classNameWithDefault) eo;
  }

  public void delete${relationship.capitalizedName}Relationship($relationship.actualDestination.classNameWithDefault object) {
    removeObjectFromBothSidesOfRelationshipWithKey(object, "${relationship.name}");
#if (!$relationship.ownsDestination)
    editingContext().deleteObject(object);
#end
  }

  public void deleteAll${relationship.capitalizedName}Relationships() {
    Enumeration objects = ${relationship.name}().immutableClone().objectEnumerator();
    while (objects.hasMoreElements()) {
      delete${relationship.capitalizedName}Relationship(($relationship.actualDestination.classNameWithDefault)objects.nextElement());
    }
  }

#end
#end

#set ($hasMandatoryAttributes = 'false')
#foreach ($attribute in $entity.sortedClassAttributes)
#if (!$attribute.allowsNull)
#set ($restrictingQualifierKey = 'false')
#foreach ($qualifierKey in $entity.restrictingQualifierKeys)
#if ($attribute.name == $qualifierKey)
#set ($restrictingQualifierKey = 'true')
#end
#end
#if ($restrictingQualifierKey == 'false')
#set ($hasMandatoryAttributes = 'true')
#end
#end
#end
#set ($hasMandatoryRelationships = 'false')
#foreach ($relationship in $entity.sortedClassToOneRelationships)
#if ($relationship.mandatory && !($relationship.ownsDestination && $relationship.propagatesPrimaryKey))
#set ($hasMandatoryRelationships = 'true')
#end
#end
#if ($hasMandatoryAttributes == 'true' || $hasMandatoryRelationships == 'true')
  public #if (!$entity.partialEntitySet)static #end${entity.classNameWithOptionalPackage}#if (!$entity.partialEntitySet) create#else init#end${entity.name}(EOEditingContext editingContext) {
    return (${entity.classNameWithOptionalPackage})#if ($entity.partialEntitySet)this;#else EOUtilities.createAndInsertInstance(editingContext, ${entity.prefixClassNameWithoutPackage}.ENTITY_NAME);#end
  }
#end

  public #if (!$entity.partialEntitySet)static #end${entity.classNameWithOptionalPackage}#if (!$entity.partialEntitySet) create#else init#end${entity.name}(EOEditingContext editingContext#foreach ($attribute in $entity.sortedClassAttributes)
#if (!$attribute.allowsNull)
#set ($restrictingQualifierKey = 'false')
#foreach ($qualifierKey in $entity.restrictingQualifierKeys)#if ($attribute.name == $qualifierKey)#set ($restrictingQualifierKey = 'true')#end#end
#if ($restrictingQualifierKey == 'false')
#if ($attribute.userInfo.ERXConstantClassName), ${attribute.userInfo.ERXConstantClassName}#else, ${attribute.javaClassName}#end ${attribute.name}
#end
#end
#end
#foreach ($relationship in $entity.sortedClassToOneRelationships)
#if ($relationship.mandatory && !($relationship.ownsDestination && $relationship.propagatesPrimaryKey)), ${relationship.actualDestination.classNameWithDefault} ${relationship.name}#end
#end
) {
    ${entity.classNameWithOptionalPackage} eo = (${entity.classNameWithOptionalPackage})#if ($entity.partialEntitySet)this;#else EOUtilities.createAndInsertInstance(editingContext, ${entity.prefixClassNameWithoutPackage}.ENTITY_NAME);#end

#foreach ($attribute in $entity.sortedClassAttributes)
#if (!$attribute.allowsNull)
#set ($restrictingQualifierKey = 'false')
#foreach ($qualifierKey in $entity.restrictingQualifierKeys)
#if ($attribute.name == $qualifierKey)
#set ($restrictingQualifierKey = 'true')
#end
#end
#if ($restrictingQualifierKey == 'false')
		eo.set${attribute.capitalizedName}(${attribute.name});
#end
#end
#end
#foreach ($relationship in $entity.sortedClassToOneRelationships)
#if ($relationship.mandatory && !($relationship.ownsDestination && $relationship.propagatesPrimaryKey))
    eo.set${relationship.capitalizedName}Relationship(${relationship.name});
#end
#end
    return eo;
  }
#if (!$entity.partialEntitySet)

  public static NSArray<${entity.classNameWithOptionalPackage}> fetchAll${entity.pluralName}(EOEditingContext editingContext) {
    return ${entity.prefixClassNameWithoutPackage}.fetchAll${entity.pluralName}(editingContext, null);
  }

  public static NSArray<${entity.classNameWithOptionalPackage}> fetchAll${entity.pluralName}(EOEditingContext editingContext, NSArray<EOSortOrdering> sortOrderings) {
    return ${entity.prefixClassNameWithoutPackage}.fetch${entity.pluralName}(editingContext, null, sortOrderings);
  }

  public static NSArray<${entity.classNameWithOptionalPackage}> fetch${entity.pluralName}(EOEditingContext editingContext, EOQualifier qualifier, NSArray<EOSortOrdering> sortOrderings) {
    EOFetchSpecification fetchSpec = new EOFetchSpecification(${entity.prefixClassNameWithoutPackage}.ENTITY_NAME, qualifier, sortOrderings);
    fetchSpec.setIsDeep(true);
    NSArray<${entity.classNameWithOptionalPackage}> eoObjects = (NSArray<${entity.classNameWithOptionalPackage}>)editingContext.objectsWithFetchSpecification(fetchSpec);
    return eoObjects;
  }

  public static NSArray<${entity.classNameWithOptionalPackage}> fetch${entity.pluralName}(EOEditingContext editingContext, String keyName, Object value, NSArray<EOSortOrdering> sortOrderings) {
	return fetch${entity.pluralName}(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value), sortOrderings);
  }

  public static NSArray<${entity.classNameWithOptionalPackage}> fetch${entity.pluralName}(EOEditingContext editingContext, String keyName, Object value, String sortAscKey) {
	NSArray<EOSortOrdering> sortOrderings = new NSArray<EOSortOrdering>(EOSortOrdering.sortOrderingWithKey(sortAscKey, EOSortOrdering.CompareAscending));
	return fetch${entity.pluralName}(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value), sortOrderings);
  }


  public static ${entity.classNameWithOptionalPackage} fetch${entity.name}(EOEditingContext editingContext, String keyName, Object value) {
    return ${entity.prefixClassNameWithoutPackage}.fetch${entity.name}(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static ${entity.classNameWithOptionalPackage} fetch${entity.name}(EOEditingContext editingContext, EOQualifier qualifier) {
    NSArray<${entity.classNameWithOptionalPackage}> eoObjects = ${entity.prefixClassNameWithoutPackage}.fetch${entity.pluralName}(editingContext, qualifier, null);
    ${entity.classNameWithOptionalPackage} eoObject;
    int count = eoObjects.count();
    if (count == 0) {
      eoObject = null;
    }
    else if (count == 1) {
      eoObject = (${entity.classNameWithOptionalPackage})eoObjects.objectAtIndex(0);
    }
    else {
      throw new IllegalStateException("There was more than one ${entity.name} that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static ${entity.classNameWithOptionalPackage} fetchRequired${entity.name}(EOEditingContext editingContext, String keyName, Object value) {
    return ${entity.prefixClassNameWithoutPackage}.fetchRequired${entity.name}(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static ${entity.classNameWithOptionalPackage} fetchRequired${entity.name}(EOEditingContext editingContext, EOQualifier qualifier) {
    ${entity.classNameWithOptionalPackage} eoObject = ${entity.prefixClassNameWithoutPackage}.fetch${entity.name}(editingContext, qualifier);
    if (eoObject == null) {
      throw new NoSuchElementException("There was no ${entity.name} that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static ${entity.classNameWithOptionalPackage} localInstanceIn(EOEditingContext editingContext, ${entity.classNameWithOptionalPackage} eo) {
    ${entity.classNameWithOptionalPackage} localInstance = (eo == null) ? null : (${entity.classNameWithOptionalPackage})EOUtilities.localInstanceOfObject(editingContext, eo);
    if (localInstance == null && eo != null) {
      throw new IllegalStateException("You attempted to localInstance " + eo + ", which has not yet committed.");
    }
    return localInstance;
  }
#end

#foreach ($fetchSpecification in $entity.sortedFetchSpecs)
#if (true || $fetchSpecification.distinctBindings.size() > 0)
  public static NSArray<${entity.className}> fs${fetchSpecification.capitalizedName}(EOEditingContext context, NSDictionary<String, Object> bindings) {
    EOFetchSpecification spec = EOFetchSpecification.fetchSpecificationNamed("${fetchSpecification.name}", "${entity.name}");
    spec = spec.fetchSpecificationWithQualifierBindings(bindings);
    return context.objectsWithFetchSpecification(spec);
  }

#end
  public static NSArray<${entity.className}> fs${fetchSpecification.capitalizedName}(EOEditingContext context#foreach ($binding in $fetchSpecification.distinctBindings),
	${binding.attributePath.childClassName} ${binding.name}Binding#end)
  {
    EOFetchSpecification spec = EOFetchSpecification.fetchSpecificationNamed("${fetchSpecification.name}", "${entity.name}");
#if ($fetchSpecification.distinctBindings.size() > 0)
    NSMutableDictionary bindings = new NSMutableDictionary();
#foreach ($binding in $fetchSpecification.distinctBindings)
    bindings.takeValueForKey(${binding.name}Binding, "${binding.name}");
#end
    spec = spec.fetchSpecificationWithQualifierBindings(bindings);
#end
    return context.objectsWithFetchSpecification(spec);
  }

#end


}
