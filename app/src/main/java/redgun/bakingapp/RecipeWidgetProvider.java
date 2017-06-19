package redgun.bakingapp;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import java.util.Random;

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

            remoteViews.setOnClickPendingIntent(R.id.widget_recipe_ingredients_linearlayout, pendingIntent);
            appWidgetManager.updateAppWidget(widgetId, remoteViews);
        }
    }
}