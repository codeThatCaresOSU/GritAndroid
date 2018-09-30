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
import com.justcorrections.grit.data.model.GritUser;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SignupNamesAge#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SignupGender extends Fragment {

    private GritUser user;
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
        if (getArguments() != null) {
            this.user = GritUser.readFromBundle(getArguments());
        } else {
            this.user = new GritUser();
        }
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
        String gender = user.getValue(GritUser.GENDER_KEY);
        if (gender != null && !gender.isEmpty()) {
            if (gender.equals(GritUser.MALE)) {
                maleRB.toggle();
            } else if (gender.equals(GritUser.FEMALE)) {
                femaleRB.toggle();
            } else {
                otherRB.toggle();
                otherEditText.setText(gender);
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
        ((MainActivity) getActivity()).navigateTo(SignupNamesAge.newInstance(signUpBundle), true);
    }

    /*
     * Navigates to the next signup screen
     */
    private void goNext() {
        if (!validInput()) {
            Toast.makeText(getContext(), R.string.empty_fields_error, Toast.LENGTH_LONG).show();
        } else {
            Bundle signUpBundle = createBundleFromThis();
            ((MainActivity) getActivity()).navigateTo(SignupLocation.newInstance(signUpBundle), true);
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

        // Update instance variables based on user input
        if (maleRB.isChecked()) {
            user.setValue(GritUser.GENDER_KEY, GritUser.MALE);
        } else if (femaleRB.isChecked()) {
            user.setValue(GritUser.GENDER_KEY, GritUser.FEMALE);
        } else if (otherRB.isChecked()){
            user.setValue(GritUser.GENDER_KEY, otherEditText.getText().toString());
        }

        // return the bundle
        return GritUser.writeToBundle(user);
    }
}
