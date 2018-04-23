package com.justcorrections.grit.modules.signup;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.justcorrections.grit.MainActivity;
import com.justcorrections.grit.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SignupNamesAge#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SignupConfirm extends Fragment {

    private SignupInfo signupInfo;

    private TextView tvFirstName, tvLastName, tvAge, tvCity, tvAddress, tvZip, tvBio, tvEmail, tvGender, tvInterests;

    public SignupConfirm() {}

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param signupInfo a bundle of the signup details.
     * @return A new instance of fragment SignupConfirm.
     */
    public static SignupConfirm newInstance(Bundle signupInfo) {
        SignupConfirm fragment = new SignupConfirm();
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

        View view = inflater.inflate(R.layout.fragment_signup_confirm, container, false);

        // find the views
        Button backButton = view.findViewById(R.id.button_confirm_back);
        Button nextButton = view.findViewById(R.id.button_confirm_next);

        tvAddress = view.findViewById(R.id.tv_confirm_address_value);
        tvCity = view.findViewById(R.id.tv_confirm_city_value);
        tvZip = view.findViewById(R.id.tv_confirm_zip_value);
        tvAge = view.findViewById(R.id.tv_confirm_age_value);
        tvFirstName = view.findViewById(R.id.tv_confirm_first_name_value);
        tvLastName = view.findViewById(R.id.tv_confirm_last_name_value);
        tvBio = view.findViewById(R.id.tv_confirm_bio_value);
        tvEmail = view.findViewById(R.id.tv_confirm_email_value);
        tvGender = view.findViewById(R.id.tv_confirm_gender_value);
        tvInterests = view.findViewById(R.id.tv_confirm_interests_value);

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
        tvAddress.setText(signupInfo.address);
        tvCity.setText(signupInfo.city);
        tvZip.setText(signupInfo.zip);
        tvFirstName.setText(signupInfo.firstName);
        tvLastName.setText(signupInfo.lastName);
        tvBio.setText(signupInfo.bio);
        tvEmail.setText(signupInfo.email);

        String interestsValue = "";
        for (String interest : signupInfo.interests) {
            interestsValue = interestsValue + interest + "\n";
        }
        if (signupInfo.interests.size() > 0) {
            // get rid of the last \n
            interestsValue = interestsValue.substring(0, interestsValue.length() - 1);
        }
        tvInterests.setText(interestsValue);

        if (signupInfo.age == 0) {
            tvAge.setText("");
        } else {
            tvAge.setText(String.valueOf(signupInfo.age));
        }

        if (!signupInfo.gender.contains(getString(R.string.other_prefix))) {
            tvGender.setText(signupInfo.gender);
        } else {
            tvGender.setText(signupInfo.gender.substring(6));
        }


        // Inflate the layout for this fragment
        return view;
    }

    /*
     * Navigates to the previous signup screen
     */
    private void goBack() {
        Bundle signUpBundle = createBundleFromThis();
        ((MainActivity) getActivity()).navigateTo(SignupEmailPasswords.newInstance(signUpBundle), true);
    }

    /*
     * Navigates to the next signup screen
     */
    private void goNext() {
        // TODO send user data to firebase
        // Check that all data has been entered
    }

    /*
     * Creates a Bundle with all of the signup information contained in this fragment's instance variables
     * and views.
     */
    private Bundle createBundleFromThis() {

        // Update instance variables based on user input

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
