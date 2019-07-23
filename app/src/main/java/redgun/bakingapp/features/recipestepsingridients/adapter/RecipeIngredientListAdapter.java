package redgun.bakingapp.features.recipestepsingridients.adapter;

import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import redgun.bakingapp.R;
import redgun.bakingapp.models.RecipeIngredients;

/**
 * Created by Ravindra on 29-05-2017.
 */

public class RecipeIngredientListAdapter extends RecyclerView.Adapter<RecipeIngredientListAdapter.MyViewHolder> {


    private ArrayList<RecipeIngredients> recipesIngredientsList;

    public RecipeIngredientListAdapter(ArrayList<RecipeIngredients> recipesIngredientsList) {
        this.recipesIngredientsList = recipesIngredientsList;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_listitem_recipe_ingredient, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        RecipeIngredients recipeIngredient = recipesIngredientsList.get(position);
        holder.recipe_ingredient_textview.setText(recipeIngredient.getRecipeIngredientName() + ": " + recipeIngredient.getRecipeIngredientQuantity() + " " + recipeIngredient.getRecipeIngredientMeasureUnit());
    }

    @Override
    public int getItemCount() {
        return recipesIngredientsList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView recipe_ingredient_textview;


        public MyViewHolder(View view) {
            super(view);
            recipe_ingredient_textview = (TextView) view.findViewById(R.id.recipe_ingredient_item_textview);
        }
    }

}
