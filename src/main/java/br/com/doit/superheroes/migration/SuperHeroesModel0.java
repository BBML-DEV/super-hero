package br.com.doit.superheroes.migration;

import com.webobjects.eocontrol.EOEditingContext;
import com.webobjects.foundation.NSArray;

import er.extensions.jdbc.ERXSQLHelper.ColumnIndex;
import er.extensions.migration.ERXMigrationDatabase;
import er.extensions.migration.ERXMigrationIndex;
import er.extensions.migration.ERXMigrationTable;
import er.extensions.migration.ERXModelVersion;

public class SuperHeroesModel0 extends ERXMigrationDatabase.Migration {
	@Override
	public void downgrade(EOEditingContext editingContext, ERXMigrationDatabase database) throws Throwable {
		// DO NOTHING
	}

	@Override
	public void upgrade(EOEditingContext editingContext, ERXMigrationDatabase database) throws Throwable {
		ERXMigrationTable abilityTable = database.newTableNamed("ability");
		abilityTable.newLargeStringColumn("description", NOT_NULL);
		abilityTable.newIntegerColumn("id", NOT_NULL);
		abilityTable.newStringColumn("strength", 50, NOT_NULL);
		abilityTable.create();
	 	abilityTable.setPrimaryKey("id");

		ERXMigrationTable superHeroTable = database.newTableNamed("super_hero");
		superHeroTable.newIntegerColumn("ability_id", NOT_NULL);
		superHeroTable.newIntegerColumn("id", NOT_NULL);
		superHeroTable.newLargeStringColumn("name", NOT_NULL);
		superHeroTable.create();
	 	superHeroTable.setPrimaryKey("id");

		superHeroTable.addForeignKey("ability_id", "ability", "id");
	}
}