package br.com.doit.superheroes.components;

import static com.wounit.matchers.EOAssert.canBeSaved;
import static com.wounit.matchers.EOAssert.cannotBeSavedBecause;
import static com.wounit.matchers.EOAssert.confirm;
import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import com.wounit.annotations.Dummy;
import com.wounit.annotations.UnderTest;
import com.wounit.rules.MockEditingContext;

import br.com.doit.superheroes.model.Ability;
import br.com.doit.superheroes.model.Strength;
import br.com.doit.superheroes.model.SuperHero;

public class AbilityTest {
	
	
	@Rule
	public MockEditingContext ec = new MockEditingContext("superheroes");
	
	

	@Dummy
	private Ability abilityComparation;
	
	
	@UnderTest
	private Ability ability;
	
	

	@Test
	public void dummy() {
		Assert.assertTrue(true);
	}
	

	
	@Before
	public void setup() {
		abilityComparation.setDescription("Super Força");
		abilityComparation.setStrength(Strength.SUPER);
	}
	
	
	@Test
	public void canSaveAbilityIfNameAreUnique() {
		ability.setDescription("Super Velocicade");
		ability.setStrength(Strength.WEAK);
		confirm(ability, canBeSaved());

	}
	
	
	@Test
	public void cannotSaveAbilityIfNameAreEquals() {
		ability.setDescription("Super Força");
		ability.setStrength(Strength.SUPER);
		confirm(ability, cannotBeSavedBecause("Duplicated"));

	}

}
