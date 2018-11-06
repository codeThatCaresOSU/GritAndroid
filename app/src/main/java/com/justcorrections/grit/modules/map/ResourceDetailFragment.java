package com.justcorrections.grit.modules.map;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.justcorrections.grit.MainActivity;
import com.justcorrections.grit.R;
import com.justcorrections.grit.data.model.Resource;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ResourceDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ResourceDetailFragment extends Fragment {

    private static final String ARG_RESOURCE_ID = "arg_id";
    private static final String ARG_RESOURCE_NAME = "arg_name";
    private String mResourceID;
    private String mResourceName;
    private ResourceDetailPresenter presenter;
    public MainActivity mainActivity;

    private TextView tvLocation, tvURL, tvName, tvPhone, tvCategory;
    private ImageView ivStreetviewPic;
    private Button buttonSaveResource;

    public static ResourceDetailFragment newInstance(String resourceID, String resourceName) {
        ResourceDetailFragment fragment = new ResourceDetailFragment();
        Bundle args = new Bundle();
        args.putString(ARG_RESOURCE_ID, resourceID);
        args.putString(ARG_RESOURCE_NAME, resourceName);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mResourceID = getArguments().getString(ARG_RESOURCE_ID);
            mResourceName = getArguments().getString(ARG_RESOURCE_NAME);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_resource_detail, container, false);

        tvLocation = view.findViewById(R.id.tv_location);
        tvPhone = view.findViewById(R.id.tv_phone);
        tvURL = view.findViewById(R.id.tv_url);
        tvName = view.findViewById(R.id.tv_name);
        tvCategory = view.findViewById(R.id.tv_category);
        ivStreetviewPic = view.findViewById(R.id.iv_streetview);
        buttonSaveResource = view.findViewById(R.id.button_save_resource);

        buttonSaveResource.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.saveResource();
            }
        });

        mainActivity = (MainActivity) getActivity();
        mainActivity.hideErrorText();

        presenter = new ResourceDetailPresenter(this, mResourceID, mResourceName);
        presenter.start();

        return view;
    }

    public void populateViewsWithResourceDetails(Resource resource) {

        tvURL.setText(resource.getUrl());
        tvName.setText(resource.getName());
        tvPhone.setText(resource.getPhone());
        tvCategory.setText(resource.getCategory());

        String addressString = resource.getAddress() + "\n" + resource.getCity() + ", " + resource.getState() + " " + resource.getZip();
        tvLocation.setText(addressString);

    }

    public void updateStreetViewImage(Bitmap image) {
        ivStreetviewPic.setImageBitmap(image);
    }

}
