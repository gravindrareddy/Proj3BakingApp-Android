package redgun.bakingapp;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.widget.RemoteViews;

import static redgun.bakingapp.SettingsActivity.context;

/**
 * Created by gravi on 13-07-2017.
 */


public class WidgetIntentService extends IntentService {

    public WidgetIntentService() {
        super(WidgetIntentService.class.getName());
        setIntentRedelivery(true);
    }


    @Override
    protected void onHandleIntent(Intent intent) {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(context, RecipeWidgetProvider.class));
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.widget_recipe_ingridients_listview);
        RecipeWidgetProvider.updateList(this, appWidgetManager, appWidgetIds);
    }
}