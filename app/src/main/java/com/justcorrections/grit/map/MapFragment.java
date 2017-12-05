package com.justcorrections.grit.map;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnMultiChoiceClickListener;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.justcorrections.grit.R;
import com.justcorrections.grit.data.category.Category;
import com.justcorrections.grit.data.resource.Resource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.justcorrections.grit.utils.GoogleMapUtils.hue;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MapFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MapFragment extends Fragment implements OnClickListener{

    private MapPresenter presenter;

    private GoogleMap googleMap; // displays resources
    private FloatingActionButton filterOpenButton; // opens filter menu, disabled when resources are loading
    private ProgressBar indeterminateProgressBar; // visible until resource data loads

    private Map<String, List<Marker>> mapMarkers; // maps: a category's name -> the markers that fall under said category

    public static MapFragment newInstance() {
        return new MapFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, container, false);

        presenter = new MapPresenter(this);

        SupportMapFragment map = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        map.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                MapFragment.this.googleMap = googleMap;
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(40.014190, -83.030914), 4f));
            }
        });

        filterOpenButton = view.findViewById(R.id.map_filter_open_button);
        filterOpenButton.setOnClickListener(this);
        filterOpenButton.setEnabled(false); // disabled until resources are loaded

        mapMarkers = new HashMap<>();

        indeterminateProgressBar = view.findViewById(R.id.map_progress_bar);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.start();
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == filterOpenButton.getId()) {
            presenter.onFilterButtonPressed();
        }
    }

    public void openFilterMenu(final String[] categories, final boolean[] selected) {
        new AlertDialog.Builder(getContext())
                .setMultiChoiceItems(categories, selected, new OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                        selected[which] = isChecked;
                    }
                })
                .setPositiveButton("APPLY", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        presenter.onFilterChanged(categories, selected);
                    }
                })
                .setNeutralButton("CANCEL", null)
                .setCancelable(true)
                .setTitle("Bus Filter")
                .create()
                .show();
    }

    public void setMarkers(Category category, List<Resource> resources) {
        List<Marker> markers = new ArrayList<>();
        for (Resource r : resources) {
            LatLng latLng = new LatLng(r.getLat(), r.getLng());
            MarkerOptions markerOptions = new MarkerOptions().position(latLng)
                    .title(r.getName()).icon(BitmapDescriptorFactory.defaultMarker(hue(category.getColor())));
            Marker m = googleMap.addMarker(markerOptions);
            markers.add(m);
        }
        mapMarkers.put(category.getName(), markers);
    }

    public void removeMarkers(String category) {
        if (mapMarkers.containsKey(category)) {
            for (Marker m : mapMarkers.get(category))
                m.remove();

            mapMarkers.get(category).clear();
        }
    }


    public void showProgressBar() {
        indeterminateProgressBar.setVisibility(View.VISIBLE);
        filterOpenButton.setEnabled(false);
    }
    
    public void hideProgressBar() {
        indeterminateProgressBar.setVisibility(View.GONE);
        filterOpenButton.setEnabled(true);
    }

}