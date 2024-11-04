package br.com.doit.superheroes.components;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import com.wounit.annotations.Dummy;
import com.wounit.annotations.UnderTest;
import com.wounit.rules.MockEditingContext;
import static com.wounit.matchers.EOAssert.confirm;
import static com.wounit.matchers.EOAssert.canBeSaved;
import static com.wounit.matchers.EOAssert.cannotBeSavedBecause;


import br.com.doit.superheroes.model.Ability;
import br.com.doit.superheroes.model.SuperHero;

public class HeroTest {	
	
	
	@Rule
	public MockEditingContext ec = new MockEditingContext("superheroes");
	
	
	@Dummy
	private SuperHero heroComparison;


	@Dummy
	private Ability ability;
	

	@UnderTest
	private SuperHero superhero;

	@Test
	public void dummy() {
		Assert.assertTrue(true);
	}
	
	
	@Before
	public void setup() {
		heroComparison.setName("SuperMan");
		heroComparison.addToAbilityRelationship(ability);;
	}
	
	
	@Test
	public void cannotSaveHeroIfNamesAreEquals() {
		superhero.setName("SuperMan");
		superhero.addToAbilityRelationship(ability);;
		confirm(superhero, cannotBeSavedBecause("Duplicated"));
	}


	
	@Test
	public void canSaveHeroIfNamesAreUnique() {
		superhero.setName("Batman");
		superhero.addToAbilityRelationship(ability);
		confirm(superhero, canBeSaved());
	}

}
