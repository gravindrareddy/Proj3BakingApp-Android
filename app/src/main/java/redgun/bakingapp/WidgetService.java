package redgun.bakingapp;

import android.appwidget.AppWidgetManager;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.widget.RemoteViewsService;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

import redgun.bakingapp.data.RecipesContract;
import redgun.bakingapp.models.RecipeIngredients;
import redgun.bakingapp.models.Recipes;

import static redgun.bakingapp.RecipesActivity.recipesList;


public class WidgetService extends RemoteViewsService {
/*
* So pretty simple just defining the Adapter of the listview
* here Adapter is WidgetFactory
* */

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        int appWidgetId = intent.getIntExtra(
                AppWidgetManager.EXTRA_APPWIDGET_ID,
                AppWidgetManager.INVALID_APPWIDGET_ID);
        ArrayList<Recipes> recipesList = new ArrayList<>();
        ArrayList<RecipeIngredients> recipeIngredientList = new ArrayList<>();
        //recipeIngredientList.add(new RecipeIngredients(2.0f, "KG", "Ingrdient Sample"));
        // ToDo Trigger Service As soon as something change in Preference Activity
        // ToDo: Get Recipe from the preferences

        Uri.Builder builder = new Uri.Builder();
        Uri _uri = builder.scheme("content")
                .authority(getResources().getString(R.string.contentprovider_authority))
                .appendPath(getResources().getString(R.string.contentprovider_recipe_entry)).build();

        Cursor _cursor = getContentResolver().query(_uri, null, null, null, null);
        if (_cursor != null && _cursor.getCount() > 0) {
            _cursor.moveToFirst();
            String recipesListStr = _cursor.getString(_cursor.getColumnIndex(RecipesContract.RecipeEntry.COLUMN_RECIPES_JSON));
            Gson gson = new GsonBuilder().create();
            Type collectionType = new TypeToken<ArrayList<Recipes>>() {
            }.getType();
            recipesList = gson.fromJson(recipesListStr, collectionType);

            // ToDo: Hardcoded to first recipe
            recipeIngredientList = recipesList.get(0).getRecipeIngredients();
        }

        return (new WidgetFactory(this.getApplicationContext(), appWidgetId, recipeIngredientList));
    }

}