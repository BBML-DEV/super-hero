package br.com.doit.superheroes.components;

import com.webobjects.appserver.WOContext;
import com.webobjects.eocontrol.EOEditingContext;

import er.extensions.components.ERXComponent;
import er.extensions.eof.ERXEC;

public abstract class AbstractComponent extends ERXComponent {
	
	protected EOEditingContext editingContext;
	
	public AbstractComponent(WOContext context) {
		super(context);
	}
	
	public EOEditingContext editingContext() {
		if(editingContext == null) {
			this.editingContext = ERXEC.newEditingContext();
		}
		
		return editingContext;
	}

	

}
