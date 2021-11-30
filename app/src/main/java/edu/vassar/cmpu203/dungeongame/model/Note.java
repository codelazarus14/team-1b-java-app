package edu.vassar.cmpu203.dungeongame.model;


/*
 * "Something is there" case: There is a note placed with random text
 * I honestly had no idea what to use here so this is just snippets of the Bee movie Script
 */
public class Note extends Interactable {
    String contents;

    public Note() {
        super.id = "Note";

        int randomContents = (int) (Math.random() * 2);
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
        }
    }

}
