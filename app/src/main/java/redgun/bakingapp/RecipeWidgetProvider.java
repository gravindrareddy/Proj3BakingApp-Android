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
            Intent svcIntent = new Intent(context, WidgetService.class);
            RemoteViews remoteViews = new RemoteViews(
                    context.getPackageName(), R.layout.widget_recipe_ingredients);
            svcIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, widgetId);
            ArrayList<RecipeIngredients> arrayList = new ArrayList<>();
            arrayList.add(new RecipeIngredients(2.0f, "KG", "Ingrdient Sample"));
            svcIntent.putExtra(context.getResources().getString(R.string.key_recipe_ingredient_parcel), arrayList);
            // ToDo to replace above hardcoded value by reusing the list created at the app start
            remoteViews.setEmptyView(widget_recipe_ingridients_listview, R.id.widget_recipe_ingridients_empty_view);
//            PendingIntent pendingIntent = PendingIntent.getActivity(context,
//                    0, svcIntent, 0);

            // ToDo - SettingActivity to let user to mark an activity as Fav
            // ToDo - If no favorite recipe, show Text message to Fav an item
            // ToDo - Fetch ingredients of the Fav recipe & show ingredients list on widget
            // ToDo - onclick of widget, open respective recipe

            remoteViews.setRemoteAdapter(R.id.widget_recipe_ingredients_linearlayout, svcIntent);
            appWidgetManager.updateAppWidget(widgetId, remoteViews);
            appWidgetManager.notifyAppWidgetViewDataChanged(widgetId,
                    R.id.widget_recipe_ingridients_listview);
        }
        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }
}