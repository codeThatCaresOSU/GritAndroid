package com.justcorrections.grit.modules.map;

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

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.justcorrections.grit.MainActivity;
import com.justcorrections.grit.R;
import com.justcorrections.grit.data.model.Category;
import com.justcorrections.grit.data.model.Resource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.justcorrections.grit.utils.GoogleMapUtils.hue;

public class MapFragment extends Fragment implements OnClickListener, OnMapReadyCallback, GoogleMap.OnInfoWindowClickListener {

    private MapPresenter presenter;

    private GoogleMap googleMap; // displays resources
    private FloatingActionButton filterOpenButton; // opens filter menu, disabled when resources are loading
    private ProgressBar progressBar; // visible until resource data loads

    private Map<String, List<Marker>> mapMarkers; // maps: a category's name -> the markers that fall under said category
    private Map<String, String> resourceIds; // maps: a marker's id -> the id of the resource it represents

    public static MapFragment newInstance() {
        return new MapFragment();
    }

    public MapFragment() {
        presenter = new MapPresenter();
        mapMarkers = new HashMap<String, List<Marker>>();
        resourceIds = new HashMap<String, String>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, container, false);

        SupportMapFragment map = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        map.getMapAsync(this);

        filterOpenButton = view.findViewById(R.id.map_filter_open_button);
        filterOpenButton.setOnClickListener(this);
        filterOpenButton.setEnabled(false); // disabled until resources are loaded

        progressBar = view.findViewById(R.id.map_progress_bar);

        return view;
    }

    /*
     * On click listener for filter button
     */
    @Override
    public void onClick(View v) {
        if (v.getId() == filterOpenButton.getId()) {
            presenter.onFilterButtonPressed();
        }
    }

    /*
     * Callback for get map async
     * Lets us know when google map is ready to be used
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(40.014190, -83.030914), 6f));
        googleMap.setOnInfoWindowClickListener(this);
        presenter.start(this); // start presenter when map is loaded
    }

    @Override
    public void onPause() {
        super.onPause();
        presenter.pause();
    }

    /*
     * Filter button has been pressed, create and open the filter menu
     */
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
                .setTitle("Resource Filter")
                .create()
                .show();
    }

    /*
     * User has selected a category in the filter menu, add the resources to the map
     */
    public void setMarkers(Category category, List<Resource> resources) {
        List<Marker> markers = new ArrayList<>();
        for (Resource r : resources) {
            LatLng latLng = new LatLng(r.getLat(), r.getLng());
            MarkerOptions markerOptions = new MarkerOptions().position(latLng)
                    .title(r.getCategory() + ": " + r.getName()).icon(BitmapDescriptorFactory.defaultMarker(hue(category.getColor())));
            Marker m = googleMap.addMarker(markerOptions);
            markers.add(m);
            resourceIds.put(m.getId(), r.getId());
        }
        mapMarkers.put(category.getName(), markers);
        zoomToFitMarkers();
    }

    /*
     * User has deselected a category in the filter menu, remove the resources from the map
     */
    public void removeMarkers(String category) {
        if (mapMarkers.containsKey(category)) {
            for (Marker m : mapMarkers.get(category))
                m.remove();

            mapMarkers.get(category).clear();
        }
    }

    /*
     * New markers have been added to the map, zoom to fit to ensure user can see new markers
     */
    private void zoomToFitMarkers() {
        LatLngBounds.Builder builder = new LatLngBounds.Builder();

        for (List<Marker> markers : mapMarkers.values())
            for (Marker marker : markers)
                builder.include(marker.getPosition());

        LatLngBounds bounds = builder.build();
        CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, 300);
        googleMap.animateCamera(cu);
    }

    /*
     * Presenter is loading data
     * Disable the filter menu and showing loading bar
     */
    public void onFilterDataLoading() {
        progressBar.setVisibility(View.VISIBLE);
        filterOpenButton.setEnabled(false);
    }

    /*
     * Presenter has loaded the data
     * Enable the filter menu and hide loading bar
     */
    public void onFilterDataLoaded() {
        progressBar.setVisibility(View.GONE);
        filterOpenButton.setEnabled(true);
    }

    /*
     * On click listener for marker info windows
     * Starts resource detail fragment to display more info about
     * the resource that was clicked
     */
    @Override
    public void onInfoWindowClick(Marker marker) {
        String markerId = marker.getId();
        String resourceId = resourceIds.get(markerId);

        ResourceDetailFragment resourceDetailFragment = ResourceDetailFragment.newInstance(resourceId);
        ((MainActivity) getActivity()).navigateTo(resourceDetailFragment);
    }
}