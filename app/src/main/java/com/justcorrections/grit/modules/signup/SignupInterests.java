package com.justcorrections.grit.modules.signup;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.justcorrections.grit.MainActivity;
import com.justcorrections.grit.R;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SignupNamesAge#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SignupInterests extends Fragment {

    private SignupInfo signupInfo;
    private ListView interestsLV;
    boolean[] checked;
    private String[] allInterests = new String[]{"Welding", "Building", "Programming", "Cooking", "Sports", "Having fun", "Partying", "Teaching", "Other"};

    public SignupInterests() {
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param signupInfo a bundle of the signup details.
     * @return A new instance of fragment SignupNamesAge.
     */
    public static SignupInterests newInstance(Bundle signupInfo) {
        SignupInterests fragment = new SignupInterests();
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

        View view = inflater.inflate(R.layout.fragment_signup_interests, container, false);

        // find the views
        Button nextButton = view.findViewById(R.id.button_interests_next);
        Button backButton = view.findViewById(R.id.button_interests_back);
        interestsLV = view.findViewById(R.id.lv_interests);

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
        checked = new boolean[allInterests.length];
        for (int i = 0; i < allInterests.length; i++) {
            checked[i] = signupInfo.interests.contains(allInterests[i]);
        }
        BaseAdapter adapter = new InterestAdapter(getContext(), allInterests, checked);
        interestsLV.setAdapter(adapter);
        interestsLV.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

        // Inflate the layout for this fragment
        return view;
    }

    /*
     * Navigates to the previous signup screen
     */
    private void goBack() {
        Bundle signUpBundle = createBundleFromThis();
        ((MainActivity) getActivity()).navigateTo(SignupBio.newInstance(signUpBundle));
    }

    /*
     * Navigates to the next signup screen
     */
    private void goNext() {
        Bundle signUpBundle = createBundleFromThis();
        ((MainActivity) getActivity()).navigateTo(SignupEmailPasswords.newInstance(signUpBundle));
    }


    /*
     * Creates a Bundle with all of the signup information contained in this fragment's instance variables
     * and views.
     */
    private Bundle createBundleFromThis() {

        // Update instance variables based on user input
        signupInfo.interests = new ArrayList<>();
        for (int i = 0; i < checked.length; i++) {
            if (checked[i]) {
                signupInfo.interests.add(allInterests[i]);
            }
        }

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
