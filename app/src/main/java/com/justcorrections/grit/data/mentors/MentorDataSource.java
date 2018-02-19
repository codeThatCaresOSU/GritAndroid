package com.justcorrections.grit.data.mentors;

import android.support.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.justcorrections.grit.data.DatabaseHelper;
import com.justcorrections.grit.data.DatabaseHelper.DatabasePath;
import com.justcorrections.grit.data.mentees.Mentee;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ianwillis on 11/27/17.
 */

public class MentorDataSource {

    private static MentorDataSource INSTANCE;

    private DatabaseReference data;
    private List<Mentor> mentorCache;

    public static MentorDataSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new MentorDataSource();
        }
        return INSTANCE;
    }

    private MentorDataSource() {
        data = DatabaseHelper.getReference(DatabasePath.Mentees);
        mentorCache = null;
    }

    public void destroyCache() {
        mentorCache = null;
    }

    public void getMentee(final String id, @NonNull final GetMentorCallback callback) {
        if (mentorCache != null) {
            for (Mentor m : mentorCache) {
                if (m.getId().equals(id)) {
                    callback.onMentorLoaded(m);
                    return;
                }
            }
            callback.onMentorLoaded(null);
        } else {
            getMentees(new GetMentorsCallback() {
                @Override
                public void onMentorsLoaded(List<Mentor> mentors) {
                    for (Mentor m : mentors) {
                        if (m.getId().equals(id)) {
                            callback.onMentorLoaded(m);
                            return;
                        }
                    }
                    callback.onMentorLoaded(null);
                }

                @Override
                public void onDataNotAvailable() {
                    callback.onDataNotAvailable();
                }
            });
        }
    }

    public void getMentees(@NonNull final GetMentorsCallback callback) {
        if (mentorCache != null) {
            callback.onMentorsLoaded(mentorCache);
        } else {
            data.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    List<Mentor> mentors = new ArrayList<>();
                    for (DataSnapshot child : dataSnapshot.getChildren()) {
                        Mentor m = child.getValue(Mentor.class);
                        m.setId(child.getKey());
                        mentors.add(m);
                    }
                    mentorCache = new ArrayList<>(mentors);
                    callback.onMentorsLoaded(mentors);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    callback.onDataNotAvailable();
                }
            });
        }
    }

    public interface GetMentorCallback {

        void onMentorLoaded(Mentor mentor);

        void onDataNotAvailable();
    }

    public interface GetMentorsCallback {

        void onMentorsLoaded(List<Mentor> mentors);

        void onDataNotAvailable();
    }

}

