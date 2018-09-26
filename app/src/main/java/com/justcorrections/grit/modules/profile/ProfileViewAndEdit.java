package com.justcorrections.grit.modules.profile;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.justcorrections.grit.R;
import com.justcorrections.grit.data.model.GritUser;
import com.justcorrections.grit.data.remote.GritUserDataSource;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileViewAndEdit#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileViewAndEdit extends Fragment {

    private EditText etFirstName, etLastName, etEmail, etAge, etGender, etAddress, etCity, etZip, etBio, etPassword;
    private Button editButton;
    private GritUser user;

    private boolean isEditing = false;

    public ProfileViewAndEdit() {
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment ProfileViewAndEdit.
     */
    public static ProfileViewAndEdit newInstance(GritUser user) {
        ProfileViewAndEdit fragment = new ProfileViewAndEdit();
        Bundle args = GritUser.writeToBundle(user, fragment.getContext());
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.user = GritUser.readFromBundle(getArguments(), getContext());
        } else {
            user = new GritUser();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle bundle) {

        // create the view
        View view = inflater.inflate(R.layout.fragment_profile_view_and_edit, container, false);

        // find the views
        this.etAddress = view.findViewById(R.id.et_profile_address_value);
        this.etAge = view.findViewById(R.id.et_profile_age_value);
        this.etEmail = view.findViewById(R.id.et_profile_email_value);
        this.etGender = view.findViewById(R.id.et_profile_gender_value);
        this.etLastName = view.findViewById(R.id.et_profile_last_name_value);
        this.etFirstName = view.findViewById(R.id.et_profile_first_name_value);
        this.etCity = view.findViewById(R.id.et_profile_city_value);
        this.etBio = view.findViewById(R.id.et_profile_bio_value);
        this.etZip = view.findViewById(R.id.et_profile_zip_value);
        this.etPassword = view.findViewById(R.id.et_profile_password_value);

        this.editButton = view.findViewById(R.id.button_profile_edit);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onButtonClick();
            }
        });

        // put info into the views
        this.etAddress.setText(user.getAddress());
        this.etAge.setText(String.valueOf(user.getBirthday()));
        this.etEmail.setText(user.getEmail());
        this.etPassword.setText(user.getPassword());
        this.etGender.setText(user.getGender());
        this.etLastName.setText(user.getLastName());
        this.etFirstName.setText(user.getFirstName());
        this.etCity.setText(user.getCity());
        this.etBio.setText(user.getBio());
        this.etZip.setText(user.getZip());

        // Inflate the layout for this fragment
        return view;
    }

    private void onButtonClick() {

        isEditing = !isEditing;

        // Enable/Disable editing of the editTexts
        this.etAddress.setEnabled(isEditing);
        this.etAge.setEnabled(isEditing);
        this.etEmail.setEnabled(isEditing);
        this.etGender.setEnabled(isEditing);
        this.etLastName.setEnabled(isEditing);
        this.etFirstName.setEnabled(isEditing);
        this.etCity.setEnabled(isEditing);
        this.etBio.setEnabled(isEditing);
        this.etZip.setEnabled(isEditing);
        this.etPassword.setEnabled(isEditing);

        if (isEditing) {

            // Change the title on the button
            this.editButton.setText(R.string.confirm);

        } else {

            // change the title on the button
            this.editButton.setText(R.string.profile_edit_button_title);

            readAndSave();
        }

    }

    private void readAndSave() {

        // Read the information from the edit texts
        user.setAddress(etAddress.getText().toString());
        user.setEmail(etEmail.getText().toString());
        user.setGender(etGender.getText().toString());
        user.setLastName(etLastName.getText().toString());
        user.setFirstName(etFirstName.getText().toString());
        user.setCity(etCity.getText().toString());
        user.setBio(etBio.getText().toString());
        user.setZip(etZip.getText().toString());
        user.setPassword(etPassword.getText().toString());
        user.setBirthday(etAge.getText().toString());

        // save the new information to the database
        GritUserDataSource.getInstance().updateItem(user, user.getId());

    }



}
