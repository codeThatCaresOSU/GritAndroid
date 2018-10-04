package com.justcorrections.grit.modules.signup;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.justcorrections.grit.MainActivity;
import com.justcorrections.grit.R;
import com.justcorrections.grit.auth.GritAuthentication;
import com.justcorrections.grit.data.StorageHelper;
import com.justcorrections.grit.data.model.GritUser;
import com.justcorrections.grit.modules.profile.ProfileViewAndEdit;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SignupNamesAge#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SignupConfirm extends Fragment {

    private GritUser user;
    private TextView tvName, tvBirthday, tvCity, tvAddress, tvZip, tvDescription, tvEmail, tvGender, tvState, tvOccupation;
    private TextView tvChooseImage;
    private Button nextButton;
    private ImageView profileImageView;

    private static final int REQ_CODE_PICK_IMAGE = 123;
    private Uri profileImageUri;
    private Bitmap profileImage;

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
        profileImageView = view.findViewById(R.id.profile_imageview);
        tvChooseImage = view.findViewById(R.id.tv_choose_image);

        profileImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseImage();
            }
        });

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

    private void chooseImage() {
        // Allow the user to choose where they want to choose an image from
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");

        // When the activity that is launched returns, it will provide the IMAGE_SELECT_REQUEST_CODE
        startActivityForResult(intent, REQ_CODE_PICK_IMAGE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQ_CODE_PICK_IMAGE && resultCode == RESULT_OK) {
            this.profileImageUri = data.getData();

            try {
                this.profileImage = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), this.profileImageUri);
                this.profileImageView.setImageBitmap(this.profileImage);
                this.tvChooseImage.setVisibility(View.INVISIBLE);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
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
                GritUser.saveToDatabase(user, FirebaseAuth.getInstance().getCurrentUser().getUid());
                saveProfileImage(FirebaseAuth.getInstance().getCurrentUser().getUid());
                ((MainActivity) getActivity()).navigateTo(ProfileViewAndEdit.newInstance(), false);
            }

            @Override
            public void onFailure(String errorMessage) {
                Toast.makeText(SignupConfirm.this.getContext(), errorMessage, Toast.LENGTH_LONG).show();
            }
        });
    }

    private void saveProfileImage(String uid) {

        if (this.profileImageUri != null) {
            StorageReference storageReference =
                    FirebaseStorage.getInstance().getReference().child(StorageHelper.USER_PROFILE_PATH + "/" + uid);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            this.profileImage.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            storageReference.putBytes(baos.toByteArray());
        }

    }

    /*
     * Creates a Bundle with all of the signup information contained in this fragment's instance variables
     * and views.
     */
    private Bundle createBundleFromThis() {
        return GritUser.writeToBundle(user);
    }
}
