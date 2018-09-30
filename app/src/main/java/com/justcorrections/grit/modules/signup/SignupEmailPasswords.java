package com.justcorrections.grit.modules.signup;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.justcorrections.grit.MainActivity;
import com.justcorrections.grit.R;
import com.justcorrections.grit.data.model.GritUser;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SignupNamesAge#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SignupEmailPasswords extends Fragment {


    private GritUser user;
    private EditText emailEditText, passwordEditText, confirmPasswordEditText;

    public SignupEmailPasswords() {}

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param signupInfo a bundle of the signup details.
     * @return A new instance of fragment SignupEmailPasswords.
     */
    public static SignupEmailPasswords newInstance(Bundle signupInfo) {
        SignupEmailPasswords fragment = new SignupEmailPasswords();
        fragment.setArguments(signupInfo);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.user = GritUser.readFromBundle(getArguments());
        } else {
            this.user = new GritUser();
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_signup_email_passwords, container, false);

        // find the views
        Button backButton = view.findViewById(R.id.button_email_password_back);
        Button nextButton = view.findViewById(R.id.button_email_password_next);
        emailEditText = view.findViewById(R.id.et_email);
        passwordEditText = view.findViewById(R.id.et_password);
        confirmPasswordEditText = view.findViewById(R.id.et_password_confirm);

        // Set on click listeners
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goBack();
            }
        });
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goNext();
            }
        });

        /*
         * Populate views with data if it already exists
         */
        if (user.getValue(GritUser.EMAIL_KEY) != null && !user.getValue(GritUser.EMAIL_KEY).isEmpty()) {
            emailEditText.setText(user.getValue(GritUser.EMAIL_KEY));
        }
        if (user.getValue(GritUser.PASSWORD_KEY) != null && !user.getValue(GritUser.PASSWORD_KEY).isEmpty()) {
            passwordEditText.setText(user.getValue(GritUser.PASSWORD_KEY));
            confirmPasswordEditText.setText(user.getValue(GritUser.PASSWORD_KEY));
        }

        // Inflate the layout for this fragment
        return view;
    }

    /*
     * Navigates to the previous signup screen
     */
    private void goBack() {

        Bundle signUpBundle = createBundleFromThis();
        ((MainActivity) getActivity()).navigateTo(SignupBio.newInstance(signUpBundle), true);
    }

    /*
     * Navigates to the next signup screen
     */
    private void goNext() {
        if (!noEmptyFields()) {
            Toast.makeText(getContext(), R.string.empty_fields_error, Toast.LENGTH_LONG).show();
        } else if (!passwordsMatch()) {
            Toast.makeText(getContext(), R.string.password_match_error, Toast.LENGTH_LONG).show();
        } else {
            Bundle signUpBundle = createBundleFromThis();
            ((MainActivity) getActivity()).navigateTo(SignupConfirm.newInstance(signUpBundle), true);
        }
    }

    private boolean noEmptyFields() {
        return !passwordEditText.getText().toString().isEmpty()
                && !emailEditText.getText().toString().isEmpty();
    }

    private boolean passwordsMatch() {
        return passwordEditText.getText().toString()
                .equals(confirmPasswordEditText.getText().toString());
    }

    /*
     * Creates a Bundle with all of the signup information contained in this fragment's instance variables
     * and views.
     */
    private Bundle createBundleFromThis() {
        Bundle signupInfo = new Bundle();

        // Update instance variables based on user input
        user.setValue(GritUser.EMAIL_KEY, emailEditText.getText().toString());
        user.setValue(GritUser.PASSWORD_KEY, passwordEditText.getText().toString());

        // return the bundle
        return GritUser.writeToBundle(user);
    }
}
