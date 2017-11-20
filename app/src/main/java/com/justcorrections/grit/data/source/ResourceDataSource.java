package com.justcorrections.grit.data.source;

import com.google.android.gms.tasks.Task;

import java.util.List;

/**
 * Created by ianwillis on 11/19/17.
 */

public interface ResourceDataSource {

    interface LoadResourcesCallback {

        void onResourcesLoaded(List<Task> tasks);

        void onDataNotAvailable();
    }

    interface GetResourcesCallback {

        void onResourcesLoaded(Task task);

        void onDataNotAvailable();
    }
}
