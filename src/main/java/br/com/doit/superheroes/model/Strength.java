package br.com.doit.superheroes.model;


public enum Strength {
    WEAK("Weak"), 
    STRONG("Strong"), 
    SUPER("Super");

    private String displayName;

    Strength(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
