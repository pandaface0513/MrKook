package ca.hwlo.mrkook;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Henry on 2015-08-04.
 */
public class GridFoodAdapter extends CursorAdapter {

    private LayoutInflater layoutInflater;

    public GridFoodAdapter(Context context, Cursor c, int flags){
        super(context, c, flags);
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return layoutInflater.inflate(R.layout.list_grid, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        //UI Elements
        TextView nameText = (TextView) view.findViewById(R.id.foodNameText);
        TextView amountText = (TextView) view.findViewById(R.id.foodAmount);

        ImageView imageView = (ImageView) view.findViewById(R.id.foodImageView);

        nameText.setText(cursor.getString(cursor.getColumnIndex(Constants.NAME)));
        amountText.setText(cursor.getString(cursor.getColumnIndex(Constants.AMOUNNT)));

        byte[] imageData = (byte[]) cursor.getBlob(cursor.getColumnIndex(Constants.IMAGE));
        if(imageData != null){
            Bitmap img = BitmapFactory.decodeByteArray(imageData, 0, imageData.length);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setImageBitmap(img);
        }

    }
}
