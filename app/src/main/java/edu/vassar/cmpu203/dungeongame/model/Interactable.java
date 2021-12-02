package edu.vassar.cmpu203.dungeongame.model;
import java.io.Serializable;
import java.lang.Math;

/*
 * This is the generalized superclass for all Interactable objects
 * Those being chests, notes, and "nothing"
 */

public class Interactable implements Serializable {
    String id; //This is what I am using as the indicator for what kind of Interactable subclass it is
    boolean accessed;

    public Interactable() {
    }

}
