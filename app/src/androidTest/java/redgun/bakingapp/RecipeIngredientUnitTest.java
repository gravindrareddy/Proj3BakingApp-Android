package redgun.bakingapp;

import android.os.Parcel;
import android.support.test.runner.AndroidJUnit4;
import android.util.Pair;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import redgun.bakingapp.models.RecipeIngredients;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

@RunWith(AndroidJUnit4.class)
public class RecipeIngredientUnitTest {

    public static final String TEST_QUANITY = "1.0";
    public static final String TEST_MEASURE = "kg";
    public static final String TEST_INGRIDIENT = "Rice";
    private RecipeIngredients mRecipeIngredients;

    @Before
    public void createRecipeIngredients() {
        mRecipeIngredients = new RecipeIngredients(TEST_QUANITY, TEST_MEASURE, TEST_INGRIDIENT);
    }

    @Test
    public void RecipeIngredients_ParcelableWriteRead() {

        // Write the data.
        Parcel parcel = Parcel.obtain();
        mRecipeIngredients.writeToParcel(parcel, mRecipeIngredients.describeContents());

        // After you're done with writing, you need to reset the parcel for reading.
        parcel.setDataPosition(0);

        // Read the data.
        RecipeIngredients createdFromParcel = new RecipeIngredients(parcel);

        // Verify that the received data is correct.
        //Assert.assertEquals(createdFromParcel.getRecipeIngredientQuantity(), TEST_QUANITY, 1.0f);
        assertThat(createdFromParcel.getRecipeIngredientMeasureUnit(), is(TEST_MEASURE));
        assertThat(createdFromParcel.getRecipeIngredientName(), is(TEST_INGRIDIENT));
    }
}