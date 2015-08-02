package ca.hwlo.mrkook;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DBClass {
    private SQLiteDatabase db;
    private Context context;
    private final SQLHelper helper;

    public DBClass(Context c){
        context = c;
        helper = new SQLHelper(context);
        db = helper.getWritableDatabase();
    }

    public long insertData(String name, int amount){
        ContentValues contentValues = new ContentValues();
        contentValues.put(Constants.NAME, name);
        contentValues.put(Constants.AMOUNNT, amount);
        long id = db.insert(Constants.TABLE_NAME, null, contentValues);
        return id;
    }

    public String getData(){
        String[] columns = {Constants.UID, Constants.NAME, Constants.AMOUNNT};
        Cursor cursor = db.query(Constants.TABLE_NAME, columns, null, null, null, null, null, null);

        StringBuffer buffer = new StringBuffer();

        while(cursor.moveToNext()){
            String name = cursor.getString(1);
            int amount = cursor.getInt(2);
            buffer.append(name + " x " + amount + "\n");
        }
        return buffer.toString();
    }

    public Cursor getAllCursor(){
        String[] columns = {Constants.UID, Constants.NAME, Constants.AMOUNNT};
        Cursor cursor = db.query(Constants.TABLE_NAME, columns, null, null, null, null, null, null);
        return cursor;
    }

    public Cursor getSelectedCursor(String term){
        String[] columns = {Constants.UID, Constants.NAME, Constants.AMOUNNT};
        String selection = Constants.NAME + "= '" + term + "'";
        Cursor cursor = db.query(Constants.TABLE_NAME, columns, selection, null, null, null, null, null);
        return cursor;
    }

    public String getSelectedData(String term){
        String[] columns = {Constants.UID, Constants.NAME, Constants.AMOUNNT};
        String selection = Constants.NAME + "= '" + term + "'";
        Cursor cursor = db.query(Constants.TABLE_NAME, columns, selection, null, null, null, null);

        StringBuffer buffer = new StringBuffer();

        while(cursor.moveToNext()){
            String name = cursor.getString(1);
            int amount = cursor.getInt(2);
            buffer.append(name + " x " + amount + "\n");
        }
        return buffer.toString();
    }

    public void updateEntry(long id, int amount){
        String strFilter = "_id=" + id;
        ContentValues args = new ContentValues();
        args.put("amount", amount);
        db.update(Constants.TABLE_NAME, args, strFilter, null);
    }

    public void deleteEntry(long id)         {
        String string =String.valueOf(id);
        db.execSQL("DELETE FROM " + Constants.TABLE_NAME + " WHERE _id = '" + string + "'");
      //if you just have key_name to select a row,you can ignore passing rowid(here-row) and use:

      //db.delete(Constants.TABLE_NAME, Constants.NAME + "=" + key_name, null);
    }
}
