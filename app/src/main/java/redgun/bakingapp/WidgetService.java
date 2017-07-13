package redgun.bakingapp;

import android.appwidget.AppWidgetManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.widget.RemoteViewsService;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

import redgun.bakingapp.data.RecipesContract;
import redgun.bakingapp.data.RecipesProvider;
import redgun.bakingapp.models.RecipeIngredients;
import redgun.bakingapp.models.Recipes;

import static redgun.bakingapp.RecipesActivity.recipesList;
import static redgun.bakingapp.SettingsActivity.context;


public class WidgetService extends RemoteViewsService {
/*
* So pretty simple just defining the Adapter of the listview
* here Adapter is WidgetFactory
* */

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return (new WidgetFactory(this.getApplicationContext()));
    }

}