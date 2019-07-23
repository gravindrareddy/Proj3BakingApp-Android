/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package redgun.bakingapp.utilities;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import androidx.appcompat.app.AlertDialog;

import java.net.MalformedURLException;
import java.net.URL;

import redgun.bakingapp.R;

/**
 * These utilities will be used to communicate with the network.
 */
public class NetworkUtils {
    private static String TAG = "NetworkUtils";

    /**
     * Builds the URL used to query Github.
     *
     * @return The URL to use to query the weather server.
     */
    public static URL buildRecipeUrl(Context context) {
        Uri builtUri = Uri.parse(context.getResources().getString(R.string.base_url)).buildUpon().build();
        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }




    public static boolean isOnline(Context _context) {
        ConnectivityManager cm =
                (ConnectivityManager) _context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        } else {
            new AlertDialog.Builder(_context)
                    .setTitle(_context.getResources().getString(R.string.app_name))
                    .setMessage(
                            _context.getResources().getString(
                                    R.string.internet_error))
                    .setPositiveButton("OK", null).show();
        }
        return false;
    }
}