package ca.hwlo.mrkook;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Henry on 2015-08-03.
 */
public class RecipeListAdapter extends ArrayAdapter<Recipe> {

    public RecipeListAdapter(Context context, ArrayList<Recipe> recipes){
        super(context, 0, recipes);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        //get the data item for this position
        final Recipe recipe = getItem(position);
        //check if an existing view is being reused, otherwise inflate the view
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_recipe, parent, false);
        }
        //lookup view for data population
        TextView titleText = (TextView) convertView.findViewById(R.id.recipeTitle);
        TextView timingText = (TextView) convertView.findViewById(R.id.timingText);

        ImageView recipePic = (ImageView) convertView.findViewById(R.id.recipePic);

        //populate the data into the template view using the data object
        titleText.setText(recipe.recipeTitle);

        int baseTime = 10;
        int randomTime = new Random().nextInt(60);
        timingText.setText((baseTime + randomTime) + " minutes");

        timingText.setTextColor(getContext().getResources().getColor(R.color.accent_material_light));

        //load the image
        new DownloadImageTask(recipePic).execute(recipe.recipeImageURL);

        //add the onclick listener to send to web browser
        convertView.setOnClickListener(new View.OnClickListener(){
            public void onClick(final View v){
                final Intent webIntent = new Intent(Intent.ACTION_VIEW);
                webIntent.setData(Uri.parse(recipe.recipeSourceURL));
                getContext().startActivity(webIntent);
            }
        });

        //return the completed view to render on screen
        return convertView;
    }

    //a separate task for downloading images
    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap>{
        ImageView imageView;

        public DownloadImageTask(ImageView imageView){
            this.imageView = imageView;
        }

        protected Bitmap doInBackground(String... urls){
            String url = urls[0];
            Bitmap pic = null;

            try{
                InputStream in = new java.net.URL(url).openStream();
                pic = BitmapFactory.decodeStream(in);
            }catch (Exception e){
                Log.e("Image Error", e.getMessage());
                e.printStackTrace();
            }
            return pic;
        }

        protected void onPostExecute(Bitmap result){
            imageView.setImageBitmap(result);
        }
    }
}
