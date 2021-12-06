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
            case 0 : contents = "According to all known laws " + "of aviation, " +
                    "there is no way a bee " + "should be able to fly. " +
                    "Its wings are too small to get " + "its fat little body off the ground. " +
                    "The bee, of course, flies anyway " + "because bees don't care " +
                    "what humans think is impossible.";
                break;
            case 1 : contents = "Three days grade school, " + "three days high school. " +
                    "Those were awkward.";
                break;
            case 2 : contents = "So you'll just work us to death? " + "We'll sure try. " +
                    "Wow! That blew my mind! ";
                break;
            case 3 : contents = "Need I remind a wise man like you " +
                    "of the fact that hunger is sated with food? " +
                    "Food can be found in shops! ";
                break;
            case 4 : contents = "People only care about bloodshed " +
                    "when it's their blood and their shed";
                break;
            case 5 :contents = "I despise trickery, "+
                    "but if we ourselves are to suffer trickery " +
                    "then our hands are no longer tied";
                break;
            default : contents = "AAAAAAAAAAAAAAAAAAAAA";
                break;
        }
        this.titleText = "You found a note. There might be " + (int) (Math.random()*10) + " notes left";
        this.bodyText = contents;
    }
}
