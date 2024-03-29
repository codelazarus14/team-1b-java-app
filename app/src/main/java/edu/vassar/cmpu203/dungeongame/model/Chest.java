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
            "Unused International Studies Degree",
            "Rowing Recruitment Flyer",
            "Orgo signup sheet",
            "Wojack Meme",
            "Garfield Brand Lighter",
            "JoJo's Bizarre Adventure Keyring",
            "Pink Glow in the Dark Timepiece",
            "Half Eaten Vegan Wrap",
            "Surprisingly Unopened Oat Milk Bottle",
            "Communal Ice Cream Scooper",
            "Dead Bedazzled Phone",
    };
    /**
     * This will be used when calculating the score of a player by breaking down the different items
     * in the inventory into a value and multiplying the value by the quantity of the item
     */
    public int[] value = {10,
            1,
            1,
            2,
            4,
            20,
            5,
            7,
            9,
            2,
            10,
            1,
    };

    public Chest() {
        super.accessed = false;
        super.id = "Chest";
        itemQuantity = (int) (Math.random() * loot.length) + 1;
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
        /**
         * The game will tell you a random amount of chests and essentially lie to you
         * all in service of 👏 Hostile Design 👏, of course
         * But this isn't a bug: it's a feature
         */
        if (remainingChests == 0) {
            this.bodyText += "There probably aren't any chests left.";
        }
        else if (remainingChests == 1) {
            this.bodyText += "There might be 1 chest left.";
        }
        else{
            this.bodyText += "There might be " + remainingChests + " chests left.";
        }

        }
}
