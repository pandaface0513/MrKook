package ca.hwlo.mrkook;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;


import com.oguzdev.circularfloatingactionmenu.library.FloatingActionButton;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;

/**
 * Created by Henry on 2015-07-31.
 */

public class MainActivity extends ActionBarActivity implements View.OnClickListener {

    private SharedPreferences sharedPrefs;

    private static String DEFAULT = "0";

    private TextView titleText, mealText;

    private Button addFood;

    private static String greet = "Welcome back, ";

    ImageButton FAB;


    //database stuffs below
    private GridView grid;
    DBClass db;
    Cursor cursor;
    SimpleCursorAdapter cursorAdapter;

    //request codes
    private static int ADD_FOOD_REQUEST = 111;
    private static int FIND_RECIPE_REQUEST = 222;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //changes the title text
        setTitle("MrKook - Home");

        //check if user data exists, if no, go to setup.
        sharedPrefs = getSharedPreferences("MrKook", Context.MODE_PRIVATE);
        String username = sharedPrefs.getString("username", DEFAULT);

        if(username.equals(DEFAULT)){
            Toast.makeText(this, "No data found, proceed to setup 1st timer", Toast.LENGTH_SHORT).show();

            //start setup activity
            Intent setupIntent = new Intent(this, SetupActivity.class);
            startActivity(setupIntent);

        }else{  //if yes, then display greeting and food items in grid
            Toast.makeText(this, "Got data, proceed to greet this user", Toast.LENGTH_SHORT).show();

            //set up the greetings
            setupGreetings(username);

            //After the greetings, we should set up the database and food items
            setupFoodGrid();
        }

//        //For logo in action bar
//        ActionBar actionBar = getSupportActionBar();
//        actionBar.setDisplayShowCustomEnabled(true);
//        actionBar.setIcon(R.mipmap.ic_launcher);

//        // in Activity Context
//        ImageView icon = new ImageView(this); // Create an icon
//        icon.setImageDrawable(getResources().getDrawable(R.drawable.ic_launcher));
//
//        FloatingActionButton actionButton = new FloatingActionButton.Builder(this)
//                .setContentView(icon)
//                .build();
//
//        actionButton.setOnClickListener(this);

        FAB = (ImageButton) findViewById(R.id.imageButton);
        FAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(MainActivity.this,AddFoodActivity.class);
                    startActivityForResult(i, ADD_FOOD_REQUEST);



            }
        });


    }

    private void setupGreetings(String username){
        //if yes, then initialize UI elements
        titleText = (TextView) findViewById(R.id.nameTitle);
        mealText = (TextView) findViewById(R.id.mealText);

        grid = (GridView) findViewById(R.id.foodGrid);

//        addFood = (Button) findViewById(R.id.addBtn);
//        addFood.setOnClickListener(this);

//        mapBtn = (Button) findViewById(R.id.mapBtn);
//        mapBtn.setOnClickListener(this);

        //set up the greeting dialog
        titleText.setText(greet + username + "!");

        //get current time
        TimeZone stz = TimeZone.getDefault();
        GregorianCalendar gCal = new GregorianCalendar(stz);

        int hour = gCal.get(Calendar.HOUR_OF_DAY);
        String meal;

        if(hour > 6 && hour < 12){
            meal = "Breakfast?";
        }else if(hour < 14){
            meal = "Lunch?";
        }else if(hour < 18){
            meal = "Afternoon snack?";
        }else if(hour < 21){
            meal = "Dinner?";
        }else {
            meal = "Late night snack?";
        }
        Log.d("Time", meal);

        mealText.setText(meal);
    }

    private void setupFoodGrid(){
        //set up database
        db = new DBClass(this);

        //for cursor adapter, specify which columns go into which views
        String[] fromColumns = {Constants.NAME, Constants.AMOUNNT};
        int[] toViews = {R.id.foodNameText, R.id.foodAmount};

        //grab all data entry
        cursor = db.getAllCursor();

        //check if any data entries
        if(cursor != null){
            cursorAdapter = new SimpleCursorAdapter(this, R.layout.list_grid, cursor, fromColumns, toViews, 2);
            grid.setAdapter(cursorAdapter);

            //insert on click listener here after

            //check if any items in cursor
            if(cursor.getCount() < 1){
                Toast.makeText(this, "No food item stored!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //check which request we're responding //
        if(requestCode == ADD_FOOD_REQUEST){
            //make sure the request was successful
            if(resultCode == RESULT_OK){
                //then we handle the rest
                String name = data.getStringExtra("foodName");
                int amount = data.getIntExtra("foodAmount", 0);

                //try insert the data into db
                long id = db.insertData(name, amount);

                if(id < 0){
                    //fail
                }else{
                    //success
                    updateUI();
                }
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    private void updateUI(){
        cursor = db.getAllCursor();
        cursorAdapter.swapCursor(cursor);
        cursorAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        //check which button
        switch(v.getId()){
<<<<<<< HEAD
            case R.id.addBtn:
                Toast.makeText(this,"Proceed to add food page", Toast.LENGTH_SHORT).show();

                Intent addFoodIntent = new Intent(MainActivity.this, AddFoodActivity.class);
                startActivityForResult(addFoodIntent, ADD_FOOD_REQUEST);
                break;
//            case R.id.mapBtn:
//                Toast.makeText(this,"Proceed to map page", Toast.LENGTH_SHORT).show();
//
//                Intent mapIntent = new Intent(MainActivity.this, MapsActivity.class);
//                startActivity(mapIntent);
=======
//            case R.id.addBtn:
//                Toast.makeText(this,"Proceed to add food page", Toast.LENGTH_SHORT).show();
//
//                Intent addFoodIntent = new Intent(MainActivity.this, AddFoodActivity.class);
//                startActivityForResult(addFoodIntent, ADD_FOOD_REQUEST);
>>>>>>> origin/UIedits
//                break;
        }
    }
}
