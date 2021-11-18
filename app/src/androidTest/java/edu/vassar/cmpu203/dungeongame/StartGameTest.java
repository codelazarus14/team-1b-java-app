package edu.vassar.cmpu203.dungeongame;

import androidx.fragment.app.FragmentManager;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.ViewAssertion;
import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;

import org.junit.Test;

import edu.vassar.cmpu203.dungeongame.controller.ControllerActivity;
import edu.vassar.cmpu203.dungeongame.view.MenuFragment;

public class StartGameTest {
    @org.junit.Rule
    public ActivityScenarioRule<ControllerActivity> activityRule =
            new ActivityScenarioRule<>(ControllerActivity.class);

    /**
     * Tests start menu and switching fragments
     */
    @Test
    public void testStartButton() {
        //checks that menu fragment is displayed on launch
        ViewInteraction startText = Espresso.onView(ViewMatchers.withId(R.id.menuTitleText));
        startText.check(ViewAssertions.matches(ViewMatchers.withText(R.string.main_menu_title)));

        // click start
        ViewInteraction startButton = Espresso.onView(ViewMatchers.withId(R.id.startButton));
        startButton.perform(ViewActions.click());

        // checks if upArrow is there to confirm menu is closed and maze fragment is open
        ViewInteraction upArrow = Espresso.onView(ViewMatchers.withId(R.id.upArrow));
        upArrow.check(ViewAssertions.matches(ViewMatchers.withText(R.string.arrow)));

    }
}
