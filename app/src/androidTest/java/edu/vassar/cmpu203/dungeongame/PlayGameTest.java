package edu.vassar.cmpu203.dungeongame;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;

import org.junit.Test;

import edu.vassar.cmpu203.dungeongame.controller.ControllerActivity;

public class PlayGameTest {
    @org.junit.Rule
    public ActivityScenarioRule<ControllerActivity> activityRule =
            new ActivityScenarioRule<>(ControllerActivity.class);

    @Test
    public void testPlayGame() {
        // press start and switch to maze view
        ViewInteraction startButton = Espresso.onView(ViewMatchers.withId(R.id.startButton)).perform(ViewActions.click());

        // press a movement button and check if toString was updated with new String instance?
    }
}