package com.justcorrections.grit.map;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Toast;

import com.justcorrections.grit.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MapFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MapFragment extends Fragment {

    private FloatingActionButton filterMenuOpenButton;
    private CardView filterMenuContent;

    public MapFragment() {
        // Required empty public constructor
    }

    public static MapFragment newInstance() {
        return new MapFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, container, false);

//        Fragment fragment = getChildFragmentManager().findFragmentById(R.id.map);
//        SupportMapFragment supportmapfragment = (SupportMapFragment) fragment;
//        supportmapfragment.getMapAsync(new OnMapReadyCallback() {
//            @Override
//            public void onMapReady(GoogleMap googleMap) {
//                if (ActivityCompat.checkSelfPermission(getContext(), permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
//                        && ActivityCompat.checkSelfPermission(getContext(), permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//                    return;
//                }
//                googleMap.setMyLocationEnabled(true);
//            }
//        });

        filterMenuOpenButton = view.findViewById(R.id.filterMenuOpenButton);
        filterMenuContent = view.findViewById(R.id.filterMenuContent);

        filterMenuOpenButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                onFilterButtonOpen(view);
            }
        });


        return view;
    }

    public void onFilterButtonOpen(View view) {
        Toast.makeText(getActivity(), "This is my Toast message!",
                Toast.LENGTH_LONG).show();
        filterMenuOpenButton.setVisibility(View.INVISIBLE);
        filterMenuContent.setVisibility(View.VISIBLE);


    }
}
