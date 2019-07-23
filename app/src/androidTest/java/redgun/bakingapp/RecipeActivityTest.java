package redgun.bakingapp;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.loader.content.Loader;
import androidx.test.espresso.NoMatchingViewException;
import androidx.test.espresso.ViewAssertion;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.matcher.BoundedMatcher;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import org.hamcrest.Description;
import org.hamcrest.FeatureMatcher;
import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

import redgun.bakingapp.data.RecipesProvider;
import redgun.bakingapp.models.Recipes;
import redgun.bakingapp.features.recipes.ui.RecipesActivity;
import redgun.bakingapp.utilities.NetworkUtils;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.openLinkWithUri;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertTrue;
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
    public ActivityTestRule<RecipesActivity> activityScenarioRule =
            new ActivityTestRule<RecipesActivity>(RecipesActivity.class);

    @Test
    public void clickRecipesRecyclerView() {

        //Todo: Fetch data via Activity Loader
//        RecipesActivity mRecipesActivity = new RecipesActivity();
//        Context mContext = mRecipesActivity.getBaseContext();
//        if (NetworkUtils.isOnline(mContext)) {
//           // mRecipesActivity.getSupportLoaderManager().initLoader(1, null, ).forceLoad();
//        }
//        Loader<ArrayList<Recipes>> mRecipes = new Loader<ArrayList<Recipes>>(mRecipesActivity.getBaseContext());
//        mRecipes = new RecipesProvider.FetchRecipes(mRecipesActivity.getBaseContext());



        onView(ViewMatchers.withId(R.id.recipes_recyclerview))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(withText("Recipe Introduction")).check(matches(isDisplayed()));

    }


}
