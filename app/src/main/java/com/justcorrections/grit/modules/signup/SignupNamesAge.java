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
import com.justcorrections.grit.modules.signin.SigninFragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SignupNamesAge#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SignupNamesAge extends Fragment {

    private EditText nameEditText, ageEditText;
    private GritUser user;

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
        if (getArguments() != null) {
            this.user = GritUser.readFromBundle(getArguments());
        } else {
            this.user = new GritUser();
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_signup_names_age, container, false);

        // find the views
        nameEditText = view.findViewById(R.id.et_name);
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
        if (user.getValue(GritUser.NAME_KEY) != null && !user.getValue(GritUser.NAME_KEY).isEmpty()) {
            nameEditText.setText(user.getValue(GritUser.NAME_KEY));
        }

        if (user.getValue(GritUser.BIRTHDAY_KEY) != null && !user.getValue(GritUser.BIRTHDAY_KEY).isEmpty()) {
            ageEditText.setText(String.valueOf(user.getValue(GritUser.BIRTHDAY_KEY)));
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
        } else {
            Bundle signUpBundle = createBundleFromThis();
            ((MainActivity) getActivity()).navigateTo(SignupGender.newInstance(signUpBundle), true);
        }

    }

    private boolean noEmptyFields() {
        return !ageEditText.getText().toString().isEmpty()
                && !nameEditText.getText().toString().isEmpty();
    }

    /*
     * Creates a Bundle with all of the signup information contained in this fragment's instance variables
     * and views.
     */
    private Bundle createBundleFromThis() {

        // Update instance variables based on user input
        user.setValue(GritUser.BIRTHDAY_KEY, this.ageEditText.getText().toString());
        user.setValue(GritUser.NAME_KEY, this.nameEditText.getText().toString());

        // return the bundle
        return GritUser.writeToBundle(user);
    }
}
