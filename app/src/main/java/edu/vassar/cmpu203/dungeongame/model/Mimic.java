package edu.vassar.cmpu203.dungeongame.model;

/**
 * We thought that it was important to have elements of hostile design in our program
 * this is an interactable which poses as a chest but when opened will try and steal an item(s) from you
 */
public class Mimic extends Interactable{

    public Mimic() {
        super.id = "Mimic";
        super.accessed = false;
        super.titleText = "Oh no! It was a Mimic!";
        super.bodyText = "You were lucky not to lose anything";
    }
}
