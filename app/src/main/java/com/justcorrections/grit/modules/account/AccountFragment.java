package com.justcorrections.grit.modules.account;

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

public class AccountFragment extends Fragment {

    private TextInputLayout userLayout;
    private TextInputLayout passLayout;
    private TextInputLayout checkLayout;
    private TextInputEditText userText;
    private TextInputEditText passText;
    private TextInputEditText checkText;
    private ImageView userIcon;
    private ImageView passIcon;
    private ImageView checkIcon;
    private Button createButton;
    private Button backButton;

    public static AccountFragment newInstance() {
        return new AccountFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_create, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        userLayout = view.findViewById(R.id.account_email_layout);
        passLayout = view.findViewById(R.id.account_password_layout);
        checkLayout = view.findViewById(R.id.account_password_confirm_layout);
        userText = view.findViewById(R.id.account_email_input);
        passText = view.findViewById(R.id.account_password_input);
        checkText = view.findViewById(R.id.account_password_confirm_input);
        userIcon = view.findViewById(R.id.account_email_icon);
        passIcon = view.findViewById(R.id.account_password_icon);
        checkIcon = view.findViewById(R.id.account_password_confirm_icon);
        createButton = view.findViewById(R.id.account_create_button);
        backButton = view.findViewById(R.id.create_registered_button);
    }
}
