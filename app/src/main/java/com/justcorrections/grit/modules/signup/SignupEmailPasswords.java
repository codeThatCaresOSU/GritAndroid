package com.justcorrections.grit.modules.signup;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.justcorrections.grit.modules.homepage.HomepageActivity;
import com.justcorrections.grit.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SignupNamesAge#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SignupEmailPasswords extends Fragment {


    private int age;
    private String firstName, lastName, city, address, zip, bio, email, password, gender;
    private EditText emailEditText, passwordEditText, confirmPasswordEditText;

    public SignupEmailPasswords() {
    }

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
        readFromArguments();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
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
        if (email != null && !email.isEmpty()) {
            emailEditText.setText(email);
        }
        if (password != null && !password.isEmpty()) {
            passwordEditText.setText(password);
            confirmPasswordEditText.setText(password);
        }

        // Inflate the layout for this fragment
        return view;
    }

    /*
     * Navigates to the previous signup screen
     */
    private void goBack() {

        Bundle signUpBundle = createBundleFromThis();
        ((HomepageActivity) getActivity()).navigateTo(SignupBio.newInstance(signUpBundle), true);
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
            ((HomepageActivity) getActivity()).navigateTo(SignupConfirm.newInstance(signUpBundle), true);
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
        email = emailEditText.getText().toString();
        password = passwordEditText.getText().toString();

        // Put all the info in the bundle
        signupInfo.putInt(getString(R.string.age), age);
        signupInfo.putString(getString(R.string.first_name), firstName);
        signupInfo.putString(getString(R.string.last_name), lastName);
        signupInfo.putString(getString(R.string.email), email);
        signupInfo.putString(getString(R.string.city), city);
        signupInfo.putString(getString(R.string.street_address), address);
        signupInfo.putString(getString(R.string.zip), zip);
        signupInfo.putString(getString(R.string.bio), bio);
        signupInfo.putString("Password", password);
        signupInfo.putString(getString(R.string.gender), gender);

        // return the bundle
        return signupInfo;
    }

    /*
     * updates instance variables to match the information contained in the given bundle.
     */
    private void readFromArguments() {
        if (getArguments() != null) {
            firstName = getArguments().getString(getString(R.string.first_name), "");
            lastName = getArguments().getString(getString(R.string.last_name), "");
            city = getArguments().getString(getString(R.string.city), "");
            address = getArguments().getString(getString(R.string.street_address), "");
            zip = getArguments().getString(getString(R.string.zip), "");
            bio = getArguments().getString(getString(R.string.bio), "");
            email = getArguments().getString(getString(R.string.email), "");
            password = getArguments().getString("Password", "");
            age = getArguments().getInt(getString(R.string.age), 0);
            gender = getArguments().getString(getString(R.string.gender), "");
        }
    }
}
