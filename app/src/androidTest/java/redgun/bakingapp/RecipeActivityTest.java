package redgun.bakingapp;

import android.support.test.espresso.matcher.BoundedMatcher;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.hamcrest.Description;
import org.hamcrest.FeatureMatcher;
import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import redgun.bakingapp.models.Recipes;
import redgun.bakingapp.features.recipes.ui.RecipesActivity;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.openLinkWithUri;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.IsEqual.equalTo;

/**
 * Created by gravi on 16-07-2017.
 */

@RunWith(AndroidJUnit4.class)
public class RecipeActivityTest {
    @Rule
    public ActivityTestRule<RecipesActivity> mRecipeActivityTestRule = new ActivityTestRule<>(RecipesActivity.class);

    @Test
    public void clickRecipesRecyclerView() {

        //onView(withId(R.id.recipes_recyclerview)).perform(click());

        onData( allOf( instanceOf( Recipes.class), myCustomObjectShouldHaveString( "recipeName: Brownies")))
                .perform(click());

        // testing the result ... as in the longlist example
        onView(withId(R.id.recipe_name_textview)).check(matches(withText("recipeName: Brownies")));
        // ToDo: OnClick of item on Recipes List
        // ToDo: Persist clicked item position
        // ToDo: On next view, cross check whether the intended content exists or not
    }


    public static Matcher<Object> myCustomObjectShouldHaveString(String expectedTest) {
        return myCustomObjectShouldHaveString( equalTo( expectedTest));
    }
    private static Matcher<Object> myCustomObjectShouldHaveString(final Matcher<String> expectedObject) {
        return new BoundedMatcher<Object, Recipes>( Recipes.class) {
            @Override
            public boolean matchesSafely(final Recipes actualObject) {
                // next line is important ... requiring a String having an "equals" method
                if( expectedObject.matches( actualObject.getRecipeName()) ) {
                    return true;
                } else {
                    return false;
                }
            }
            @Override
            public void describeTo(final Description description) {
                // could be improved, of course
                description.appendText("recipe name should return ");
            }
        };
    }



    private static FeatureMatcher<Recipes, String> withRecipeName(final String recipeName) {
        return new FeatureMatcher<Recipes, String>(equalTo(recipeName), "with Recipe Name", "recipeName") {
            @Override
            protected String featureValueOf(Recipes recipe) {
                return recipe.getRecipeName();
            }
        };
    }
}
