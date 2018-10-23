package com.justcorrections.grit.modules.savedresources;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.justcorrections.grit.MainActivity;
import com.justcorrections.grit.R;
import com.justcorrections.grit.data.model.Resource;
import com.justcorrections.grit.data.remote.FirebaseDataSource;
import com.justcorrections.grit.data.remote.ResourcesDataSource;
import com.justcorrections.grit.modules.map.ResourceDetailFragment;

import java.util.ArrayList;
import java.util.List;

public class SavedResourcesFragment extends Fragment {

    private RecyclerView recyclerView;
    private List<Resource> resourceList;
    private SavedItemAdapter adapter;

    public SavedResourcesFragment() {
        // Required empty public constructor
    }

    public static SavedResourcesFragment newInstance() {
        SavedResourcesFragment fragment = new SavedResourcesFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        resourceList = new ArrayList<>();
        adapter = new SavedItemAdapter(resourceList, this);
        loadSavedResources();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_saved_resources, container, false);
        recyclerView = view.findViewById(R.id.rv_saved);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        loadSavedResources();


        return view;
    }

    public void itemOnClick(View view) {
        int itemPosition = recyclerView.getChildLayoutPosition(view);
        String resourceId = resourceList.get(itemPosition).getId();
        ((MainActivity) this.getActivity()).navigateTo(ResourceDetailFragment.newInstance(resourceId), true);
    }

    private void loadSavedResources() {

        // Load the saved resources into resourceList
        ResourcesDataSource.getInstance().getItems(new FirebaseDataSource.GetItemsCallback<Resource>() {
            @Override
            public void onItemsLoaded(List<Resource> items) {
                resourceList.addAll(items);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onDataNotAvailable() {
                Toast.makeText(getContext(), "Could not load resources", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
