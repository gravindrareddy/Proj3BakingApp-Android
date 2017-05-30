package redgun.bakingapp.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by gravi on 29-09-2016.
 */
public class RecipeSteps implements Parcelable {


    // Creator
    public static final Creator CREATOR
            = new Creator() {
        public RecipeSteps createFromParcel(Parcel in) {
            return new RecipeSteps(in);
        }

        public RecipeSteps[] newArray(int size) {
            return new RecipeSteps[size];
        }
    };
    @SerializedName("id")
    int recipeStepId;
    @SerializedName("shortDescription")
    String recipeStepShortDescription;
    @SerializedName("description")
    String recipeStepDescription;
    @SerializedName("videoURL")
    String recipeStepVideoURL;

    public int getRecipeStepId() {
        return recipeStepId;
    }

    public void setRecipeStepId(int recipeStepId) {
        this.recipeStepId = recipeStepId;
    }

    public String getRecipeStepShortDescription() {
        return recipeStepShortDescription;
    }

    public void setRecipeStepShortDescription(String recipeStepShortDescription) {
        this.recipeStepShortDescription = recipeStepShortDescription;
    }

    public String getRecipeStepDescription() {
        return recipeStepDescription;
    }

    public void setRecipeStepDescription(String recipeStepDescription) {
        this.recipeStepDescription = recipeStepDescription;
    }

    public String getRecipeStepVideoURL() {
        return recipeStepVideoURL;
    }

    public void setRecipeStepVideoURL(String recipeStepVideoURL) {
        this.recipeStepVideoURL = recipeStepVideoURL;
    }

    public String getRecipeStepThumbnailURL() {
        return recipeStepThumbnailURL;
    }

    public void setRecipeStepThumbnailURL(String recipeStepThumbnailURL) {
        this.recipeStepThumbnailURL = recipeStepThumbnailURL;
    }

    @SerializedName("thumbnailURL")
    String recipeStepThumbnailURL;

    RecipeSteps(int recipeStepId, String recipeStepShortDescription, String recipeStepDescription, String recipeStepVideoURL, String recipeStepThumbnailURL) {
        this.recipeStepId = recipeStepId;
        this.recipeStepShortDescription = recipeStepShortDescription;
        this.recipeStepDescription = recipeStepDescription;
        this.recipeStepVideoURL = recipeStepVideoURL;
        this.recipeStepThumbnailURL = recipeStepThumbnailURL;
    }

    // "De-parcel object
    public RecipeSteps(Parcel in) {
        recipeStepId = in.readInt();
        recipeStepShortDescription = in.readString();
        recipeStepDescription = in.readString();
        recipeStepVideoURL = in.readString();
        recipeStepThumbnailURL = in.readString();
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(recipeStepId);
        dest.writeString(recipeStepShortDescription);
        dest.writeString(recipeStepDescription);
        dest.writeString(recipeStepVideoURL);
        dest.writeString(recipeStepThumbnailURL);
    }
}
