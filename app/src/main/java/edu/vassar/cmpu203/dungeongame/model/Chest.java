package edu.vassar.cmpu203.dungeongame.model;

/*
 * if there isn't a note and there isn't a nothing, then there has to be: something
 * A chest with random items.
 */
public class Chest extends Interactable{
    int itemQuantity;
    int itemType;
    String itemName;
    String[] loot = {"Bust of Matthew Vassar",
            "Half-Eaten Mozz Stick",
            "Unused International Studies Degree",
            "Rowing Recruitment Flyer"};

    public Chest() {
        super.accessed = false;
        super.id = "Chest";
        itemQuantity = (int) (Math.random() * 4) + 1;
//        while (itemQuantity == 0) { //this is just a bit of code to make sure there isn't an empty chest as funny as that would be
//            itemQuantity = (int) (Math.random() * 4);
//        }

        itemType = (int) (Math.random() * 4);
        itemName = loot[itemType];
    }
}
