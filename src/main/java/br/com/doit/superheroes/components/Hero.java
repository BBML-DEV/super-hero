package br.com.doit.superheroes.components;

import com.webobjects.appserver.WOActionResults;
import com.webobjects.appserver.WOContext;
import com.webobjects.eocontrol.EOQualifier;
import com.webobjects.foundation.NSArray;
import com.webobjects.foundation.NSMutableArray;

import br.com.doit.superheroes.model.Ability;
import br.com.doit.superheroes.model.SuperHero;

public class Hero extends  AbstractComponent {
	
	public NSArray<Ability> abilitiess = Ability.fetchAllAbilities(editingContext());	
	
	public String heroName;
	
	private NSArray<SuperHero> superHeroes; 
	
	public NSArray<SuperHero> heroesFilteredList;
	
	public SuperHero superHero;
	
	public Ability ability;
	
	public SuperHero superHeroesItem;
	
	public String errorMessage;
	
	public boolean isErrorMessage;
	
	private boolean thisHeroNameAlreadyExists;

	private String thisHeroNameAlreadyExistsMessage;

	private String filterHeroName;
	
	
    public Hero(WOContext context) {
        super(context);
    }
    
    
    public WOActionResults goToMainPage() {
    	Main goToMain = pageWithName(Main.class);
        return goToMain; 
    }
    
    
    public String filterHeroName() {
        return filterHeroName;
    }

    public void setFilterHeroName(String filterHeroName) {
        this.filterHeroName = filterHeroName;
        updateHeroesFilteredList();
    }

    public NSArray<SuperHero> getHeroesFilteredList() {
        return heroesFilteredList != null ? heroesFilteredList : superHeroes();
    }
    
    public boolean hasFilteredHeroes() {
    	return !getHeroesFilteredList().isEmpty();
    }
    
    public void updateHeroesFilteredList() {
    	thisHeroNameAlreadyExistsMessage = null;
    	thisHeroNameAlreadyExists = false;
      
    	
        
        EOQualifier qualifier = SuperHero.NAME.contains(filterHeroName);
        heroesFilteredList = SuperHero.fetchSuperHeros(editingContext(), qualifier, null);
    }

    
    public NSArray<SuperHero> superHeroes(){
    	if(superHeroes == null) {
    		superHeroes = SuperHero.fetchAllSuperHeros(editingContext());
    	}
    	
    	return superHeroes;
    }
    
    public SuperHero getSuperHero() {
    	return superHero;
    }
    
    
    public void setSuperHero(SuperHero superHero) {
    	this.superHero = superHero.localInstanceIn(editingContext());
    	heroName = superHero.name();
    	ability = superHero.ability() != null ? superHero.ability().localInstanceIn(editingContext()): null;
    }
    
    
    public WOActionResults saveHero() {
    	
        if (heroName == null || heroName.trim().isEmpty()) {        	
        	errorMessage = "Hero name cannot be empty";
        	isErrorMessage = true;
        	return null;
        }

        if (ability == null) {
        	errorMessage = "Ability must be selected";
        	isErrorMessage = true;
        	return null;
        }
        
        if (thisHeroNameAlreadyExistis() != null) {
            errorMessage = thisHeroNameAlreadyExistsMessage;
            System.out.print(errorMessage);
            return null;
        }

    	
    	if(superHero == null) {
    		superHero = SuperHero.createSuperHero(editingContext(), heroName, ability);
    	}
    	
    	superHero.setName(heroName);
    	superHero.addToAbilityRelationship(ability);
    	superHero. editingContext().saveChanges();
    	errorMessage = null;
    	isErrorMessage = false;
    	
    	superHero = null;
    		
    	return pageWithName(Hero.class);
    }
    
    public String thisHeroNameAlreadyExistis() {
    	
    	
    	 thisHeroNameAlreadyExistsMessage = null;
         thisHeroNameAlreadyExists = false; 
    	
        for (SuperHero hero : superHeroes) {
            if (hero.name().equalsIgnoreCase(heroName) && !hero.equals(superHero)) { 
                thisHeroNameAlreadyExistsMessage = "This hero name already exists";
                thisHeroNameAlreadyExists = true; 
                return thisHeroNameAlreadyExistsMessage;             
            }
        }
        thisHeroNameAlreadyExistsMessage = null;
        thisHeroNameAlreadyExists = false; 
        return null;

    }

    
    public WOActionResults editHero() {
    	Hero superHeroEditComponent = pageWithName(Hero.class);
    	superHeroEditComponent.setSuperHero(superHeroesItem);
    	return superHeroEditComponent;
    	
    } 
    
    public WOActionResults deleteHero() {
    	superHeroesItem.editingContext().deleteObject(superHeroesItem);
    	superHeroesItem.editingContext().saveChanges();
    	
    	return pageWithName(Hero.class);
    }
    
    
    public boolean hasSuperHeroes() {
    	return !superHeroes().isEmpty();    
    }
    
    
    public String getErrorMesage() {
    	return errorMessage;
    }
    
    public boolean getIsErrorMessage() {
    	return  isErrorMessage;
    }
    
	public boolean getThisHeroNameAlreadyExistsError() {
		return thisHeroNameAlreadyExists;
	}
	
	public String getThisHeroNameAlreadyExistsMessage() {
		return thisHeroNameAlreadyExistsMessage;
	}

    
}
