package com.justcorrections.grit.map;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import com.crashlytics.android.Crashlytics;
import com.justcorrections.grit.MainActivity;
import com.justcorrections.grit.R;
import com.justcorrections.grit.data.resource.Resource;
import com.justcorrections.grit.data.resource.ResourcesDataSource;

import java.io.InputStream;

/**
 * Created by Andrew Davis on 12/9/2017.
 */

public class ResourceDetailPresenter {

    private ResourceDetailFragment resourceDetailFragment;

    private String mResourceID;

    public ResourceDetailPresenter(ResourceDetailFragment fragment, String resourceID) {
        this.resourceDetailFragment = fragment;
        this.mResourceID = resourceID;
    }

    public void start() {
        loadResource();
    }

    private void loadResource() {

        ResourcesDataSource.getInstance().getResource(Integer.parseInt(mResourceID), new ResourcesDataSource.GetResourceCallback() {

            @Override
            public void onResourceLoaded(Resource resource) {
                resourceDetailFragment.populateViewsWithResourceDetails(resource);
                new DownloadStreetviewImageTask().execute(resource.getAddress());
            }

            @Override
            public void onDataNotAvailable() {
                resourceDetailFragment.mainActivity.showErrorText("Details could not be loaded. Please check your connection and try again later.");
            }
        });
    }

    private class DownloadStreetviewImageTask extends AsyncTask<String, Void, Bitmap> {

        public DownloadStreetviewImageTask() {
        }

        protected Bitmap doInBackground(String... addresses) {
            Bitmap downloadedImage = null;

            // Construct the streetview api string.
            StringBuilder urlBuilder = new StringBuilder();
            urlBuilder.append("https://maps.googleapis.com/maps/api/streetview");
            urlBuilder.append("?size=600x300");
            urlBuilder.append("&location=\"").append(addresses[0]).append("\"");
            urlBuilder.append("&key=")
                    .append(resourceDetailFragment.getContext().getString(R.string.google_street_view_api_key));

            try {
                InputStream in = new java.net.URL(urlBuilder.toString()).openStream();
                downloadedImage = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                resourceDetailFragment.mainActivity.showErrorText("Google Streetview Image could not be loaded. Please check your connection and try again later.");
                e.printStackTrace();
            }

            return downloadedImage;
        }

        protected void onPostExecute(Bitmap result) {
            resourceDetailFragment.updateStreetViewImage(result);
        }
    }

}
