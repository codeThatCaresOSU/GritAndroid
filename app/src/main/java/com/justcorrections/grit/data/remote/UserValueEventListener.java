package com.justcorrections.grit.data.remote;

import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

public class UserValueEventListener implements ValueEventListener {

    private EditText et;

    public UserValueEventListener(EditText et) {
        this.et = et;
    }

    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {
        String value = (String) dataSnapshot.getValue();
        et.setText(value);
    }

    @Override
    public void onCancelled(DatabaseError databaseError) {

    }
}
