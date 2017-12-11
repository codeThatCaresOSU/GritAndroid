package com.justcorrections.grit.resource_detail;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.justcorrections.grit.R;
import com.justcorrections.grit.data.resource.Resource;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ResourceDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ResourceDetailFragment extends Fragment {

    private static final String ARG_RESOURCE_ID = "arg_id";
    private String mResourceID;
    private ResourceDetailPresenter presenter;

    private TextView tvLocation, tvURL, tvName, tvPhone, tvCategory;

    public static ResourceDetailFragment newInstance(String resourceID) {
        ResourceDetailFragment fragment = new ResourceDetailFragment();
        Bundle args = new Bundle();
        args.putString(ARG_RESOURCE_ID, resourceID);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mResourceID = getArguments().getString(ARG_RESOURCE_ID);
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

        presenter = new ResourceDetailPresenter(this, mResourceID);
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

}
