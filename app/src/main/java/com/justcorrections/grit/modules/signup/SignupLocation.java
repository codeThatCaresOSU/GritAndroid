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

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SignupNamesAge#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SignupLocation extends Fragment {

    private SignupInfo signupInfo;

    private EditText addressEditText, cityEditText, zipEditText;

    public SignupLocation() {}

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param signupInfo a bundle of the signup details.
     * @return A new instance of fragment SignupNamesAge.
     */
    public static SignupLocation newInstance(Bundle signupInfo) {
        SignupLocation fragment = new SignupLocation();
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

        View view = inflater.inflate(R.layout.fragment_signup_location, container, false);

        // find the views
        Button backButton = view.findViewById(R.id.button_location_back);
        Button nextButton = view.findViewById(R.id.button_location_next);
        addressEditText = view.findViewById(R.id.et_street_address);
        cityEditText = view.findViewById(R.id.et_City);
        zipEditText = view.findViewById(R.id.et_zip);

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
        if (addressEditText != null && !signupInfo.address.isEmpty()) {
            addressEditText.setText(signupInfo.address);
        }
        if (signupInfo.city != null && !signupInfo.city.isEmpty()) {
            cityEditText.setText(signupInfo.city);
        }
        if (signupInfo.zip != null && !signupInfo.zip.isEmpty()) {
            zipEditText.setText(signupInfo.zip);
        }

        // Inflate the layout for this fragment
        return view;
    }

    /*
     * Navigates to the previous signup screen
     */
    private void goBack() {
        Bundle signUpBundle = createBundleFromThis();
        ((MainActivity) getActivity()).navigateTo(SignupGender.newInstance(signUpBundle), true);
    }

    /*
     * Navigates to the next signup screen
     */
    private void goNext() {
        if (!noEmptyFields()) {
            Toast.makeText(getContext(), R.string.empty_fields_error, Toast.LENGTH_LONG).show();
        } else {
            Bundle signUpBundle = createBundleFromThis();
            ((MainActivity) getActivity()).navigateTo(SignupBio.newInstance(signUpBundle), true);
        }
    }

    private boolean noEmptyFields() {
        return !addressEditText.getText().toString().isEmpty()
                && !zipEditText.getText().toString().isEmpty()
                && !cityEditText.getText().toString().isEmpty();
    }

    /*
     * Creates a Bundle with all of the signup information contained in this fragment's instance variables
     * and views.
     */
    private Bundle createBundleFromThis() {

        // Update instance variables based on user input
        signupInfo.city = cityEditText.getText().toString();
        signupInfo.address = addressEditText.getText().toString();
        signupInfo.zip = zipEditText.getText().toString();

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
