package com.justcorrections.grit.modules.account;

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
 * Use the {@link ResetFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ResetFragment extends Fragment implements AccountInterface {

    private static final String ARG_EMAIL = "arg_email";
    private String email;

    private OnAccountRequestListener resetRequestListener;

    // Views
    private TextInputLayout userLayout;
    private TextInputEditText userText;
    private ImageView userIcon;
    private ImageView logo;
    private Button resetButton;
    private Button backButton;

    public ResetFragment() {
        // Required empty public constructor
    }

    public static ResetFragment newInstance(String email) {
        ResetFragment fragment = new ResetFragment();
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
        return inflater.inflate(R.layout.fragment_reset, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // initialize the views for the fragment
        userLayout = view.findViewById(R.id.reset_user_layout);
        userText = view.findViewById(R.id.reset_user_input);
        userIcon = view.findViewById(R.id.reset_user_icon);
        logo = view.findViewById(R.id.reset_logo);
        resetButton = view.findViewById(R.id.reset_login_button);
        backButton = view.findViewById(R.id.reset_back_log_in_button);

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

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isValidEmail()) {
                    resetRequestListener.onResetRequest(userText.getText().toString());
                } else {
                    resetRequestListener.onFail("Not a valid email");
                }
            }
        });
    }

    private boolean isValidEmail() {
        return !userText.getText().toString().isEmpty() &&
                Patterns.EMAIL_ADDRESS.matcher(userText.getText().toString()).matches();
    }

    @Override
    public int getType() {
        return TYPE_RESET;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnAccountRequestListener) {
            resetRequestListener = (OnAccountRequestListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnAccountRequestListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        resetRequestListener = null;
    }
}
