package com.justcorrections.grit.modules.profile;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.justcorrections.grit.MainActivity;
import com.justcorrections.grit.R;
import com.justcorrections.grit.auth.GritAuthentication;
import com.justcorrections.grit.data.DatabaseHelper;
import com.justcorrections.grit.data.StorageHelper;
import com.justcorrections.grit.data.model.GritUser;
import com.justcorrections.grit.data.remote.UserValueEventListener;
import com.justcorrections.grit.modules.signin.SigninFragment;

import java.io.IOException;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileViewAndEdit#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileViewAndEdit extends Fragment {

    private EditText etName, etOccupation, etState, etEmail, etBirthday, etGender, etAddress, etCity, etZip, etDescription;
    private Button editButton, signoutButton, changePasswordButton;
    private String uid;
    private ImageView profileImageView;

    private boolean isEditing = false;

    private static final int MAX_DOWNLOAD_RETRYS = 5;
    private int numRetrys;

    public ProfileViewAndEdit() {
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment ProfileViewAndEdit.
     */
    public static ProfileViewAndEdit newInstance() {
        ProfileViewAndEdit fragment = new ProfileViewAndEdit();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle bundle) {

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            uid = currentUser.getUid();
        } else {
            ((MainActivity) this.getActivity()).navigateTo(SigninFragment.newInstance(), false);
        }

        // create the view
        View view = inflater.inflate(R.layout.fragment_profile_view_and_edit, container, false);

        // find the views
        this.etAddress = view.findViewById(R.id.et_profile_address_value);
        this.etBirthday = view.findViewById(R.id.et_profile_birthday_value);
        this.etEmail = view.findViewById(R.id.et_profile_email_value);
        this.etGender = view.findViewById(R.id.et_profile_gender_value);
        this.etName = view.findViewById(R.id.et_profile_name_value);
        this.etCity = view.findViewById(R.id.et_profile_city_value);
        this.etDescription = view.findViewById(R.id.et_profile_bio_value);
        this.etZip = view.findViewById(R.id.et_profile_zip_value);
        this.etState = view.findViewById(R.id.et_profile_state_value);
        this.etOccupation = view.findViewById(R.id.et_profile_occupation_value);

        this.profileImageView = view.findViewById(R.id.profile_imageview_profile);
        Bitmap placeHolder = BitmapFactory.decodeResource(this.getContext().getResources(), R.drawable.ic_user);
        this.profileImageView.setImageBitmap(placeHolder);

        this.editButton = view.findViewById(R.id.button_profile_edit);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onEditButtonClick();
            }
        });

        this.signoutButton = view.findViewById(R.id.button_profile_signout);
        signoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                ((MainActivity) getActivity()).navigateTo(SigninFragment.newInstance(), false);
            }
        });

        this.changePasswordButton = view.findViewById(R.id.button_profile_change_password);
        this.changePasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth instance = FirebaseAuth.getInstance();
                FirebaseUser currentUser = instance.getCurrentUser();
                instance.sendPasswordResetEmail(currentUser.getEmail());
                instance.signOut();
                ((MainActivity) getActivity()).navigateTo(SigninFragment.newInstance(), false);
            }
        });

        // Update the views with data from the database
        DatabaseReference userRef = DatabaseHelper.getReference(DatabaseHelper.DatabasePath.TEST).child(uid);
        userRef.child(GritUser.NAME_KEY).addValueEventListener(new UserValueEventListener(etName));
        userRef.child(GritUser.CITY_KEY).addValueEventListener(new UserValueEventListener(etCity));
        userRef.child(GritUser.ADDRESS_KEY).addValueEventListener(new UserValueEventListener(etAddress));
        userRef.child(GritUser.ZIP_KEY).addValueEventListener(new UserValueEventListener(etZip));
        userRef.child(GritUser.DESCRIPTION_KEY).addValueEventListener(new UserValueEventListener(etDescription));
        userRef.child(GritUser.BIRTHDAY_KEY).addValueEventListener(new UserValueEventListener(etBirthday));
        userRef.child(GritUser.GENDER_KEY).addValueEventListener(new UserValueEventListener(etGender));
        userRef.child(GritUser.STATE_KEY).addValueEventListener(new UserValueEventListener(etState));
        userRef.child(GritUser.OCCUPATION_KEY).addValueEventListener(new UserValueEventListener(etOccupation));
        userRef.child(GritUser.EMAIL_KEY).addValueEventListener(new UserValueEventListener(etEmail));

        // Load the profile image from firebase storage
        downloadProfileImage();

        // Inflate the layout for this fragment
        return view;
    }

    private void downloadProfileImage() {

        StorageReference storageRef =
                FirebaseStorage.getInstance().getReference().child(StorageHelper.USER_PROFILE_PATH + "/" + uid);
        storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(ProfileViewAndEdit.this.getContext()).load(uri).into(ProfileViewAndEdit.this.profileImageView);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                // Keep retrying to download the image

                // TODO probably a terrible way to do this
                numRetrys++;
                if (numRetrys <= MAX_DOWNLOAD_RETRYS) {
                    android.os.SystemClock.sleep(1000);
                    downloadProfileImage();
                }

            }
        });


    }

    private void onEditButtonClick() {

        isEditing = !isEditing;

        // Enable/Disable editing of the editTexts
        this.etAddress.setEnabled(isEditing);
        this.etBirthday.setEnabled(isEditing);
        this.etEmail.setEnabled(isEditing);
        this.etGender.setEnabled(isEditing);
        this.etName.setEnabled(isEditing);
        this.etCity.setEnabled(isEditing);
        this.etDescription.setEnabled(isEditing);
        this.etZip.setEnabled(isEditing);
        this.etOccupation.setEnabled(isEditing);
        this.etState.setEnabled(isEditing);

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
        GritUser user = new GritUser();
        user.setValue(GritUser.ADDRESS_KEY, etAddress.getText().toString());
        user.setValue(GritUser.EMAIL_KEY, etEmail.getText().toString());
        user.setValue(GritUser.GENDER_KEY, etGender.getText().toString());
        user.setValue(GritUser.NAME_KEY, etName.getText().toString());
        user.setValue(GritUser.CITY_KEY, etCity.getText().toString());
        user.setValue(GritUser.DESCRIPTION_KEY, etDescription.getText().toString());
        user.setValue(GritUser.ZIP_KEY, etZip.getText().toString());
        user.setValue(GritUser.BIRTHDAY_KEY, etBirthday.getText().toString());
        user.setValue(GritUser.STATE_KEY, etState.getText().toString());
        user.setValue(GritUser.OCCUPATION_KEY, etOccupation.getText().toString());

        try {
            FirebaseAuth.getInstance().getCurrentUser().updateEmail(etEmail.getText().toString());
        } catch (Exception e) {
            Toast.makeText(this.getContext(),
                    "There was an issue changing your email. Please Sign out and Sign back in.",
                    Toast.LENGTH_LONG).show();
            return;
        }


        // save the new information to the database
        GritUser.saveToDatabase(user, this.uid);

    }


}
