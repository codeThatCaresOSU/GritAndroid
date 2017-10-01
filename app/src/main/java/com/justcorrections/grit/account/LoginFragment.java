package com.justcorrections.grit.account;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.justcorrections.grit.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LoginFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoginFragment extends Fragment implements AccountInterface {

    private static final String ARG_EMAIL = "arg_email";
    // interface
    OnAccountRequestListener loginRequestListener;
    private String email;
    private String password;
    // Views
    private TextInputLayout userLayout;
    private TextInputLayout passLayout;
    private TextInputEditText userText;
    private TextInputEditText passText;
    private ImageView userIcon;
    private ImageView passIcon;
    private ImageView logo;
    private Button loginButton;
    private Button forgotButton;
    private Button createButton;

    public LoginFragment() {
        // Required empty public constructor
    }

    public static LoginFragment newInstance(String email) {
        LoginFragment fragment = new LoginFragment();
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
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // initialize the views for the fragment
        userLayout = view.findViewById(R.id.login_user_layout);
        passLayout = view.findViewById(R.id.login_pass_layout);
        userText = view.findViewById(R.id.login_user_input);
        passText = view.findViewById(R.id.login_pass_input);
        userIcon = view.findViewById(R.id.login_user_icon);
        passIcon = view.findViewById(R.id.login_pass_icon);
        logo = view.findViewById(R.id.login_logo);
        loginButton = view.findViewById(R.id.login_login_button);
        forgotButton = view.findViewById(R.id.login_forgot_password_button);
        createButton = view.findViewById(R.id.login_signup_button);

        userText.setText(email);

        setButtonListeners();
    }

    private boolean isValidEmail(String email) {
        return !email.isEmpty() &&
                Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private void setButtonListeners() {
        forgotButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_fragment_holder, ResetFragment.newInstance(userText.getText().toString()))
                        .addToBackStack(null)
                        .commit();
            }
        });

        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_fragment_holder, CreateFragment.newInstance(userText.getText().toString()))
                        .addToBackStack(null)
                        .commit();
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email = userText.getText().toString();
                password = passText.getText().toString();
                if (isValidEmail(email)) {
                    loginRequestListener.onLoginRequest(email, password);
                } else {
                    loginRequestListener.onFail("Not a valid email address");
                }
            }
        });
    }

    @Override
    public int getType() {
        return TYPE_LOGIN;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnAccountRequestListener) {
            loginRequestListener = (OnAccountRequestListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnAccountRequestListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        loginRequestListener = null;
    }
}
