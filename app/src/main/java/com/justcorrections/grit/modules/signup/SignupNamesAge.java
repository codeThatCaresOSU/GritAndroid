package com.justcorrections.grit.modules.signup;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.justcorrections.grit.MainActivity;
import com.justcorrections.grit.R;
import com.justcorrections.grit.modules.signin.SigninFragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SignupNamesAge#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SignupNamesAge extends Fragment {

    private int age;
    private String firstName, lastName, city, address, zip, bio, email, password, gender;
    private EditText firstNameEditText, lastNameEditText, ageEditText;

    public SignupNamesAge() {
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param signupInfo a bundle of the signup details.
     * @return A new instance of fragment SignupNamesAge.
     */
    public static SignupNamesAge newInstance(Bundle signupInfo) {
        SignupNamesAge fragment = new SignupNamesAge();
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

        View view = inflater.inflate(R.layout.fragment_signup_names_age, container, false);

        // find the views
        firstNameEditText = view.findViewById(R.id.et_first_name);
        lastNameEditText = view.findViewById(R.id.et_last_name);
        ageEditText = view.findViewById(R.id.et_age);
        Button nextButton = view.findViewById(R.id.button_names_age_next);
        Button backButton = view.findViewById(R.id.button_names_age_back);

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goNext();
            }
        });
        backButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                goBack();
            }
        });

        /*
         * Populate views with data if it already exists
         */
        if (firstName != null && !firstName.isEmpty()) {
            firstNameEditText.setText(firstName);
        }

        if (lastName != null && !lastName.isEmpty()) {
            lastNameEditText.setText(lastName);
        }

        if (age != 0) {
            ageEditText.setText(String.valueOf(age));
        }

        // Inflate the layout for this fragment
        return view;
    }

    /*
     * Navigates to the previous signup screen
     */
    private void goBack() {
        ((MainActivity) getActivity()).navigateTo(SigninFragment.newInstance(), true);
    }

    /*
     * Navigates to the next signup screen
     */
    private void goNext() {
        if (!noEmptyFields()) {
            Toast.makeText(getContext(), R.string.empty_fields_error, Toast.LENGTH_LONG).show();
        } else if (!ageOver18()) {
            Toast.makeText(getContext(), R.string.over_18_error, Toast.LENGTH_LONG).show();
        } else {
            Bundle signUpBundle = createBundleFromThis();
            ((MainActivity) getActivity()).navigateTo(SignupGender.newInstance(signUpBundle), true);
        }

    }

    private boolean noEmptyFields() {
        return !ageEditText.getText().toString().isEmpty()
                && !lastNameEditText.getText().toString().isEmpty()
                && !firstNameEditText.getText().toString().isEmpty();
    }

    private boolean ageOver18() {
        return Integer.parseInt(ageEditText.getText().toString()) >= 18;
    }

    /*
     * Creates a Bundle with all of the signup information contained in this fragment's instance variables
     * and views.
     */
    private Bundle createBundleFromThis() {
        Bundle signupInfo = new Bundle();

        // Update instance variables based on user input
        try {
            this.age = Integer.parseInt(this.ageEditText.getText().toString());
        } catch (NumberFormatException e) {
            this.age = 0;
        }
        this.lastName = this.lastNameEditText.getText().toString();
        this.firstName = this.firstNameEditText.getText().toString();

        // Put all the info in the bundle
        signupInfo.putInt(getString(R.string.age), age);
        signupInfo.putString(getString(R.string.first_name), firstName);
        signupInfo.putString(getString(R.string.last_name), lastName);
        signupInfo.putString(getString(R.string.email), email);
        signupInfo.putString(getString(R.string.city), city);
        signupInfo.putString(getString(R.string.street_address), address);
        signupInfo.putString(getString(R.string.zip), zip);
        signupInfo.putString(getString(R.string.bio), bio);
        signupInfo.putString(getString(R.string.password), password);
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
            password = getArguments().getString(getString(R.string.password), "");
            age = getArguments().getInt(getString(R.string.age), 0);
            gender = getArguments().getString(getString(R.string.gender), "");
        }
    }
}
