package edu.vassar.cmpu203.dungeongame.model;


/*
 * "Something is there" case: There is a note placed with random text
 * I honestly had no idea what to use here so this is just snippets of the Bee movie Script
 */
public class Note extends Interactable {
    String contents;

    public Note() {
        super.id = "Note";
        super.accessed = false;

        int randomContents = (int) (Math.random() * 6);
        switch (randomContents) {
            case 0 : contents = "According to all known laws\n" + "of aviation,\n" +
                    "there is no way a bee\n" + "should be able to fly.\n" +
                    "Its wings are too small to get\n" + "its fat little body off the ground.\n" +
                    "The bee, of course, flies anyway\n" + "because bees don't care\n" +
                    "what humans think is impossible.";
                break;
            case 1 : contents = "Three days grade school,\n" + "three days high school.\n" +
                    "Those were awkward.";
                break;
            case 2 : contents = "So you'll just work us to death?\n" + "We'll sure try.\n" +
                    "Wow! That blew my mind!\n";
                break;
            case 3 : contents = "Need I remind a wise man like you \n" +
                    "of the fact that hunger is sated with food?\n" +
                    "Food can be found in shops!\n";
                break;
            case 4 : contents = "People only care about bloodshed\n" +
                    "when it's their blood\n and their shed";
                break;
            case 5 :contents = "I despise trickery, \n"+
                    "but if we ourselves are to suffer trickery\n" +
                    "then our hands are no longer tied";
                break;
            default : contents = "Something broke! You weren't supposed to see this!";
                break;
        }

        super.titleText = "The Note reads:";
        super.bodyText = contents;
    }



}
