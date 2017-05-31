package redgun.bakingapp.utilities;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.widget.Toast;

import redgun.bakingapp.R;

/**
 * Created by Ravindra on 30-05-2017.
 */

public class Utils {
    public static void showToast(Context _context, String value) {
        if (value != null)
            Toast.makeText(_context, value, Toast.LENGTH_LONG).show();
    }

}
