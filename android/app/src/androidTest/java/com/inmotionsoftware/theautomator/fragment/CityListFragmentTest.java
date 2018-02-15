package com.inmotionsoftware.theautomator.fragment;

import android.support.test.espresso.ViewAction;
import android.support.test.espresso.action.GeneralClickAction;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.inmotionsoftware.theautomator.MainActivity;
import com.inmotionsoftware.theautomator.R;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isEnabled;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.not;

/**
 * Created by jhunt on 2/14/18.
 */

@RunWith(AndroidJUnit4.class)
@LargeTest
public class CityListFragmentTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(
            MainActivity.class);


    @Test
    public void loginTest() throws InterruptedException {
        onView(withId(R.id.loginButton)).check(matches(not(isEnabled())));

        onView(withId(R.id.emailEditText)).perform(typeText("test@gmail.com"), closeSoftKeyboard());
        onView(withId(R.id.passwordEditText)).perform(typeText("111111"), closeSoftKeyboard());
        onView(withId(R.id.loginButton)).check(matches(isEnabled()));

        onView(withId(R.id.loginButton)).perform(click());

        Thread.sleep(500);

        onView(withId(R.id.filter)).perform(click());

        onView(withText("TX")).perform(click());

        Thread.sleep(500);
    }


    @Test
    public void testMenuItemsStatus() throws Exception {
        //onView(withId(R.id.filter_texas)).check(matches(isEnabled()));
        //onView(withId(R.id.filter_none)).check(matches(not(isEnabled())));
    }

}