package ca.hwlo.mrkook;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class FoodListActivity extends ActionBarActivity implements AdapterView.OnItemClickListener {

    private ArrayList<String> foodList;

    private ListView list;

    DBClass db;

    String term;
    long itemInQuestion;

    Cursor cursor;

    SimpleCursorAdapter cursorAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_list);

        //set title
        setTitle(getString(R.string.app_name) + " - Food List");

        //get references to the listview
        list = (ListView) findViewById(R.id.listView);
        foodList = new ArrayList<String>();

        Intent intent = getIntent();
        Bundle b = intent.getExtras();

        //if bundle isn't empty
        if(b != null){
            db = new DBClass(this);
            term = b.getString("term");

            //for the cursor adapter, specify which columns go into which views
            String[] fromColumns = {Constants.NAME, Constants.AMOUNNT};
            int[] toViews = {R.id.foodName, R.id.foodAmount};

            if(term.equals("all")){
                //if to view all
                cursor = db.getAllCursor();
            }else{
                //if to view selected
                cursor = db.getSelectedCursor(term);
            }

            if(cursor != null) {
                cursorAdapter = new SimpleCursorAdapter(this, R.layout.list_row, cursor, fromColumns, toViews, 2);
                list.setAdapter(cursorAdapter);
                list.setOnItemClickListener(this);

                int dataCount = cursor.getCount();
                if(dataCount < 1){
                    Toast.makeText(this, "No data!", Toast.LENGTH_SHORT).show();
                }
            }
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_food_list, menu);
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
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        itemInQuestion = id;
        createAndShowAlertDialog();
    }

    private void updateUI(){
        if(term.equals("all")){
            //if to view all
            cursor = db.getAllCursor();
        }else{
            //if to view selected
            cursor = db.getSelectedCursor(term);
        }

        cursorAdapter.swapCursor(cursor);
        cursorAdapter.notifyDataSetChanged();
    }

    private void createAndShowAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Alert!");
        builder.setMessage("Do you want to remove this food?");
        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                //TODO
                dialog.dismiss();
            }
        });
        builder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                //TODO
                db.deleteEntry(itemInQuestion);
                updateUI();
                Toast.makeText(getApplicationContext(), "Food Removed", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
