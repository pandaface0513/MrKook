package ca.hwlo.mrkook;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;

public class AddFoodActivity extends AppCompatActivity implements View.OnClickListener {

    //UI Elements
    EditText foodnameField, foodamountField;
    Button addfoodButton, cancelfoodButton;

    private static final int CAMERA_REQUEST = 1888;
    private ImageView imageView;

    //Camera Stuff
    private ImageView imageview;
    private byte[] img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_food);

        foodnameField = (EditText) findViewById(R.id.foodNameField);
        foodamountField = (EditText) findViewById(R.id.foodAmountField);

        addfoodButton = (Button) findViewById(R.id.addFoodBtn);
        addfoodButton.setOnClickListener(this);

        cancelfoodButton = (Button) findViewById(R.id.cancelFoodBtn);
        cancelfoodButton.setOnClickListener(this);
        cancelfoodButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AddFoodActivity.this,MainActivity.class);
                startActivity(i);
            }
        });


        //For logo in action bar
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setIcon(R.mipmap.ic_launcher);



    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_food, menu);
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

    @Override
    public void onClick(View v) {
        //check the button
        switch(v.getId()){
            case R.id.addFoodBtn:
                if (foodnameField.getText().toString().trim().equals(""))
                {
                    foodnameField.requestFocus();
                    InputMethodManager inputManager = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputManager.showSoftInput(this.getCurrentFocus(), InputMethodManager.SHOW_IMPLICIT);
                    Toast.makeText(this, "You need to enter a name", Toast.LENGTH_SHORT).show();
                }else if (foodamountField.getText().toString().trim().equals(""))
                {
                    foodamountField.requestFocus();
                    InputMethodManager inputManager = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputManager.showSoftInput(this.getCurrentFocus(), InputMethodManager.SHOW_IMPLICIT);
                    Toast.makeText(this, "You need to enter a number", Toast.LENGTH_SHORT).show();
                }else {
                    String name = foodnameField.getText().toString();
                    int amount = Integer.parseInt(foodamountField.getText().toString());

                    //if all okay, send result back
                    Intent i = new Intent();
                    i.putExtra("foodName", name);
                    i.putExtra("foodAmount", amount);
                    i.putExtra("imageData", img);
                    setResult(RESULT_OK, i);
                    finish();
                }
                break;
        }

    }
}
