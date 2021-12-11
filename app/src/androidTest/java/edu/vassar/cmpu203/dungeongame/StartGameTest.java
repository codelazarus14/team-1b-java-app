package edu.vassar.cmpu203.dungeongame;

import androidx.test.espresso.DataInteraction;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;

import org.hamcrest.Matchers;
import org.junit.Test;

import edu.vassar.cmpu203.dungeongame.controller.ControllerActivity;

public class StartGameTest {
    @org.junit.Rule
    public ActivityScenarioRule<ControllerActivity> activityRule =
            new ActivityScenarioRule<>(ControllerActivity.class);

    /**
     * Tests start menu and switching to maze fragment
     */
    @Test
    public void testStartGame() {
        // checks that menu fragment is displayed on launch
        ViewInteraction startText = Espresso.onView(ViewMatchers.withId(R.id.menuTitleText));
        startText.check(ViewAssertions.matches(ViewMatchers.withText(R.string.main_menu_title)));

        // set a difficulty
        setDifficulty();

        // click start
        ViewInteraction startButton = Espresso.onView(ViewMatchers.withId(R.id.startButton));
        startButton.perform(ViewActions.click());

        checkMazeViewAndInventory();
    }

    private void checkMazeViewAndInventory() {
        // checks if upArrow is there to confirm menu is closed and maze fragment is open
        ViewInteraction upArrow = Espresso.onView(ViewMatchers.withId(R.id.upArrow));
        upArrow.check(ViewAssertions.matches(ViewMatchers.withText(R.string.arrow)));

        //presses inventory button to show dialog
        ViewInteraction invButton = Espresso.onView(ViewMatchers.withId(R.id.inventoryButton));
        invButton.perform(ViewActions.click());

        //check if dialog text is displayed
        ViewInteraction invDialog = Espresso.onView(ViewMatchers.withText(R.string.inventory_default_text));
        invDialog.check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }

    private void setDifficulty() {
        ViewInteraction diffSpinner = Espresso.onView((ViewMatchers.withId(R.id.difficultySpinner)));
        diffSpinner.perform(ViewActions.click());
        DataInteraction diff = Espresso.onData(Matchers.allOf(Matchers.is(Matchers.instanceOf(String.class)),
                Matchers.is("Medium")));
        diff.perform(ViewActions.click());
        ViewInteraction diffSpinnerSelect = Espresso.onView(ViewMatchers.withId(R.id.difficultySpinner));
        diffSpinnerSelect.check(ViewAssertions.matches(ViewMatchers
                                .withSpinnerText(Matchers.containsString("Medium"))));
    }
}
