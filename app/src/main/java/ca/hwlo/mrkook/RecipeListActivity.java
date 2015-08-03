package ca.hwlo.mrkook;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class RecipeListActivity extends AppCompatActivity {

    //UI Elements
    private TextView searchField;
    private ListView recipeList;

    private int resultNum = 0;

    private static final int recipeLimit = 9;

    //construct the array for recipe
    private ArrayList<Recipe> recipes;

    //custom adapter for recipe
    private RecipeListAdapter recipeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);

        setTitle("Recipe Result : " + resultNum + " items");

        //UI Elements
        searchField = (TextView) findViewById(R.id.searchField);
        recipeList = (ListView) findViewById(R.id.recipeList);

        Bundle b = getIntent().getExtras();
        String name = b.getString("foodname");
        searchField.setText(name);

        //add adapter
        recipes = new ArrayList<>();
        recipeAdapter = new RecipeListAdapter(this, recipes);
        recipeList = (ListView) findViewById(R.id.recipeList);
        recipeList.setAdapter(recipeAdapter);

        // Check if no view has focus:
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager inputManager = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }   //hide the keyboard!

        getRecipeSearch(name);

    }

    private void getRecipeSearch(String term){
        String urlString = Constants.API_URL + "?key=" + Constants.API_KEY + "&q=" + term + "&sort=r";
        String data = "";
        try {
            data = new Food2ForkAPI(this).execute(urlString).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        if(data != null){
            try {
                //grab the object
                JSONObject jsonObject = new JSONObject(data);
                //grab the count
                resultNum = Integer.parseInt(jsonObject.getString("count"));
                setTitle("Recipe Result : " + resultNum + " items");
                //grab the array inside the object
                JSONArray recipeArray = jsonObject.getJSONArray("recipes");

                Log.d("LOL", data);

                //for every object inside the array, grab the data
                for (int i = 0; i < recipeArray.length(); i++){
                    //initialize the recipeData object
                    JSONObject recipeData = recipeArray.getJSONObject(i);
                    //then extract the data
                    String title = recipeData.getString("title");
                    String rank = recipeData.getString("social_rank");
                    String sourceURL = recipeData.getString("source_url");
                    String imageURL = recipeData.getString("image_url");
                    String recipeID = recipeData.getString("recipe_id");
                    //create a recipe object and put in the data
                    Recipe newRecipe = new Recipe(recipeID, rank, title, imageURL, sourceURL);

                    recipeAdapter.add(newRecipe);
                }

            }catch (Exception e){
                Log.d("Food Error", e.getLocalizedMessage());
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_recipe_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
