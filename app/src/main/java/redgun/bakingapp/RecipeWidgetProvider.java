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



    public static final String TOAST_ACTION = "com.example.android.stackwidget.TOAST_ACTION";
    public static final String EXTRA_ITEM = "com.example.android.stackwidget.EXTRA_ITEM";
    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        super.onDeleted(context, appWidgetIds);
    }
    @Override
    public void onDisabled(Context context) {
        super.onDisabled(context);
    }
    @Override
    public void onEnabled(Context context) {
        super.onEnabled(context);
    }


    @Override
    public void onReceive(Context context, Intent intent) {
        AppWidgetManager mgr = AppWidgetManager.getInstance(context);
        if (intent.getAction().equals(TOAST_ACTION)) {
            int appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                    AppWidgetManager.INVALID_APPWIDGET_ID);
            int viewIndex = intent.getIntExtra(EXTRA_ITEM, 0);
            Toast.makeText(context, "Touched view " + viewIndex, Toast.LENGTH_SHORT).show();
        }
        super.onReceive(context, intent);
    }






    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        final int count = appWidgetIds.length;




        for (int i = 0; i < count; i++) {
            int widgetId = appWidgetIds[i];

            ArrayList<RecipeIngredients> arrayList = new ArrayList<>();
            arrayList.add(new RecipeIngredients(2.0f, "KG", "Ingrdient Sample"));

            Intent svcIntent = new Intent(context, WidgetService.class);
            svcIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, widgetId);
            svcIntent.setData(Uri.parse(svcIntent.toUri(Intent.URI_INTENT_SCHEME)));

            // ToDo to replace above hardcoded value by reusing the list created at the app start
            // ToDo - SettingActivity to let user to mark an activity as Fav
            // ToDo - If no favorite recipe, show Text message to Fav an item
            // ToDo - Fetch ingredients of the Fav recipe & show ingredients list on widget
            // ToDo - onclick of widget, open respective recipe

            RemoteViews remoteViews = new RemoteViews(
                    context.getPackageName(), R.layout.widget_recipe_ingredients);
            remoteViews.setRemoteAdapter(R.id.widget_recipe_ingridients_listview, svcIntent);
            remoteViews.setEmptyView(R.id.widget_recipe_ingridients_listview, R.id.widget_recipe_ingridients_empty_view);

            //PendingIntent appPendingIntent = PendingIntent.getActivity(context,0,svcIntent,PendingIntent.FLAG_UPDATE_CURRENT);
            //remoteViews.setPendingIntentTemplate(R.id.widget_recipe_ingridients_listview,appPendingIntent);



            Intent toastIntent = new Intent(context, RecipeWidgetProvider.class);
            toastIntent.setAction(RecipeWidgetProvider.TOAST_ACTION);
            toastIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetIds[i]);
            svcIntent.setData(Uri.parse(svcIntent.toUri(Intent.URI_INTENT_SCHEME)));
            PendingIntent toastPendingIntent = PendingIntent.getBroadcast(context, 0, toastIntent,
                    PendingIntent.FLAG_UPDATE_CURRENT);
            remoteViews.setPendingIntentTemplate(R.id.widget_recipe_ingridients_listview, toastPendingIntent);



//            PendingIntent pendingIntent = PendingIntent.getActivity(context,
//                    0, svcIntent, 0);
            appWidgetManager.updateAppWidget(widgetId, remoteViews);
        }
        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }
}