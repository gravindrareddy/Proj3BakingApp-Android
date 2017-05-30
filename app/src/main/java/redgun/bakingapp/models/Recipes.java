package redgun.bakingapp.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by gravi on 29-09-2016.
 */
public class Recipes implements Parcelable {


    // Creator
    public static final Parcelable.Creator CREATOR
            = new Parcelable.Creator() {
        public Recipes createFromParcel(Parcel in) {
            return new Recipes(in);
        }

        public Recipes[] newArray(int size) {
            return new Recipes[size];
        }
    };
    @SerializedName("id")
    int recipeId;
    @SerializedName("name")
    String recipeName;
    @SerializedName("ingredients")
    ArrayList<RecipeIngredients> recipeIngredients;
    @SerializedName("steps")
    ArrayList<RecipeSteps> recipeSteps;

    public int getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(int recipeId) {
        this.recipeId = recipeId;
    }

    public String getRecipeName() {
        return recipeName;
    }

    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName;
    }

    public ArrayList<RecipeIngredients> getRecipeIngredients() {
        return recipeIngredients;
    }

    public void setRecipeIngredients(ArrayList<RecipeIngredients> recipeIngredients) {
        this.recipeIngredients = recipeIngredients;
    }

    public ArrayList<RecipeSteps> getRecipeSteps() {
        return recipeSteps;
    }

    public void setRecipeSteps(ArrayList<RecipeSteps> recipeSteps) {
        this.recipeSteps = recipeSteps;
    }

    public int getRecipeServings() {
        return recipeServings;
    }

    public void setRecipeServings(int recipeServings) {
        this.recipeServings = recipeServings;
    }

    public String getRecipeImage() {
        return recipeImage;
    }

    public void setRecipeImage(String recipeImage) {
        this.recipeImage = recipeImage;
    }

    @SerializedName("servings")

    int recipeServings;
    @SerializedName("image")
    String recipeImage;

    Recipes(int recipeId, String recipeName, ArrayList<RecipeIngredients> recipeIngredients, ArrayList<RecipeSteps> recipeSteps, int recipeServings, String recipeImage) {
        this.recipeId = recipeId;
        this.recipeName = recipeName;
        this.recipeIngredients = recipeIngredients;
        this.recipeSteps = recipeSteps;
        this.recipeServings = recipeServings;
        this.recipeImage = recipeImage;
    }


    // "De-parcel object
    public Recipes(Parcel in) {
        recipeId = in.readInt();
        recipeName = in.readString();
        recipeIngredients = in.readArrayList(new ClassLoader() {
            @Override
            public Class<?> loadClass(String name) throws ClassNotFoundException {
                return super.loadClass(name);
            }
        });
        recipeSteps = in.readArrayList(new ClassLoader() {
            @Override
            public Class<?> loadClass(String name) throws ClassNotFoundException {
                return super.loadClass(name);
            }
        });
        this.recipeServings = in.readInt();
        this.recipeImage = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(recipeId);
        dest.writeString(recipeName);
        dest.writeList(recipeIngredients);
        dest.writeList(recipeSteps);
        dest.writeInt(recipeServings);
        dest.writeString(recipeImage);
    }


}
