package redgun.bakingapp;

/**
 * Created by gravi on 22-06-2017.
 */

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import java.util.ArrayList;

import redgun.bakingapp.models.RecipeIngredients;

/**
 * If you are familiar with Adapter of ListView,this is the same as adapter
 * with few changes
 */
public class WidgetFactory implements RemoteViewsService.RemoteViewsFactory {
    private ArrayList<RecipeIngredients> recipeIngredientList;
    private Context mContext = null;
    private int appWidgetId;
    private AppWidgetManager mAppManager;

    public WidgetFactory(Context context, int appWidgetId,ArrayList<RecipeIngredients> recipeIngredientList) {
        this.mContext = context;
        this.appWidgetId = appWidgetId;
        mAppManager = AppWidgetManager.getInstance(RecipesActivity.mContext);
        this.recipeIngredientList = recipeIngredientList;
        onDataSetChanged();
        //populateListItem();
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {
//ToDo:  When ever user update his preference on setting, trigger this via Notify Data Set Change
        // get fav recipe from preferences
        // fetch Ingredients of that Recipe
        // Fill the recipeIngredientList
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return recipeIngredientList.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    /*
    *Similar to getView of Adapter where instead of View
    *we return RemoteViews
    *
    */
    @Override
    public RemoteViews getViewAt(int position) {
        final RemoteViews remoteView = new RemoteViews(
                mContext.getPackageName(), R.layout.view_listitem_recipe_ingredient);
        RecipeIngredients recipeIngredient = recipeIngredientList.get(position);
        remoteView.setTextViewText(R.id.recipe_ingredient_textview, recipeIngredient.getRecipeIngredientName() + ": " + recipeIngredient.getRecipeIngredientQuantity() + " " + recipeIngredient.getRecipeIngredientMeasureUnit());


        Bundle extras = new Bundle();
        extras.putInt(RecipeWidgetProvider.EXTRA_ITEM, position);
        Intent fillInIntent = new Intent();
        fillInIntent.putExtras(extras);
        remoteView.setOnClickFillInIntent(R.id.widget_recipe_ingridients_listview, fillInIntent);
        // You can do heaving lifting in here, synchronously. For example, if you need to
        // process an image, fetch something from the network, etc., it is ok to do it here,
        // synchronously. A loading view will show up in lieu of the actual contents in the
        // interim.
        try {
            System.out.println("Loading view " + position);
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return remoteView;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }
}
