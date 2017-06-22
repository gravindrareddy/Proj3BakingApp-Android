package redgun.bakingapp;

import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.os.IBinder;
import android.widget.RemoteViewsService;

import java.util.ArrayList;

import redgun.bakingapp.models.RecipeIngredients;


public class WidgetService extends RemoteViewsService {
/*
* So pretty simple just defining the Adapter of the listview
* here Adapter is ListProvider
* */

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        int appWidgetId = intent.getIntExtra(
                AppWidgetManager.EXTRA_APPWIDGET_ID,
                AppWidgetManager.INVALID_APPWIDGET_ID);
        ArrayList<RecipeIngredients> recipeIngredientList = intent.getParcelableArrayListExtra(
                getResources().getString(R.string.key_recipe_ingredient_parcel));

        return (new ListProvider(this.getApplicationContext(), appWidgetId, recipeIngredientList));
    }

}