/*
 * Copyright (C) 2014 The Android Open Source Project
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
package redgun.bakingapp.data;

import android.annotation.TargetApi;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.os.Looper;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.CursorLoader;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import redgun.bakingapp.R;
import redgun.bakingapp.models.Recipes;
import redgun.bakingapp.utilities.NetworkUtils;

public class RecipesProvider extends ContentProvider {

    static final int RECIPES = 100;
    // The URI Matcher used by this content provider.
    private static final UriMatcher sUriMatcher = buildUriMatcher();
    private static final SQLiteQueryBuilder sWeatherByLocationSettingQueryBuilder;

    static {
        sWeatherByLocationSettingQueryBuilder = new SQLiteQueryBuilder();
    }

    private RecipesDbHelper mOpenHelper;

    /*
        This UriMatcher will match each URI to the RECIPES, MOVIE_DETAILS
        constants defined above.
     */
    static UriMatcher buildUriMatcher() {
        // I know what you're thinking.  Why create a UriMatcher when you can use regular
        // expressions instead?  Because you're not crazy, that's why.

        // All paths added to the UriMatcher have a corresponding code to return when a match is
        // found.  The code passed into the constructor represents the code to return for the root
        // URI.  It's common to use NO_MATCH as the code for this case.
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = RecipesContract.CONTENT_AUTHORITY;

        // For each type of URI you want to add, create a corresponding code.
        matcher.addURI(authority, RecipesContract.PATH_RECIPE, RECIPES);
        return matcher;
    }

    /*
        We just create a new RecipesDbHelper for later use here.
     */
    @Override
    public boolean onCreate() {
        mOpenHelper = new RecipesDbHelper(getContext());
        return true;
    }

    /*
        Students: Here's where you'll code the getType function that uses the UriMatcher.  You can
        test this by uncommenting testGetType in TestProvider.

     */
    @Override
    public String getType(Uri uri) {

        // Use the Uri Matcher to determine what kind of URI this is.
        final int match = sUriMatcher.match(uri);

        switch (match) {
            case RECIPES:
                return RecipesContract.RecipeEntry.CONTENT_TYPE;

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
                        String sortOrder) {
        // Here's the switch statement that, given a URI, will determine what kind of request it is,
        // and query the database accordingly.
        Cursor retCursor;
        switch (sUriMatcher.match(uri)) {

            case RECIPES: {
                retCursor = mOpenHelper.getReadableDatabase().query(
                        RecipesContract.RecipeEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            }


            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        retCursor.setNotificationUri(getContext().getContentResolver(), uri);
        return retCursor;
    }

    /*
        Student: Add the ability to insert Locations to the implementation of this function.
     */
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        Uri returnUri;

        switch (match) {
            case RECIPES: {
                long _id = db.insert(RecipesContract.RecipeEntry.TABLE_NAME, null, values);
                if (_id > 0)
                    returnUri = RecipesContract.RecipeEntry.buildRecipesUri(_id);
                else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;
            }
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return returnUri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int rowsDeleted;
        // this makes delete all rows return the number of rows deleted
        if (null == selection) selection = "1";
        switch (match) {
            case RECIPES:
                rowsDeleted = db.delete(
                        RecipesContract.RecipeEntry.TABLE_NAME, selection, selectionArgs);
                break;

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        // Because a null deletes all rows
        if (rowsDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsDeleted;
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int rowsUpdated;
        // this makes delete all rows return the number of rows deleted
        if (null == selection) selection = "1";
        switch (match) {
            case RECIPES:
                rowsUpdated = db.update(
                        RecipesContract.RecipeEntry.TABLE_NAME, contentValues, selection, selectionArgs);
                break;

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        // Because a null deletes all rows
        if (rowsUpdated != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsUpdated;
    }


    // You do not need to call this method. This is a method specifically to assist the testing
    // framework in running smoothly. You can read more at:
    // http://developer.android.com/reference/android/content/ContentProvider.html#shutdown()
    @Override
    @TargetApi(11)
    public void shutdown() {
        mOpenHelper.close();
        super.shutdown();
    }


    public static ArrayList<Recipes> fetchRecipes(Context context) {
        ArrayList<Recipes> recipesList = new ArrayList<>();
        Uri.Builder builder = new Uri.Builder();
        Uri _uri = builder.scheme("content")
                .authority(context.getResources().getString(R.string.contentprovider_authority))
                .appendPath(context.getResources().getString(R.string.contentprovider_recipe_entry)).build();
        //Looper.prepare();
        Cursor _cursor = context.getContentResolver().query(_uri, null, null, null, null);

        //CursorLoader cursorLoader = new CursorLoader(
//                context,
//                _uri, null, null, null, null);
//        Cursor _cursor = cursorLoader.loadInBackground();
        if (_cursor != null && _cursor.getCount() > 0) {
            _cursor.moveToFirst();
            String recipesListStr = _cursor.getString(_cursor.getColumnIndex(RecipesContract.RecipeEntry.COLUMN_RECIPES_JSON));
            Gson gson = new GsonBuilder().create();
            Type collectionType = new TypeToken<ArrayList<Recipes>>() {
            }.getType();
            recipesList = gson.fromJson(recipesListStr, collectionType);

        }
        return recipesList;
    }


    /**
     * This method returns the entire result from the HTTP response.
     *
     * @param url The URL to fetch the HTTP response from.
     * @return The contents of the HTTP response.
     * @throws IOException Related to network and stream reading
     */
    public static ArrayList<Recipes> getRecipesFromURL(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.setRequestMethod("GET");
        InputStream inputStream;
        ArrayList<Recipes> recipesList = new ArrayList<Recipes>();
        try {
            inputStream = urlConnection.getInputStream();
            Gson gson = new GsonBuilder().create();
            Type collectionType = new TypeToken<ArrayList<Recipes>>() {
            }.getType();
            recipesList = gson.fromJson(new BufferedReader(new InputStreamReader(inputStream)), collectionType);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            urlConnection.disconnect();
        }
        return recipesList;
    }

    public static class FetchRecipes extends AsyncTaskLoader<ArrayList<Recipes>> {
        Context mContext;

        public FetchRecipes(Context context) {
            super(context);
            mContext = context;
        }

        @Override
        public ArrayList<Recipes> loadInBackground() {
            BufferedReader reader = null;
            ArrayList<Recipes> recipesArrayList = new ArrayList<Recipes>();
            try {
                URL url = NetworkUtils.buildRecipeUrl(mContext);
                recipesArrayList = getRecipesFromURL(url);

            } catch (IOException e) {
                Log.e("PlaceholderFragment", "Error ", e);
                e.printStackTrace();
                return null;
            } finally {
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e(mContext.getPackageName(), "Error closing stream", e.fillInStackTrace());
                        e.printStackTrace();
                    }
                }
            }
            return recipesArrayList;

        }


        @Override
        public void deliverResult(ArrayList<Recipes> data) {
            super.deliverResult(data);
        }
    }
}