package com.justcorrections.grit.modules.signup;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.justcorrections.grit.MainActivity;
import com.justcorrections.grit.R;
import com.justcorrections.grit.auth.GritAuthentication;
import com.justcorrections.grit.data.model.GritUser;
import com.justcorrections.grit.modules.profile.ProfileViewAndEdit;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SignupNamesAge#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SignupConfirm extends Fragment {

    private GritUser user;
    private TextView tvName, tvBirthday, tvCity, tvAddress, tvZip, tvDescription, tvEmail, tvGender, tvState, tvOccupation;
    private Button nextButton;

    public SignupConfirm() {
    }

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
        if (getArguments() != null) {
            this.user = GritUser.readFromBundle(getArguments());
        } else {
            this.user = new GritUser();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_signup_confirm, container, false);

        // find the views
        Button backButton = view.findViewById(R.id.button_confirm_back);
        nextButton = view.findViewById(R.id.button_confirm_next);

        tvAddress = view.findViewById(R.id.tv_confirm_address_value);
        tvCity = view.findViewById(R.id.tv_confirm_city_value);
        tvZip = view.findViewById(R.id.tv_confirm_zip_value);
        tvBirthday = view.findViewById(R.id.tv_confirm_age_value);
        tvName = view.findViewById(R.id.tv_confirm_name_value);
        tvDescription = view.findViewById(R.id.tv_confirm_bio_value);
        tvEmail = view.findViewById(R.id.tv_confirm_email_value);
        tvGender = view.findViewById(R.id.tv_confirm_gender_value);
        tvState = view.findViewById(R.id.tv_confirm_state_value);
        tvOccupation = view.findViewById(R.id.tv_confirm_occupation_value);


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
                registerUser();
            }
        });

        /*
         * Populate views with data if it already exists
         */
        tvAddress.setText(user.getValue(GritUser.ADDRESS_KEY));
        tvCity.setText(user.getValue(GritUser.CITY_KEY));
        tvZip.setText(user.getValue(GritUser.ZIP_KEY));
        tvName.setText(user.getValue(GritUser.NAME_KEY));
        tvDescription.setText(user.getValue(GritUser.DESCRIPTION_KEY));
        tvEmail.setText(user.getValue(GritUser.EMAIL_KEY));
        tvBirthday.setText(user.getValue(GritUser.BIRTHDAY_KEY));
        tvState.setText(user.getValue(GritUser.STATE_KEY));
        tvOccupation.setText(user.getValue(GritUser.OCCUPATION_KEY));
        tvGender.setText(user.getValue(GritUser.GENDER_KEY));

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
    private void registerUser() {

        final GritAuthentication auth = GritAuthentication.getInstance();
        auth.createUser(user.getValue(GritUser.EMAIL_KEY), user.getValue(GritUser.PASSWORD_KEY), new GritAuthentication.GritAuthCallback() {
            @Override
            public void onSuccess() {
                nextButton.setEnabled(false);
                GritUser.saveToDatabase(user, auth.getCurrentUser().getUid());
                ((MainActivity) getActivity()).navigateTo(ProfileViewAndEdit.newInstance(), false);
            }

            @Override
            public void onFailure(String errorMessage) {
                Toast.makeText(SignupConfirm.this.getContext(), errorMessage, Toast.LENGTH_LONG).show();
            }
        });
    }

    /*
     * Creates a Bundle with all of the signup information contained in this fragment's instance variables
     * and views.
     */
    private Bundle createBundleFromThis() {
        // return the bundle
        return GritUser.writeToBundle(user);
    }
}
