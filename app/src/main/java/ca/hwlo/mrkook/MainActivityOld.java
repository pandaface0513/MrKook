//package ca.hwlo.mrkook;
//
//import android.content.Context;
//import android.content.Intent;
//import android.support.v7.app.ActionBarActivity;
//import android.os.Bundle;
//import android.view.Menu;
//import android.view.MenuItem;
//import android.view.View;
//import android.view.inputmethod.InputMethodManager;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.Toast;
//
//
//public class MainActivityOld extends ActionBarActivity implements View.OnClickListener {
//
//    DBClass sqldb;
//
//    //UI Elements
//    EditText nameField, amountField, queryField;
//    Button insertBtn, viewBtn, queryBtn;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        sqldb = new DBClass(this);
//
//        //UI Elements
////        nameField = (EditText) findViewById(R.id.nameField);
////        amountField = (EditText) findViewById(R.id.passwordField);
////        queryField = (EditText) findViewById(R.id.queryField);
////
////        insertBtn = (Button) findViewById(R.id.insertBtn);
////        insertBtn.setOnClickListener(this);
////        viewBtn = (Button) findViewById(R.id.viewBtn);
////        viewBtn.setOnClickListener(this);
////        queryBtn = (Button) findViewById(R.id.queryBtn);
////        queryBtn.setOnClickListener(this);
//    }
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
//
//    private void insertFood(){
//        if (nameField.getText().toString().trim().equals(""))
//        {
//            nameField.requestFocus();
//            Toast.makeText(this, "You need to enter a name", Toast.LENGTH_SHORT).show();
//        }else if (amountField.getText().toString().trim().equals(""))
//        {
//            amountField.requestFocus();
//            Toast.makeText(this, "You need to enter a number", Toast.LENGTH_SHORT).show();
//        }else {
//            String name = nameField.getText().toString();
//            int amount = Integer.parseInt(amountField.getText().toString());
//
//            Toast.makeText(this, name + amount, Toast.LENGTH_SHORT).show();
//
//            long id = sqldb.insertData(name, amount);
//
//            if(id < 0){
//                Toast.makeText(this, "fail", Toast.LENGTH_SHORT).show();
//            }else{
//                Toast.makeText(this, "success", Toast.LENGTH_SHORT).show();
//            }
//            hideKeyboard();
//        }
//    }
//
//    private void getAllFood() {
//        //String data = sqldb.getData();
//
//        //start Food List Intent
//        Intent view = new Intent(this, FoodListActivity.class);
//        view.putExtra("term", "all");
//        startActivity(view);
//
//        //Toast.makeText(this, data, Toast.LENGTH_SHORT).show();
//    }
//
//    private void getFoodSearch(String term){
//        //String queryResults = sqldb.getSelectedData(term);
//
//        //start Food List Intent
//        //Intent view = new Intent(this, FoodListActivity.class);
//        //view.putExtra("term", term);
//        //startActivity(view);
//
//        String urlString = Constants.API_URL + "?key=" + Constants.API_KEY + "&q=" + term;
//        new Food2ForkAPI(this).execute(urlString);
//
//    }
//
//    private void hideKeyboard() {
//        // Check if no view has focus:
//        View view = this.getCurrentFocus();
//        if (view != null) {
//            InputMethodManager inputManager = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
//            inputManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
//        }
//    }
//
//
//    @Override
//    public void onClick(View v) {
//        int id = v.getId();
//
//        switch (id){
//            case R.id.insertBtn:
//                insertFood();
//                break;
//            case R.id.viewBtn:
//                getAllFood();
//                break;
//            case R.id.queryBtn:
//                getFoodSearch(queryField.getText().toString());
//                break;
//            default:
//                break;
//        }
//    }
//
//
//}
//
