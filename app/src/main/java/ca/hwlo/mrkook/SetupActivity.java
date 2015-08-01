package ca.hwlo.mrkook;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class SetupActivity extends ActionBarActivity implements View.OnClickListener{

    //UI Elements
    EditText nameField, passwordField;
    Button continueBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup);

        //changes the title text
        setTitle("MrKook - First Time Set Up");

        nameField = (EditText) findViewById(R.id.nameField);
        passwordField = (EditText) findViewById(R.id.passwordField);

        continueBtn = (Button) findViewById(R.id.continueBtn);
        continueBtn.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
        //check which button is clicked
        switch (v.getId()){
            case R.id.continueBtn:
                SharedPreferences sharedPrefs = getSharedPreferences("MrKook", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPrefs.edit();
                editor.putString("username", nameField.getText().toString());
                editor.putString("password", passwordField.getText().toString());
                Toast.makeText(this, "Username - " + nameField.getText().toString() + " Created", Toast.LENGTH_SHORT).show();
                editor.apply();

                //start main activity
                Intent mainIntent = new Intent(this, MainActivity.class);
                startActivity(mainIntent);
                break;
        }
    }
}

