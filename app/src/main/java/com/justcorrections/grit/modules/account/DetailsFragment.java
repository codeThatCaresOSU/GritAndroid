package com.justcorrections.grit.modules.account;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.justcorrections.grit.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetailsFragment extends Fragment implements AccountInterface {

    private static final String ARG_EMAIL = "arg_email";

    private OnAccountRequestListener createRequestListener;

    public DetailsFragment() {
        // Required empty public constructor
    }

    public static DetailsFragment newInstance(String email) {
        DetailsFragment fragment = new DetailsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_EMAIL, email);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_create, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        // Initialize the views here

        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public int getType() {
        return TYPE_DETAILS;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnAccountRequestListener) {
            createRequestListener = (OnAccountRequestListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnAccountRequestListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        createRequestListener = null;
    }
}
