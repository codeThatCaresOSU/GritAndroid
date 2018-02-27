package com.justcorrections.grit.modules.signup;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.justcorrections.grit.MainActivity;
import com.justcorrections.grit.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SignupNamesAge#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SignupGender extends Fragment {

    private int age;
    private String firstName, lastName, city, address, zip, bio, email, password, gender;
    private RadioButton maleRB, femaleRB, otherRB;
    private EditText otherEditText;

    public SignupGender() {}

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param signupInfo a bundle of the signup details.
     * @return A new instance of fragment SignupNamesAge.
     */
    public static SignupGender newInstance(Bundle signupInfo) {
        SignupGender fragment = new SignupGender();
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

        View view = inflater.inflate(R.layout.fragment_signup_gender, container, false);

        // find the views
        maleRB = view.findViewById(R.id.rb_gender_male);
        femaleRB = view.findViewById(R.id.rb_gender_female);
        otherRB = view.findViewById(R.id.rb_gender_other);
        Button backButton = view.findViewById(R.id.button_gender_back);
        Button nextButton = view.findViewById(R.id.button_gender_next);
        otherEditText = view.findViewById(R.id.et_other_gender);

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

        otherEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                otherRB.toggle();
            }
        });

        /*
         * Populate views with data if it already exists
         */
        if (gender != null && !gender.isEmpty()) {
            if (gender.equals(getString(R.string.male))) {
                maleRB.toggle();
            } else if (gender.equals(getString(R.string.female))) {
                femaleRB.toggle();
            } else if (gender.contains(getString(R.string.other_prefix))) {
                otherRB.toggle();
                otherEditText.setText(gender.substring(6));
            }
        }

        // Inflate the layout for this fragment
        return view;
    }

    /*
     * Navigates to the previous signup screen
     */
    private void goBack() {
        Bundle signUpBundle = createBundleFromThis();
        ((MainActivity) getActivity()).navigateTo(SignupNamesAge.newInstance(signUpBundle));
    }

    /*
     * Navigates to the next signup screen
     */
    private void goNext() {
        if (!validInput()) {
            Toast.makeText(getContext(), R.string.empty_fields_error, Toast.LENGTH_LONG).show();
        } else {
            Bundle signUpBundle = createBundleFromThis();
            ((MainActivity) getActivity()).navigateTo(SignupLocation.newInstance(signUpBundle));
        }

    }

    private boolean validInput() {
        return !otherEditText.getText().toString().isEmpty() || !otherRB.isChecked();
    }

    /*
     * Creates a Bundle with all of the signup information contained in this fragment's instance variables
     * and views.
     */
    private Bundle createBundleFromThis() {
        Bundle signupInfo = new Bundle();

        // Update instance variables based on user input
        if (maleRB.isChecked()) {
            gender = getString(R.string.male);
        } else if (femaleRB.isChecked()) {
            gender = getString(R.string.female);
        } else if (otherRB.isChecked()){
            gender = getString(R.string.other_prefix) + otherEditText.getText().toString();
        }

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
