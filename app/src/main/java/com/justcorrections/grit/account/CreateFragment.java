package com.justcorrections.grit.account;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.justcorrections.grit.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CreateFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CreateFragment extends Fragment implements AccountInterface {

    private static final String ARG_EMAIL = "arg_email";
    private String email;

    // Views
    private TextInputLayout userLayout;
    private TextInputLayout passLayout;
    private TextInputLayout checkLayout;
    private TextInputEditText userText;
    private TextInputEditText passText;
    private TextInputEditText checkText;
    private ImageView userIcon;
    private ImageView passIcon;
    private ImageView checkIcon;
    private ImageView logo;
    private Button loginButton;
    private Button backButton;

    public CreateFragment() {
        // Required empty public constructor
    }

    public static CreateFragment newInstance(String email) {
        CreateFragment fragment = new CreateFragment();
        Bundle args = new Bundle();
        args.putString(ARG_EMAIL, email);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.email = getArguments().getString(ARG_EMAIL);
            if (this.email == null) this.email = "";
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_create, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // initialize the views for the fragment
        userLayout = view.findViewById(R.id.create_user_layout);
        passLayout = view.findViewById(R.id.create_pass_layout);
        checkLayout = view.findViewById(R.id.create_check_layout);
        userText = view.findViewById(R.id.create_user_input);
        passText = view.findViewById(R.id.create_pass_input);
        checkText = view.findViewById(R.id.create_check_input);
        userIcon = view.findViewById(R.id.create_user_icon);
        passIcon = view.findViewById(R.id.create_pass_icon);
        checkIcon = view.findViewById(R.id.create_check_icon);
        logo = view.findViewById(R.id.create_logo);
        loginButton = view.findViewById(R.id.create_account_button);
        backButton = view.findViewById(R.id.create_registered_button);

        userText.setText(email);

        setButtonListeners();
    }

    private void setButtonListeners() {
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().popBackStack();
            }
        });
    }

    @Override
    public int getType() {
        return TYPE_CREATE;
    }
}
