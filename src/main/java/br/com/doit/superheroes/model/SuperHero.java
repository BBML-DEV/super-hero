package br.com.doit.superheroes.model;

import org.apache.log4j.Logger;

import com.webobjects.foundation.NSArray;

import er.extensions.validation.ERXValidationException;
import static er.extensions.eof.ERXEOControlUtilities.validateUniquenessOf;




@SuppressWarnings("serial")
public class SuperHero extends _SuperHero {
	
	
	public void addToAbilityRelationship(Ability ability) {
		setAbility(ability);
	}
	
	
	@SuppressWarnings("unused")
	private static Logger log = Logger.getLogger(SuperHero.class);
	
	
	@Override
	public void validateForSave() throws ValidationException {

		super.validateForSave();

		NSArray<SuperHero> superHeroes = SuperHero.fetchAllSuperHeros(editingContext());

		superHeroes.mutableClone();
		
			superHeroes.remove(this);

			for (SuperHero superHero : superHeroes) {
		
				if (superHero.name().equals(this.name())) {
	
					throw new ERXValidationException("Duplicated", this, NAME_KEY);
				}
			}
		}
  


  
}
