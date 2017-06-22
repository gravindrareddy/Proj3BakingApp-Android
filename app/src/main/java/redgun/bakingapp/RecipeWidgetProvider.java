package redgun.bakingapp;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.RemoteViews;

import java.util.ArrayList;
import java.util.Random;

import redgun.bakingapp.models.RecipeIngredients;

import static redgun.bakingapp.R.id.widget_recipe_ingridients_listview;

/**
 * Created by Ravindra on 11-06-2017.
 */


public class RecipeWidgetProvider extends AppWidgetProvider {

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        final int count = appWidgetIds.length;

        for (int i = 0; i < count; i++) {
            int widgetId = appWidgetIds[i];
            String number = String.format("%03d", (new Random().nextInt(900) + 100));

            RemoteViews remoteViews = new RemoteViews(context.getPackageName(),
                    R.layout.widget_recipe_ingredients);
            Intent intent = new Intent(context, RecipesActivity.class);
            intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, widgetId);
            PendingIntent pendingIntent = PendingIntent.getActivity(context,
                    0, intent, 0);

            // ToDo - SettingActivity to let user to mark an activity as Fav
            // ToDo - If no favorite recipe, show Text message to Fav an item
            // ToDo - Fetch ingredients of the Fav recipe & show ingredients list on widget
            // ToDo - onclick of widget, open respective recipe
            updateWidgetListView(context, widgetId);
            remoteViews.setOnClickPendingIntent(R.id.widget_recipe_ingredients_linearlayout, pendingIntent);
            appWidgetManager.updateAppWidget(widgetId, remoteViews);
        }
    }

    private RemoteViews updateWidgetListView(Context context,
                                             int appWidgetId) {

        //which layout to show on widget
        RemoteViews remoteViews = new RemoteViews(
                context.getPackageName(), R.layout.widget_recipe_ingredients);

        //RemoteViews Service needed to provide adapter for ListView
        Intent svcIntent = new Intent(context, WidgetService.class);
        //passing app widget id to that RemoteViews Service
        svcIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        svcIntent.putExtra(context.getResources().getString(R.string.key_recipe_ingredient_parcel), new ArrayList<RecipeIngredients>().add(new RecipeIngredients(2.0f, "KG", "Ingrdient Sample")));
        //ToDo to replace above hardcoded value by reusing the list created at the app start
        // ToDo Create service - Purpose: to populate data on widget whenever the ingredients are available
//        svcIntent.setData(Uri.parse(
//                svcIntent.toUri(Intent.URI_INTENT_SCHEME)));
        remoteViews.setRemoteAdapter(widget_recipe_ingridients_listview, svcIntent);
        //setting an empty view in case of no data
        remoteViews.setEmptyView(widget_recipe_ingridients_listview, R.id.widget_recipe_ingridients_empty_view);
        return remoteViews;
    }
}