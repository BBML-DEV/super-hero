package br.com.doit.superheroes.model;

import org.apache.log4j.Logger;

import com.webobjects.eocontrol.EOEditingContext;
import com.webobjects.foundation.NSArray;
import com.webobjects.foundation.NSValidation.ValidationException;

import er.extensions.validation.ERXValidationException;


@SuppressWarnings("serial")
public class Ability extends _Ability {
  
	@SuppressWarnings("unused")
	private static Logger log = Logger.getLogger(Ability.class);
  
  
  
  @Override
	public void validateForSave() throws ValidationException {

		super.validateForSave();

		NSArray<Ability> abilities = Ability.fetchAllAbilities(editingContext());

		abilities.mutableClone();
		
			abilities.remove(this);

			for (Ability ability : abilities) {
		
				if (ability.description().equals(this.description())) {
	
					throw new ERXValidationException("Duplicated", this, DESCRIPTION_KEY);
				}
			}
		}
  
  
  
}
