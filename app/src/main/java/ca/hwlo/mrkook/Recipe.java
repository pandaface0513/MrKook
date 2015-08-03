package ca.hwlo.mrkook;

/**
 * Created by Henry on 2015-08-03.
 */
public class Recipe {

    public String recipeID;
    public String recipeRank;
    public String recipeTitle;
    public String recipeImageURL;
    public String recipeSourceURL;

    public Recipe(String id, String rank, String title, String imageURL, String sourceURL){
        recipeID = id;
        recipeRank = rank;
        recipeTitle = title;
        recipeImageURL = imageURL;
        recipeSourceURL = sourceURL;
    }

}
