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
import com.justcorrections.grit.data.model.GritUser;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SignupNamesAge#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SignupBio extends Fragment {

    private GritUser user;
    private EditText occupationEditText, descriptionEditText;

    public SignupBio() {}

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param signupInfo a bundle of the signup details.
     * @return A new instance of fragment SignupBio.
     */
    public static SignupBio newInstance(Bundle signupInfo) {
        SignupBio fragment = new SignupBio();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_signup_bio, container, false);

        // find the views
        Button backButton = view.findViewById(R.id.button_bio_back);
        Button nextButton = view.findViewById(R.id.button_bio_next);
        occupationEditText = view.findViewById(R.id.et_bio_occupation);
        descriptionEditText = view.findViewById(R.id.et_bio_description);

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
        if (user.getValue(GritUser.DESCRIPTION_KEY) != null && !user.getValue(GritUser.DESCRIPTION_KEY).isEmpty()) {
            descriptionEditText.setText(user.getValue(GritUser.DESCRIPTION_KEY));
        }

        if (user.getValue(GritUser.OCCUPATION_KEY) != null && !user.getValue(GritUser.OCCUPATION_KEY).isEmpty()) {
            occupationEditText.setText(user.getValue(GritUser.OCCUPATION_KEY));
        }

        // Inflate the layout for this fragment
        return view;
    }

    /*
     * Navigates to the previous signup screen
     */
    private void goBack() {
        Bundle signUpBundle = createBundleFromThis();
        ((MainActivity) getActivity()).navigateTo(SignupLocation.newInstance(signUpBundle), true);
    }

    /*
     * Navigates to the next signup screen
     */
    private void goNext() {
        if (!noEmptyFields()) {
            Toast.makeText(getContext(), R.string.empty_fields_error, Toast.LENGTH_LONG).show();
        } else {
            Bundle signUpBundle = createBundleFromThis();
            ((MainActivity) getActivity()).navigateTo(SignupEmailPasswords.newInstance(signUpBundle), true);
        }
    }

    private boolean noEmptyFields() {
        return !occupationEditText.getText().toString().isEmpty();
    }

    /*
     * Creates a Bundle with all of the signup information contained in this fragment's instance variables
     * and views.
     */
    private Bundle createBundleFromThis() {

        // Update instance variables based on user input
        user.setValue(GritUser.DESCRIPTION_KEY, this.descriptionEditText.getText().toString());
        user.setValue(GritUser.OCCUPATION_KEY, this.occupationEditText.getText().toString());

        // return the bundle
        return GritUser.writeToBundle(user);
    }
}
