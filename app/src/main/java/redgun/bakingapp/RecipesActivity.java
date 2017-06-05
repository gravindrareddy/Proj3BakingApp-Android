package redgun.bakingapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import redgun.bakingapp.models.Recipes;
import redgun.bakingapp.utilities.NetworkUtils;
import redgun.bakingapp.utilities.RecyclerTouchListener;

/**
 * Created by Ravindra on 29-05-2017.
 */

public class RecipesActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<Recipes>> {
    private static final int RECIPE_LOADER = 1;
    public static Context mContext;
    static ArrayList<Recipes> recipesList = new ArrayList<Recipes>();
    RecyclerView recipes_recyclerview;
    private RecipesAdapter mRecipeAdapter;

    /**
     * This method returns the entire result from the HTTP response.
     *
     * @param url The URL to fetch the HTTP response from.
     * @return The contents of the HTTP response.
     * @throws IOException Related to network and stream reading
     */
    public static ArrayList<Recipes> getRecipesFromURL(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.setRequestMethod("GET");
        InputStream inputStream = new InputStream() {
            @Override
            public int read() throws IOException {
                return 0;
            }
        };
        ArrayList<Recipes> recipesList = new ArrayList<Recipes>();
        try {
            inputStream = urlConnection.getInputStream();
            Gson gson = new GsonBuilder().create();
            Type collectionType = new TypeToken<ArrayList<Recipes>>() {
            }.getType();
            recipesList = gson.fromJson(new BufferedReader(new InputStreamReader(inputStream)), collectionType);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            urlConnection.disconnect();
        }
        return recipesList;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipes);
        mContext = this;
        recipes_recyclerview = (RecyclerView) findViewById(R.id.recipes_recyclerview);
        if (NetworkUtils.isOnline(mContext)) {
            getSupportLoaderManager().initLoader(RECIPE_LOADER, null, this).forceLoad();
        }
        recipes_recyclerview.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recipes_recyclerview, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Recipes recipe = recipesList.get(position);
                Intent intent = new Intent(getApplicationContext(), RecipeStepsActivity.class);
                intent.putExtra(getResources().getString(R.string.key_recipe_parcel), recipe);
                startActivity(intent);
            }

            @Override
            public void onLongClick(View view, int position) {
            }
        }));

    }

    @Override
    public Loader<ArrayList<Recipes>> onCreateLoader(int id, Bundle args) {
        switch (id) {
            case RECIPE_LOADER:
                return new FetchRecipes(this);
        }
        return new FetchRecipes(this);
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<Recipes>> loader, ArrayList<Recipes> data) {
        recipesList = data;
        mRecipeAdapter = new RecipesAdapter(recipesList);
        //todo - make it dynamic for mobile & tablet
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 2);
        recipes_recyclerview.setLayoutManager(mLayoutManager);
        recipes_recyclerview.setItemAnimator(new DefaultItemAnimator());
        recipes_recyclerview.setAdapter(mRecipeAdapter);
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<Recipes>> loader) {

    }

    private static class FetchRecipes extends AsyncTaskLoader<ArrayList<Recipes>> {
        public FetchRecipes(Context context) {
            super(context);
        }

        @Override
        public ArrayList<Recipes> loadInBackground() {
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
            ArrayList<Recipes> recipesArrayList = new ArrayList<Recipes>();
            try {
                URL url = NetworkUtils.buildRecipeUrl(mContext);
                recipesArrayList = getRecipesFromURL(url);

            } catch (IOException e) {
                Log.e("PlaceholderFragment", "Error ", e);
                e.printStackTrace();
                return null;
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e("PlaceholderFragment12", "Error closing stream", e.fillInStackTrace());
                        e.printStackTrace();
                    }
                }
            }
            return recipesArrayList;

        }

        @Override
        public void deliverResult(ArrayList<Recipes> data) {
            super.deliverResult(data);
        }
    }
}
