package redgun.bakingapp.features.recipes.ui;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;

import redgun.bakingapp.R;
import redgun.bakingapp.features.recipestepsingridients.ui.RecipeStepsActivity;
import redgun.bakingapp.features.recipes.adapter.RecipesAdapter;
import redgun.bakingapp.data.RecipesContract;
import redgun.bakingapp.data.RecipesProvider;
import redgun.bakingapp.features.settings.SettingsActivity;
import redgun.bakingapp.models.Recipes;
import redgun.bakingapp.utilities.NetworkUtils;
import redgun.bakingapp.utilities.RecyclerTouchListener;

/**
 * Created by Ravindra on 29-05-2017.
 */

public class RecipesActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<Recipes>> {
    private static final int RECIPE_LOADER = 1;
    public static Context mContext;
    public static ArrayList<Recipes> recipesList = new ArrayList<Recipes>();
    RecyclerView recipes_recyclerview;
    private RecipesAdapter mRecipeAdapter;
    static String mTAG;
    boolean mTwoPane;
    ProgressBar progressbar_cyclic;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipes);
        mContext = this;
        mTAG = this.getPackageName() + getLocalClassName();
        recipes_recyclerview = (RecyclerView) findViewById(R.id.recipes_recyclerview);
        progressbar_cyclic = (ProgressBar) findViewById(R.id.progressbar_cyclic);
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

        progressbar_cyclic.setVisibility(View.VISIBLE);
        Loader<ArrayList<Recipes>> recipes = new Loader<ArrayList<Recipes>>(mContext);



        switch (id) {
            case RECIPE_LOADER:
                recipes = new RecipesProvider.FetchRecipes(mContext);
        }
        return recipes;
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<Recipes>> loader, ArrayList<Recipes> data) {
        recipesList = data;
        mRecipeAdapter = new RecipesAdapter(recipesList);

        //this will define the dynamic column count for phone and tablet
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, getResources().getInteger(R.integer.recipes_per_row));
        recipes_recyclerview.setLayoutManager(mLayoutManager);
        recipes_recyclerview.setItemAnimator(new DefaultItemAnimator());
        recipes_recyclerview.setAdapter(mRecipeAdapter);


        Gson gson = new GsonBuilder().create();
        String jsonStr = gson.toJson(recipesList);


        Uri.Builder builder = new Uri.Builder();
        Uri _uri = builder.scheme("content")
                .authority(getResources().getString(R.string.contentprovider_authority))
                .appendPath(getResources().getString(R.string.contentprovider_recipe_entry)).build();

        Cursor _cursor = getContentResolver().query(_uri, null, null, null, null);

        //Persist data locally to access in other screens (Recipe steps and Step Details)
        if (_cursor == null || _cursor.getCount() <= 0) {
            _cursor.moveToFirst();
            ContentValues recipeContentValues = new ContentValues();
            recipeContentValues.put(RecipesContract.RecipeEntry.COLUMN_RECIPES_JSON, jsonStr);
            getContentResolver().insert(RecipesContract.RecipeEntry.CONTENT_URI, recipeContentValues);
            _cursor.close();
        }
        progressbar_cyclic.setVisibility(View.GONE);


    }

    @Override
    public void onLoaderReset(Loader<ArrayList<Recipes>> loader) {

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
//respond to menu item selection
        switch (item.getItemId()) {
            case R.id.settings:
                startActivity(new Intent(this, SettingsActivity.class));
                return true;
            default:
                return true;
        }
    }

}
