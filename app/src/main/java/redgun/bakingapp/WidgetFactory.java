package redgun.bakingapp;

/**
 * Created by gravi on 22-06-2017.
 */

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import java.util.ArrayList;

import redgun.bakingapp.data.RecipesProvider;
import redgun.bakingapp.models.RecipeIngredients;
import redgun.bakingapp.models.Recipes;

/**
 * If you are familiar with Adapter of ListView,this is the same as adapter
 * with few changes
 */
public class WidgetFactory implements RemoteViewsService.RemoteViewsFactory {
    private ArrayList<RecipeIngredients> recipeIngredientList = new ArrayList<>();
    private Context mContext;


    public WidgetFactory(Context context) {
        this.mContext = context;
    }

    @Override
    public void onCreate() {

    }

    //This is called for the first time & every time notifyAppWidgetViewDataChanged is triggered
    @Override
    public void onDataSetChanged() {
        ArrayList<Recipes> recipesList = RecipesProvider.fetchRecipes(mContext);
        SharedPreferences sharedpreferences = mContext.getSharedPreferences(mContext.getResources().getString(R.string.pref_main), Context.MODE_PRIVATE);
        int selectedRecipeIndex = sharedpreferences.getInt(mContext.getResources().getString(R.string.prefered_recipe_index), -1);
        // to get the favorite recipe index from Shared Preferences and pass appropriate Ingredients list to Fav. The default value will be empty ingredients
        if (selectedRecipeIndex >= 0) {
            this.recipeIngredientList = recipesList.get(selectedRecipeIndex).getRecipeIngredients();
        }

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
    *getViewAt of Remote View is similar to onBindViewHolder of normal layout
    *return RemoteViews instead of normal Views
    */
    @Override
    public RemoteViews getViewAt(int position) {
        final RemoteViews remoteView = new RemoteViews(
                mContext.getPackageName(), R.layout.view_listitem_recipe_ingredient);
        RecipeIngredients recipeIngredient = recipeIngredientList.get(position);
        remoteView.setTextViewText(R.id.recipe_ingredient_textview, recipeIngredient.getRecipeIngredientName() + ": " + recipeIngredient.getRecipeIngredientQuantity() + " " + recipeIngredient.getRecipeIngredientMeasureUnit());
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
