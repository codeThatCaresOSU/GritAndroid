package com.justcorrections.grit.map;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.justcorrections.grit.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MapFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MapFragment extends Fragment {

    private View view;
    private FloatingActionButton filterMenuOpenButton;
    private PopupWindow filterMenu;

    public MapFragment() {
        // Required empty public constructor
    }

    public static MapFragment newInstance() {
        return new MapFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_map, container, false);

        filterMenuOpenButton = view.findViewById(R.id.filterMenuOpenButton);
        filterMenuOpenButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                onFilterMenuOpen();
            }
        });

        View filterMenuLayout = inflater.inflate(R.layout.map_filter_menu, container, false);
        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        boolean focusable = true; // lets taps outside the popup also dismiss it
        filterMenu = new PopupWindow(filterMenuLayout, width, height, focusable);
        filterMenu.setAnimationStyle(R.style.mapFilterMenuAnimation);

        return view;
    }

    public void onFilterMenuOpen() {
//        filterMenuOpenButton.setVisibility(View.GONE);
        filterMenu.showAtLocation(view, Gravity.CENTER, 0, 0);

        final float scale = getContext().getResources().getDisplayMetrics().density;
        int pixels = (int) (60 * scale + 0.5f);

        filterMenuOpenButton.animate().translationXBy(-pixels).setDuration(100);

    }
}
