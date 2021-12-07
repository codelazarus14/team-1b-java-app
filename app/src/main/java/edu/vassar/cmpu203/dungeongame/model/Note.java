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

        String[] possibleNotes = {
                "According to all known laws of aviation, there is no way a bee should be able to fly. Its wings are too small to get its fat little body off the ground. The bee, of course, flies anyway because bees don't care what humans think is impossible.",
                "Three days grade school, three days high school. Those were awkward.",
                "So you'll just work us to death? We'll sure try. Wow! That blew my mind!",
                "Need I remind a wise man like you of the fact that hunger is sated with food? Food can be found in shops!",
                "People only care about bloodshed when it's their blood and their shed",
                "I despise trickery, but if we ourselves are to suffer deception, then our hands are no longer tied",
                "It’s so dark down here. The fluorescent lights gave out an hour ago, and now there’s nothing. Thank god I had a lighter in my pocket.",
                "I’ve walked for days down here, and I thought I could get out, but the corridors keep going forever",
                "Is this some cruel joke? Some evil twist of fate?",
                "How big even in this maze. I lost track of how long I’ve been down here, and I don’t think I’ve ever crossed the same path twice. Isn’t this just under Main House?",
                "I think I saw something yesterday. Out of the corner of my eye, motion, but when I turned to look, all was still.",
                "I don’t think I’m the first down here. I come across items, stored neatly away in chests. The items are strange, unlike any that would be placed deliberately. Who put them in the chests? Whose items were they? Did they ever get out?",
                "I looked in a chest and found nothing. That hadn’t happened before. I can’t remember what happened next, but when I came to I was missing some of my things, and my head felt sore. What else is down here with me?",
                "I saw the sun yesterday. Apparently, this labyrinth has windows. But I could not reach there. I tried to shout but no one could hear me. I saw footsteps go by. Can I find my way out?",
                "I found an unmarked door yesterday, but it wouldn’t open. Today I found strange keys. If this is my chance, I must take it.",
                "Whoever put me in this hell is going to pay. I’ll make sure of it.",
                "I’ve been here for 2 days now. Thank god for my watch telling me the time. The lights went out at 6:55 yesterday. Luckily my watch is old, so it faintly glows. The radium would eventually kill me, but I think hunger will get their first.",
                "Day 12 and I’m feeling fine. There are bits of food everywhere. Enough to sustain on, but it always comes at a risk. Those chests are dangerous.",
                
        };

        int randomContents = (int) (Math.random() * possibleNotes.length);
        contents = possibleNotes[randomContents];
//        switch (randomContents) {
//            case 0 : contents = "According to all known laws of aviation, " +
//                    "there is no way a bee should be able to fly. " +
//                    "Its wings are too small to get its fat little body off the ground. " +
//                    "The bee, of course, flies anyway because bees don't care " +
//                    "what humans think is impossible.";
//                break;
//            case 1 : contents = "Three days grade school, three days high school. " +
//                    "Those were awkward.";
//                break;
//            case 2 : contents = "So you'll just work us to death? We'll sure try. " +
//                    "Wow! That blew my mind! ";
//                break;
//            case 3 : contents = "Need I remind a wise man like you of the fact that hunger is sated with food? " +
//                    "Food can be found in shops! ";
//                break;
//            case 4 : contents = "People only care about bloodshed " +
//                    "when it's their blood and their shed";
//                break;
//            case 5 :contents = "I despise trickery, "+
//                    "but if we ourselves are to suffer trickery " +
//                    "then our hands are no longer tied";
//                break;
//            default : contents = "Something broke! You weren't supposed to see this!";
//                break;
//        }

        this.titleText = "You found a note. There might be " + (int) (Math.random()*10) + " notes left";
        this.bodyText = contents;
    }
}
