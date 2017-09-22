package redgun.bakingapp.features.recipes.adapter;

/**
 * Created by Ravindra on 01-06-2017.
 */

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import redgun.bakingapp.R;
import redgun.bakingapp.models.Recipes;

public class RecipesAdapter extends RecyclerView.Adapter<RecipesAdapter.MyViewHolder> {

    private List<Recipes> recipesList;

    public RecipesAdapter(List<Recipes> recipesList) {
        this.recipesList = recipesList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_listitem_recipe, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Recipes recipe = recipesList.get(position);
        holder.recipe_name_textview.setText(recipe.getRecipeName());
    }

    @Override
    public int getItemCount() {
        return recipesList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView recipe_name_textview;

        public MyViewHolder(View view) {
            super(view);
            recipe_name_textview = (TextView) view.findViewById(R.id.recipe_name_textview);
        }
    }
}