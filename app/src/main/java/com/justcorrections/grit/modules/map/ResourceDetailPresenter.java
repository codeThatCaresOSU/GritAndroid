package com.justcorrections.grit.modules.map;

import com.justcorrections.grit.data.resource.Resource;
import com.justcorrections.grit.data.resource.ResourcesDataSource;

/**
 * Created by Andrew Davis on 12/9/2017.
 */

public class ResourceDetailPresenter {

    private ResourceDetailFragment resourceDetailFragment;

    private String mResourceID;

    public ResourceDetailPresenter(ResourceDetailFragment fragment, String resourceID) {
        this.resourceDetailFragment = fragment;
        this.mResourceID = resourceID;
    }

    public void start() {
        loadResource();
    }

    private void loadResource() {

        ResourcesDataSource.getInstance().getResource(Integer.parseInt(mResourceID), new ResourcesDataSource.GetResourceCallback() {

            @Override
            public void onResourceLoaded(Resource resource) {
                resourceDetailFragment.populateViewsWithResourceDetails(resource);
            }

            @Override
            public void onDataNotAvailable() {
                // TODO: err handling
            }
        });
    }


}
