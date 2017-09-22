package redgun.bakingapp.features.recipes.ui;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import redgun.bakingapp.R;
import redgun.bakingapp.features.recipesteps.ui.RecipeStepsActivity;
import redgun.bakingapp.features.recipes.adapter.RecipesAdapter;
import redgun.bakingapp.features.settings.SettingsActivity;
import redgun.bakingapp.data.RecipesContract;
import redgun.bakingapp.data.RecipesProvider;
import redgun.bakingapp.models.Recipes;
import redgun.bakingapp.utilities.NetworkUtils;
import redgun.bakingapp.utilities.RecyclerTouchListener;

import static redgun.bakingapp.data.RecipesProvider.getRecipesFromURL;

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


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipes);
        mContext = this;
        mTAG = this.getPackageName() + getLocalClassName();
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
                return new RecipesProvider.FetchRecipes(this);
        }
        return new RecipesProvider.FetchRecipes(this);
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<Recipes>> loader, ArrayList<Recipes> data) {
        recipesList = data;
        mRecipeAdapter = new RecipesAdapter(recipesList);
        //this will define the dynamic column count for phone and tablet
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, getResources().getInteger(R.integer.recipes_recycler_count));
        recipes_recyclerview.setLayoutManager(mLayoutManager);
        recipes_recyclerview.setItemAnimator(new DefaultItemAnimator());
        recipes_recyclerview.setAdapter(mRecipeAdapter);
        JSONObject json = new JSONObject();
        try {
            json.put("uniqueArrays", recipesList);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        String arrayList = json.toString();


        Gson gson = new GsonBuilder().create();
        String jsonStr = gson.toJson(recipesList);


        Uri.Builder builder = new Uri.Builder();
        Uri _uri = builder.scheme("content")
                .authority(getResources().getString(R.string.contentprovider_authority))
                .appendPath(getResources().getString(R.string.contentprovider_recipe_entry)).build();

        Cursor _cursor = getContentResolver().query(_uri, null, null, null, null);
        if (_cursor != null && _cursor.getCount() > 0) {
            _cursor.moveToFirst();
            ContentValues recipeContentValues = new ContentValues();
            recipeContentValues.put(RecipesContract.RecipeEntry.COLUMN_RECIPES_JSON, jsonStr);
            getContentResolver().update(RecipesContract.RecipeEntry.CONTENT_URI, recipeContentValues, RecipesContract.RecipeEntry._ID + "=?", new String[]{_cursor.getInt(_cursor.getColumnIndex(RecipesContract.RecipeEntry._ID)) + ""});
            _cursor.close();
        } else {
            //ToDo fetch data.. if no data exists in DB insert, else, update
            ContentValues recipeContentValues = new ContentValues();
            recipeContentValues.put(RecipesContract.RecipeEntry.COLUMN_RECIPES_JSON, jsonStr);
            getContentResolver().insert(RecipesContract.RecipeEntry.CONTENT_URI, recipeContentValues);
        }

    }

    @Override
    public void onLoaderReset(Loader<ArrayList<Recipes>> loader) {

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);//Menu Resource, Menu
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.settings:
                Intent intent = new Intent(this, SettingsActivity.class);
                intent.putParcelableArrayListExtra(getResources().getString(R.string.key_recipes_list_parcel), recipesList);
                startActivity(intent);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }


}
