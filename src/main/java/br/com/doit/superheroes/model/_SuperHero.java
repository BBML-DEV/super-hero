// $LastChangedRevision: 4733 $ DO NOT EDIT.  Make changes to SuperHero.java instead.
package br.com.doit.superheroes.model;

import er.extensions.eof.*;
import er.extensions.foundation.*;
import com.webobjects.eoaccess.*;
import com.webobjects.eocontrol.*;
import com.webobjects.foundation.*;
import java.math.*;
import java.util.*;
import org.apache.log4j.Logger;

@SuppressWarnings("all")
public abstract class _SuperHero extends  ERXGenericRecord {
	public static final String ENTITY_NAME = "SuperHero";

// Attribute Keys
  public static final ERXKey<String> NAME = new ERXKey<String>("name");
  // Relationship Keys
  public static final ERXKey<br.com.doit.superheroes.model.Ability> ABILITY = new ERXKey<br.com.doit.superheroes.model.Ability>("ability");

	// Attributes
	public static final String NAME_KEY = "name";

	// Relationships
	public static final String ABILITY_KEY = "ability";

  private static Logger logger = Logger.getLogger(_SuperHero.class);

  public SuperHero localInstanceIn(EOEditingContext editingContext) {
    SuperHero localInstance = (SuperHero)EOUtilities.localInstanceOfObject(editingContext, this);
    if (localInstance == null) {
      throw new IllegalStateException("You attempted to localInstance " + this + ", which has not yet committed.");
    }
    return localInstance;
  }

  public String name() {
    return (String) storedValueForKey("name");
  }

  public void setName(String value) {
    if (_SuperHero.logger.isDebugEnabled()) {
    	_SuperHero.logger.debug( "updating name from " + name() + " to " + value);
    }
    takeStoredValueForKey(value, "name");
  }

  public br.com.doit.superheroes.model.Ability ability() {
    return (br.com.doit.superheroes.model.Ability)storedValueForKey("ability");
  }

  protected void setAbilityRelationship(br.com.doit.superheroes.model.Ability value) {
    if (_SuperHero.logger.isDebugEnabled()) {
      _SuperHero.logger.debug("updating ability from " + ability() + " to " + value);
    }
    if (value == null) {
    	br.com.doit.superheroes.model.Ability oldValue = ability();
    	if (oldValue != null) {
    		removeObjectFromBothSidesOfRelationshipWithKey(oldValue, "ability");
      }
    } else {
    	addObjectToBothSidesOfRelationshipWithKey(value, "ability");
    }
  }

  public void setAbility(br.com.doit.superheroes.model.Ability value) {
      this.takeStoredValueForKey(value, "ability");
  }


  public static SuperHero createSuperHero(EOEditingContext editingContext) {
    return (SuperHero) EOUtilities.createAndInsertInstance(editingContext, _SuperHero.ENTITY_NAME);  }

  public static SuperHero createSuperHero(EOEditingContext editingContext, String name
, br.com.doit.superheroes.model.Ability ability) {
    SuperHero eo = (SuperHero) EOUtilities.createAndInsertInstance(editingContext, _SuperHero.ENTITY_NAME);
		eo.setName(name);
    eo.setAbilityRelationship(ability);
    return eo;
  }

  public static NSArray<SuperHero> fetchAllSuperHeros(EOEditingContext editingContext) {
    return _SuperHero.fetchAllSuperHeros(editingContext, null);
  }

  public static NSArray<SuperHero> fetchAllSuperHeros(EOEditingContext editingContext, NSArray<EOSortOrdering> sortOrderings) {
    return _SuperHero.fetchSuperHeros(editingContext, null, sortOrderings);
  }

  public static NSArray<SuperHero> fetchSuperHeros(EOEditingContext editingContext, EOQualifier qualifier, NSArray<EOSortOrdering> sortOrderings) {
    EOFetchSpecification fetchSpec = new EOFetchSpecification(_SuperHero.ENTITY_NAME, qualifier, sortOrderings);
    fetchSpec.setIsDeep(true);
    NSArray<SuperHero> eoObjects = (NSArray<SuperHero>)editingContext.objectsWithFetchSpecification(fetchSpec);
    return eoObjects;
  }

  public static NSArray<SuperHero> fetchSuperHeros(EOEditingContext editingContext, String keyName, Object value, NSArray<EOSortOrdering> sortOrderings) {
	return fetchSuperHeros(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value), sortOrderings);
  }

  public static NSArray<SuperHero> fetchSuperHeros(EOEditingContext editingContext, String keyName, Object value, String sortAscKey) {
	NSArray<EOSortOrdering> sortOrderings = new NSArray<EOSortOrdering>(EOSortOrdering.sortOrderingWithKey(sortAscKey, EOSortOrdering.CompareAscending));
	return fetchSuperHeros(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value), sortOrderings);
  }


  public static SuperHero fetchSuperHero(EOEditingContext editingContext, String keyName, Object value) {
    return _SuperHero.fetchSuperHero(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static SuperHero fetchSuperHero(EOEditingContext editingContext, EOQualifier qualifier) {
    NSArray<SuperHero> eoObjects = _SuperHero.fetchSuperHeros(editingContext, qualifier, null);
    SuperHero eoObject;
    int count = eoObjects.count();
    if (count == 0) {
      eoObject = null;
    }
    else if (count == 1) {
      eoObject = (SuperHero)eoObjects.objectAtIndex(0);
    }
    else {
      throw new IllegalStateException("There was more than one SuperHero that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static SuperHero fetchRequiredSuperHero(EOEditingContext editingContext, String keyName, Object value) {
    return _SuperHero.fetchRequiredSuperHero(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static SuperHero fetchRequiredSuperHero(EOEditingContext editingContext, EOQualifier qualifier) {
    SuperHero eoObject = _SuperHero.fetchSuperHero(editingContext, qualifier);
    if (eoObject == null) {
      throw new NoSuchElementException("There was no SuperHero that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static SuperHero localInstanceIn(EOEditingContext editingContext, SuperHero eo) {
    SuperHero localInstance = (eo == null) ? null : (SuperHero)EOUtilities.localInstanceOfObject(editingContext, eo);
    if (localInstance == null && eo != null) {
      throw new IllegalStateException("You attempted to localInstance " + eo + ", which has not yet committed.");
    }
    return localInstance;
  }



}
