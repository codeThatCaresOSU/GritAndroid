package com.justcorrections.grit.modules.map;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.functions.FirebaseFunctions;
import com.google.firebase.functions.HttpsCallableResult;
import com.justcorrections.grit.MainActivity;
import com.justcorrections.grit.R;

import com.justcorrections.grit.data.model.Resource;
import com.justcorrections.grit.data.remote.ResourcesDataSource;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

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

        ResourcesDataSource.getInstance().getItem(mResourceID, new ResourcesDataSource.GetItemCallback<Resource>() {

            @Override
            public void onItemLoaded(Resource resource) {
                resourceDetailFragment.populateViewsWithResourceDetails(resource);
                // TODO new DownloadStreetviewImageTask().execute(resource.getAddress());
            }

            @Override
            public void onDataNotAvailable() {
                resourceDetailFragment.mainActivity.showErrorText("Details could not be loaded. Please check your connection and try again later.");
            }
        });
    }

    public void saveResource() {
        FirebaseFunctions functions = FirebaseFunctions.getInstance();
        FirebaseAuth auth = FirebaseAuth.getInstance();

        if (auth.getCurrentUser() == null) {
            Toast.makeText(resourceDetailFragment.getContext(), "Please Sign-in to save Resources", Toast.LENGTH_LONG).show();
            return;
        }

        Map<String, Object> data = new HashMap<>();
        data.put("uid", FirebaseAuth.getInstance().getCurrentUser().getUid());
        data.put("resourceid", mResourceID);

        functions.getHttpsCallable("saveResource")
                .call(data)
                .addOnSuccessListener(new OnSuccessListener<HttpsCallableResult>() {
                    @Override
                    public void onSuccess(HttpsCallableResult httpsCallableResult) {
                        Toast.makeText(resourceDetailFragment.getContext(), "Successfully Saved", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(resourceDetailFragment.getContext(), "Failed to save: " + e.getMessage(), Toast.LENGTH_LONG).show();
                        e.printStackTrace();
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
                in.close();
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                resourceDetailFragment.mainActivity.showErrorText("Google Streetview Image could not be loaded. Please check your connection and try again later.");
                e.printStackTrace();
                return BitmapFactory.decodeResource(ResourceDetailPresenter.this.resourceDetailFragment.getResources(), R.drawable.default_streetview_image);
            }

            return downloadedImage;
        }

        protected void onPostExecute(Bitmap result) {
            resourceDetailFragment.updateStreetViewImage(result);
        }
    }

}
