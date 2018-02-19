package com.justcorrections.grit.data.mentees;

import android.support.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.justcorrections.grit.data.DatabaseHelper;
import com.justcorrections.grit.data.DatabaseHelper.DatabasePath;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ianwillis on 11/27/17.
 */

public class MenteeDataSource {

    private static MenteeDataSource INSTANCE;

    private DatabaseReference data;
    private List<Mentee> menteeCache;

    public static MenteeDataSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new MenteeDataSource();
        }
        return INSTANCE;
    }

    private MenteeDataSource() {
        data = DatabaseHelper.getReference(DatabasePath.Mentees);
        menteeCache = null;
    }

    public void destroyCache() {
        menteeCache = null;
    }

    public void getMentee(final String id, @NonNull final GetMenteeCallback callback) {
        if (menteeCache != null) {
            for (Mentee m : menteeCache) {
                if (m.getId().equals(id)) {
                    callback.onMenteeLoaded(m);
                    return;
                }
            }
            callback.onMenteeLoaded(null);
        } else {
            getMentees(new GetMenteesCallback() {
                @Override
                public void onMenteesLoaded(List<Mentee> mentees) {
                    for (Mentee m : mentees) {
                        if (m.getId().equals(id)) {
                            callback.onMenteeLoaded(m);
                            return;
                        }
                    }
                    callback.onMenteeLoaded(null);
                }

                @Override
                public void onDataNotAvailable() {
                    callback.onDataNotAvailable();
                }
            });
        }
    }

    public void getMentees(@NonNull final GetMenteesCallback callback) {
        if (menteeCache != null) {
            callback.onMenteesLoaded(menteeCache);
        } else {
            data.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    List<Mentee> mentees = new ArrayList<>();
                    for (DataSnapshot child : dataSnapshot.getChildren()) {
                        Mentee m = child.getValue(Mentee.class);
                        m.setId(child.getKey());
                        mentees.add(m);
                    }
                    menteeCache = new ArrayList<>(mentees);
                    callback.onMenteesLoaded(mentees);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    callback.onDataNotAvailable();
                }
            });
        }
    }

    public interface GetMenteeCallback {

        void onMenteeLoaded(Mentee mentee);

        void onDataNotAvailable();
    }

    public interface GetMenteesCallback {

        void onMenteesLoaded(List<Mentee> mentees);

        void onDataNotAvailable();
    }

}

