package com.justcorrections.grit.modules.savedresources;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.justcorrections.grit.MainActivity;
import com.justcorrections.grit.R;
import com.justcorrections.grit.data.model.Resource;
import com.justcorrections.grit.data.remote.FirebaseDataSource;
import com.justcorrections.grit.data.remote.ResourcesDataSource;
import com.justcorrections.grit.modules.map.ResourceDetailFragment;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SavedResourcesFragment extends Fragment {

    private RecyclerView recyclerView;
    private List<Resource> resourceList;
    private SavedItemAdapter adapter;
    private Set<String> savedIds = new HashSet<>();

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

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) return;

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users/" + user.getUid() + "/savedResources");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                resourceList.clear();
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    savedIds.add(child.getValue().toString());
                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference("MapData/OhioData/" + child.getValue().toString());
                    ref.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot resourceData) {
                            Resource resource = resourceData.getValue(Resource.class);
                            if (!resourceList.contains(resource)) {
                                resourceList.add(resource);
                                adapter.notifyDataSetChanged();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
