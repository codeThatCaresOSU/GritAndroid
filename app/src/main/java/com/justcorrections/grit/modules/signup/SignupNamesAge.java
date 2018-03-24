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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SignupNamesAge#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SignupNamesAge extends Fragment {

    private SignupInfo signupInfo;

    private EditText firstNameEditText, lastNameEditText, ageEditText;

    public SignupNamesAge() {
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param bundle a bundle of the signup details.
     * @return A new instance of fragment SignupNamesAge.
     */
    public static SignupNamesAge newInstance(Bundle bundle) {
        SignupNamesAge fragment = new SignupNamesAge();
        fragment.setArguments(bundle);
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

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goNext();
            }
        });

        /*
         * Populate views with data if it already exists
         */
        if (signupInfo.firstName != null && !signupInfo.firstName.isEmpty()) {
            firstNameEditText.setText(signupInfo.firstName);
        }

        if (signupInfo.lastName != null && !signupInfo.lastName.isEmpty()) {
            lastNameEditText.setText(signupInfo.lastName);
        }

        if (signupInfo.age != 0) {
            ageEditText.setText(String.valueOf(signupInfo.age));
        }

        // Inflate the layout for this fragment
        return view;
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
            ((MainActivity) getActivity()).navigateTo(SignupMentorMentee.newInstance(signUpBundle));
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

        // Update instance variables based on user input
        try {
            signupInfo.age = Integer.parseInt(this.ageEditText.getText().toString());
        } catch (NumberFormatException e) {
            signupInfo.age = 0;
        }
        signupInfo.lastName = this.lastNameEditText.getText().toString();
        signupInfo.firstName = this.firstNameEditText.getText().toString();

        // Write to a bundle
        Bundle bundle = SignupInfo.writeToBundle(signupInfo, this.getContext());

        // return the bundle
        return bundle;
    }

    /*
     * updates instance variables to match the information contained in the given bundle.
     */
    private void readFromArguments() {
        signupInfo = SignupInfo.readFromBundle(getArguments(), this.getContext());
    }
}
