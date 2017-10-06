package com.justcorrections.grit.map;

import android.content.DialogInterface;
import android.content.DialogInterface.OnMultiChoiceClickListener;
import android.content.DialogInterface.OnShowListener;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import com.justcorrections.grit.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MapFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MapFragment extends Fragment {

    private FloatingActionButton filterMenuOpenButton;

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

        filterMenuOpenButton = view.findViewById(R.id.filterMenuOpenButton);
        filterMenuOpenButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                onFilterMenuOpen();
            }
        });

        return view;
    }

    public void onFilterMenuOpen() {

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setCancelable(false)
                .setTitle("The title");
        String[] choices = {"One", "Two", "Three"};
        boolean[] checked = {false, true, false};

        builder.setMultiChoiceItems(choices, checked, new OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int index, boolean b) {
            }
        });

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Do something when click the negative button
            }
        });

        builder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Do something when click the neutral button
            }
        });

        final AlertDialog dialog = builder.create();

        final int darkPink = ContextCompat.getColor(getContext(), R.color.darkPink);

        dialog.setOnShowListener(new OnShowListener() {
            @Override
            public void onShow(DialogInterface d) {
                dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(darkPink);
                dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(darkPink);
            }
        });

        dialog.show();

    }
}
