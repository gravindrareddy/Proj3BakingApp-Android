package redgun.bakingapp;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.RemoteViews;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

import redgun.bakingapp.models.RecipeIngredients;

import static redgun.bakingapp.R.id.widget_recipe_ingridients_listview;

/**
 * Created by Ravindra on 11-06-2017.
 */


public class RecipeWidgetProvider extends AppWidgetProvider {
    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        super.onDeleted(context, appWidgetIds);
    }

    @Override
    public void onEnabled(Context context) {
        super.onEnabled(context);
    }

    @Override
    public void onDisabled(Context context) {
        super.onDisabled(context);
    }


    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        updateList(context, appWidgetManager, appWidgetIds);
        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }

    public static void updateList(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        Intent svcIntent;
        final int count = appWidgetIds.length;
        for (int i = 0; i < count; i++) {
            svcIntent = new Intent(context, WidgetService.class);
            int widgetId = appWidgetIds[i];
            svcIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, widgetId);
            RemoteViews remoteViews = new RemoteViews(
                    context.getPackageName(), R.layout.widget_recipe_ingredients);
            svcIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetIds[i]);
            svcIntent.setData(Uri.parse(svcIntent.toUri(Intent.URI_INTENT_SCHEME)));
            remoteViews.setRemoteAdapter(R.id.widget_recipe_ingridients_listview, svcIntent);
            remoteViews.setEmptyView(R.id.widget_recipe_ingridients_listview, R.id.widget_recipe_ingridients_empty_view);
//            PendingIntent toastPendingIntent = PendingIntent.getBroadcast(context, 0, svcIntent,
//                    PendingIntent.FLAG_UPDATE_CURRENT);
//            remoteViews.setPendingIntentTemplate(R.id.widget_recipe_ingridients_listview, toastPendingIntent);
            appWidgetManager.updateAppWidget(widgetId, remoteViews);
//            context.startService(svcIntent);
        }
    }
}