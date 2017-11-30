package com.justcorrections.grit.map;

import com.justcorrections.grit.data.category.Category;
import com.justcorrections.grit.data.category.CategoryDataSource;
import com.justcorrections.grit.data.category.CategoryDataSource.GetCategoriesCallback;
import com.justcorrections.grit.data.resource.Resource;
import com.justcorrections.grit.data.resource.ResourcesDataSource;
import com.justcorrections.grit.data.resource.ResourcesDataSource.GetResourcesCallback;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * Created by ianwillis on 11/27/17.
 */

public class MapPresenter {

    private MapFragment mapFragment;

    private Category[] categories;
    private boolean[] checked;
    private List<Resource> resources;

    private SortedMap<String, List<Resource>> sortedResources; // maps: a category's name to the resources that fall under said category

    public MapPresenter(MapFragment mapFragment) {
        this.mapFragment = mapFragment;
    }

    public void start() {
        loadCategories();
        loadResources();
    }

    public void loadCategories() {
        CategoryDataSource.getInstance().getCategories(new GetCategoriesCallback() {
            @Override
            public void onCategoriesLoaded(List<Category> loadedCategories) {
                categories = new Category[loadedCategories.size()];
                loadedCategories.toArray(categories);
                checked = new boolean[loadedCategories.size()];
            }

            @Override
            public void onDataNotAvailable() {
                //TODO: err handling?
            }
        });
    }

    public void loadResources() {
        ResourcesDataSource.getInstance().getResources(new GetResourcesCallback() {
            @Override
            public void onResourcesLoaded(List<Resource> loadedResources) {
                resources = loadedResources;
                sortResources();
            }

            @Override
            public void onDataNotAvailable() {
                //TODO: err handling?
            }
        });
    }

    private void sortResources() {
        sortedResources = new TreeMap<>();
        for (Resource r : resources) {
            String category = r.getCategory();
            if (!sortedResources.containsKey(category)) {
                sortedResources.put(category, new ArrayList<Resource>());
            }
            sortedResources.get(category).add(r);
        }
    }

    public void onFilterButtonPressed() {
        String[] categories = new String[this.categories.length];
        for (int i = 0; i < this.categories.length; i++)
            categories[i] = this.categories[i].getName();
        mapFragment.openFilterMenu(categories, checked);
    }

    public void onFilterChanged(String[] categoryNames, boolean[] newSelected) {
    }
}
