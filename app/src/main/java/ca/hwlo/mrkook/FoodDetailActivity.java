package ca.hwlo.mrkook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class FoodDetailActivity extends AppCompatActivity implements View.OnClickListener {

    //UI Element
    private EditText amountField;
    private TextView nameText;

    private Button deleteButton, updateButton;

    private String foodname, foodamount;
    private long itemInQuestion;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_detail);

        nameText = (TextView) findViewById(R.id.fooddetailname);
        amountField = (EditText) findViewById(R.id.fooddetailamount);

        deleteButton = (Button) findViewById(R.id.deleteBtn);
        deleteButton.setOnClickListener(this);
        updateButton = (Button) findViewById(R.id.updateBtn);
        updateButton.setOnClickListener(this);

        Bundle b = getIntent().getExtras();

        if(b != null){
            foodname = b.getString("foodname");
            foodamount = b.getString("foodamount");
            itemInQuestion = b.getLong("itemInQuestion");

            nameText.setText(foodname);
            amountField.setText(foodamount);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_food_detail, menu);
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
        switch(v.getId()){
            case R.id.deleteBtn:
                //if all okay, send result back
                Intent deleteIntent = new Intent();
                deleteIntent.putExtra("itemInQuestion", itemInQuestion);
                deleteIntent.putExtra("operation", "delete");
                setResult(RESULT_OK, deleteIntent);
                finish();
                break;
            case R.id.updateBtn:
                //if all okay, send result back
                Intent updateIntent = new Intent();
                updateIntent.putExtra("itemInQuestion", itemInQuestion);
                updateIntent.putExtra("foodamount", amountField.getText().toString());
                updateIntent.putExtra("operation", "update");
                setResult(RESULT_OK, updateIntent);
                finish();
                break;
        }
    }
}
