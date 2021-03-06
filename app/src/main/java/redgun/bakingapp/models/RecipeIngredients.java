package redgun.bakingapp.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by gravi on 29-09-2016.
 */
public class RecipeIngredients implements Parcelable {


    // Creator
    public static final Creator CREATOR
            = new Creator() {
        public RecipeIngredients createFromParcel(Parcel in) {
            return new RecipeIngredients(in);
        }
        public RecipeIngredients[] newArray(int size) {
            return new RecipeIngredients[size];
        }
    };

    @SerializedName("quantity")
    String recipeIngredientQuantity;
    @SerializedName("measure")
    String recipeIngredientMeasureUnit;
    @SerializedName("ingredient")
    String recipeIngredientName;

    public RecipeIngredients(String recipeIngredientQuantity, String recipeIngredientMeasureUnit, String recipeIngredientName) {
        this.recipeIngredientQuantity = recipeIngredientQuantity;
        this.recipeIngredientMeasureUnit = recipeIngredientMeasureUnit;
        this.recipeIngredientName = recipeIngredientName;
    }

    // "De-parcel object
    public RecipeIngredients(Parcel in) {
        recipeIngredientQuantity = in.readString();
        recipeIngredientMeasureUnit = in.readString();
        recipeIngredientName = in.readString();
    }

    public String getRecipeIngredientQuantity() {
        return recipeIngredientQuantity;
    }

    public void setRecipeIngredientQuantity(String recipeIngredientQuantity) {
        this.recipeIngredientQuantity = recipeIngredientQuantity;
    }

    public String getRecipeIngredientMeasureUnit() {
        return recipeIngredientMeasureUnit;
    }

    public void setRecipeIngredientMeasureUnit(String recipeIngredientMeasureUnit) {
        this.recipeIngredientMeasureUnit = recipeIngredientMeasureUnit;
    }

    public String getRecipeIngredientName() {
        return recipeIngredientName;
    }

    public void setRecipeIngredientName(String recipeIngredientName) {
        this.recipeIngredientName = recipeIngredientName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(recipeIngredientQuantity);
        dest.writeString(recipeIngredientMeasureUnit);
        dest.writeString(recipeIngredientName);
    }


}
