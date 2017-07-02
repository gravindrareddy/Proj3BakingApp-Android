package redgun.bakingapp;

/**
 * Created by gravi on 22-06-2017.
 */

import android.app.LauncherActivity;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
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
    private Context context = null;
    private int appWidgetId;

    public WidgetFactory(Context context, int appWidgetId, ArrayList<RecipeIngredients> recipeIngredientList) {
        this.context = context;
        this.appWidgetId = appWidgetId;
        this.recipeIngredientList = recipeIngredientList;
        //populateListItem();
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {

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
                context.getPackageName(), R.layout.view_listitem_recipe_ingredient);
        RecipeIngredients recipeIngredient = recipeIngredientList.get(position);
        remoteView.setTextViewText(R.id.recipe_ingredient_textview, recipeIngredient.getRecipeIngredientName() + ": " + recipeIngredient.getRecipeIngredientQuantity() + " " + recipeIngredient.getRecipeIngredientMeasureUnit());
        remoteView.notify();
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
