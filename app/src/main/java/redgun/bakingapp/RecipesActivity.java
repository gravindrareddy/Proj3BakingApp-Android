package redgun.bakingapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Ravindra on 29-05-2017.
 */

public class RecipesActivity extends AppCompatActivity {
    static final String[] recipe_names = {};
    GridView recipe_grid_view;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_recipes);

        recipe_grid_view = (GridView) findViewById(R.id.recipe_grid_view);
        fetchRecipeNames();

        // Create adapter to set value for grid view
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, recipe_names);

        recipe_grid_view.setAdapter(adapter);

        recipe_grid_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {


            @Override
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                //ToDo - Show steps of the recipe
                //ToDo - Intent to call next activity/ fragment
                Toast.makeText(getApplicationContext(),
                        ((TextView) v).getText(), Toast.LENGTH_SHORT).show();


            }
        });

    }

    public void fetchRecipeNames() {
        // ToDo - fetch recipe names from API call
    }
}
