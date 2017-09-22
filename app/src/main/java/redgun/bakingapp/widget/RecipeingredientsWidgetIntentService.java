package redgun.bakingapp.widget;

import android.app.IntentService;
import android.app.Notification;
import android.content.Intent;
import android.support.annotation.Nullable;

/**
 * Created by Ravindra on 13-06-2017.
 */

public class RecipeingredientsWidgetIntentService extends IntentService {
    public static final String ACTION_OPEN_RECIPE_STEPS = "redgun.bakingapp.open_recipe_steps";

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public RecipeingredientsWidgetIntentService(String name) {
        super(name);
    }


    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if(intent!=null) {
            final String action = intent.getAction();
            if(ACTION_OPEN_RECIPE_STEPS.equals(action)){

            }
        }
    }
}
