package ca.hwlo.mrkook;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
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

public class AddFoodActivity extends AppCompatActivity implements View.OnClickListener {

    //UI Elements
    EditText foodnameField, foodamountField;
    Button addfoodButton, cancelfoodButton;

    private static final int CAMERA_REQUEST = 1888;
    private ImageView imageView;

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

        //camera
        this.imageView = (ImageView)this.findViewById(R.id.imageView1);
        Button photoButton = (Button) this.findViewById(R.id.button1);
        photoButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            }
        });

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            imageView.setImageBitmap(photo);
        }
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
                    setResult(RESULT_OK, i);
                    finish();
                }
                break;
        }
    }
}
