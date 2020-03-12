package edu.ucsb.cs.cs48.schedoptim;

import androidx.test.espresso.action.ViewActions;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.Visibility.VISIBLE;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.allOf;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class NavigationBehaviorTest {

    @Rule
    public ActivityTestRule<MainActivity> activityRule
            = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void navigateCalendar() {
        onView(withId(R.id.navigation_left)).perform(ViewActions.click());
        onView(allOf(withId(R.id.navigation_left),withEffectiveVisibility(VISIBLE))).
                check(matches(isDisplayed()));
    }

    @Test
    public void navigateSettings() {
        onView(withId(R.id.navigation_right)).perform(ViewActions.click());
        onView(allOf(withId(R.id.navigation_right),withEffectiveVisibility(VISIBLE))).
                check(matches(isDisplayed()));
    }
    @Test
    public void navigateMap() {
        onView(withId(R.id.navigation_center)).perform(ViewActions.click());
        onView(allOf(withId(R.id.navigation_center),withEffectiveVisibility(VISIBLE))).
                check(matches(isDisplayed()));
    }
}
