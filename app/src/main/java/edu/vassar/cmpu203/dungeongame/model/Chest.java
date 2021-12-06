package edu.vassar.cmpu203.dungeongame.model;

/*
 * if there isn't a note and there isn't a nothing, then there has to be: something
 * A chest with random items.
 */
public class Chest extends Interactable{
    int itemQuantity;
    int itemType;
    String itemName;
    /**
     * I'm hoping that by having all of the items in the constructor be references to loot's aspects
     * rather than set integers or other things, that to expand the possible loot items we will only
     * need to add the strings to the loot array and nothing else
     */
    public String[] loot = {"Bust of Matthew Vassar",
            "Half-Eaten Mozz Stick",
            "Unused I.S. Degree",
            "Rowing Recruitment Flyer",
            "Orgo signup sheet",
            "Wojack Meme"};
    /**
     * This will be used when calculating the score of a player by breaking down the different items
     * in the inventory into a value and multiplying the value by the quantity of the item
     */
    public int[] value = {10,
            1,
            1,
            2,
            4,
            20};

    public Chest() {
        super.accessed = false;
        super.id = "Chest";
        itemQuantity = (int) (Math.random() * loot.length) + 1;
        super.bodyText = "Quantity " + Integer.toString(itemQuantity);
        itemType = (int) (Math.random() * loot.length);
        itemName = loot[itemType];

        this.titleText = "You found a chest containing " + itemQuantity + " item";
        if (itemQuantity != 1) this.titleText += "s";
        this.titleText += ".";

        this.bodyText = "You found ";
        this.bodyText += itemQuantity == 1 ? "a" : itemQuantity;
        this.bodyText += " " + itemName;
        if (itemQuantity != 1) this.bodyText += "s";
        this.bodyText += ".\n\n";
        int remainingChests = (int) (Math.random() * 10);
        switch (remainingChests) {
            case 0:
                this.bodyText += "There probably aren't any chests left.";
                break;
            case 1:
                this.bodyText += "There might be 1 chest left.";
                break;
            default:
                this.bodyText += "There might be " + (int) (Math.random() * 10) + " chests left.";
                break;
        }
        }
}
