package edu.vassar.cmpu203.dungeongame.model;

/*
 * if there isn't a note and there isn't a nothing, then there has to be: something
 * A chest with random items.
 */
public class Chest extends Interactable{
    int itemQuantity; //there is a chance that there is more than one item!
    String itemName;

    public Chest() {
        super.id = "Chest";
        itemQuantity = (int) (Math.random() * 4) + 1;
//        while (itemQuantity == 0) { //this is just a bit of code to make sure there isn't an empty chest as funny as that would be
//            itemQuantity = (int) (Math.random() * 4);
//        }

        int itemType = (int) (Math.random() * 4);
        switch (itemType) {
            case 0 : itemName = "Bust of Matthew Vassar";
                break;
            case 1 : itemName = "Half-Eaten Mozz Stick";
                break;
            case 2 : itemName = "Unused International Studies Degree";
                break;
            case 3 : itemName = "Rowing Recruitment Flyer";
                break;
        }

    }

}
