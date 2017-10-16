package com.justcorrections.grit.map;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.justcorrections.grit.R;
import com.justcorrections.grit.map.FilterMenu.OnFilterUpdatedListener;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MapFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MapFragment extends Fragment {

    private FilterMenu filterMenu;

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


        filterMenu = view.findViewById(R.id.filter_menu);
        filterMenu.setItems(new String[]{"Food", "G.E.D"}, new boolean[]{false, true});
        filterMenu.setOnFilterChangedListener(new OnFilterUpdatedListener() {
            @Override
            public void onFilterUpdated(String title, boolean checked) {
                System.out.println("Ian t c " + title + " " + checked);
            }
        });

        return view;
    }

}
