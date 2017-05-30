package redgun.bakingapp.utilities;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import redgun.bakingapp.models.Recipes;

/**
 * Created by gravi on 09-10-2016.
 * This class was created on-line with DB contract. This class will hold the entries of all API JSON responses
 */
public class APIResponseContract {

    static class MoviesAPIResponseEntry {
        @SerializedName("results")
        public ArrayList<Recipes> recipesArrayList;
    }
}
