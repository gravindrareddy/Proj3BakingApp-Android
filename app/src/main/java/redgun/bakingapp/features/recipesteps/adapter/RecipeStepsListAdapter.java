package redgun.bakingapp.features.recipesteps.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import redgun.bakingapp.R;
import redgun.bakingapp.features.recipesteps.ui.RecipeStepsFragment;
import redgun.bakingapp.models.RecipeSteps;

/**
 * Created by Ravindra on 29-05-2017.
 */


public class RecipeStepsListAdapter extends RecyclerView.Adapter<RecipeStepsListAdapter.MyViewHolder> {

    RecipeStepsFragment.OnRecipeStepClickListener listener;
    private List<RecipeSteps> recipesList;

    public RecipeStepsListAdapter(List<RecipeSteps> recipesList) {
        this.recipesList = recipesList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_listitem_recipe_step, parent, false);

        return new MyViewHolder(itemView);
    }


    public void setOnItemClickListener(RecipeStepsFragment.OnRecipeStepClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        RecipeSteps recipeSteps = recipesList.get(position);
        holder.recipe_step_short_description_textview.setText(recipeSteps.getRecipeStepShortDescription());

        holder.listitem_recipe_step_linearlayout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                listener.onRecipeStepClicked(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return recipesList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView recipe_step_short_description_textview;
        public LinearLayout listitem_recipe_step_linearlayout;

        public MyViewHolder(View view) {
            super(view);
            recipe_step_short_description_textview = (TextView) view.findViewById(R.id.recipe_step_short_description_textview);
            listitem_recipe_step_linearlayout = (LinearLayout) view
                    .findViewById(R.id.listitem_recipe_step_linearlayout);
        }
    }
}