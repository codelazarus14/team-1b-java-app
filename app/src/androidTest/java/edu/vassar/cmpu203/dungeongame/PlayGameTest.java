package edu.vassar.cmpu203.dungeongame;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.ViewAssertion;
import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;

import org.junit.Test;

import edu.vassar.cmpu203.dungeongame.controller.ControllerActivity;

public class PlayGameTest {
    @org.junit.Rule
    ActivityScenarioRule<ControllerActivity> activityRule =
            new ActivityScenarioRule<ControllerActivity>(ControllerActivity.class);

    @Test
    public void testPlayGame() {
        ViewInteraction mazeView = Espresso.onView(ViewMatchers.withId(R.id.mazeView));
        mazeView.check(ViewAssertions.matches(ViewMatchers.withText(R.string.maze_view)));


    }
}