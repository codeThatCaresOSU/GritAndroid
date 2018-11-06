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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.functions.FirebaseFunctions;
import com.google.firebase.functions.HttpsCallableResult;
import com.justcorrections.grit.MainActivity;
import com.justcorrections.grit.R;

import com.justcorrections.grit.data.model.FirebaseDataModel;
import com.justcorrections.grit.data.model.Resource;
import com.justcorrections.grit.data.remote.ResourcesDataSource;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.HttpUrl;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Andrew Davis on 12/9/2017.
 */

public class ResourceDetailPresenter {

    private ResourceDetailFragment resourceDetailFragment;

    private String mResourceID;
    private String resourceName;

    public ResourceDetailPresenter(ResourceDetailFragment fragment, String resourceID, String resourceName) {
        this.resourceDetailFragment = fragment;
        this.mResourceID = resourceID;
        this.resourceName = resourceName;
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
        FirebaseAuth auth = FirebaseAuth.getInstance();

        if (auth.getCurrentUser() == null) {
            Toast.makeText(resourceDetailFragment.getContext(), "Please Sign-in to save Resources", Toast.LENGTH_LONG).show();
            return;
        }

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users/" + auth.getCurrentUser().getUid() + "/savedResources/" + resourceName);
        ref.setValue("" + this.mResourceID);

//        OkHttpClient client = new OkHttpClient();
//
//        HttpUrl.Builder urlBuilder = HttpUrl.parse("https://us-central1-grit-f9d52.cloudfunctions.net/saveResource").newBuilder();
//        urlBuilder.addQueryParameter("uid", FirebaseAuth.getInstance().getCurrentUser().getUid());
//        urlBuilder.addQueryParameter("id", mResourceID);
//        String url = urlBuilder.build().toString();
//
//        final Request request = new Request.Builder()
//                .url(url)
//                .build();
//
//        client.newCall(request).enqueue(new Callback() {
//            @Override
//            public void onFailure(Request request, IOException e) {
//                e.printStackTrace();
//            }
//
//            @Override
//            public void onResponse(Response response) throws IOException {
//                Log.d("HTTPRESPONSE", response.message());
//            }
//        });

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
