package com.justcorrections.grit.map;

import android.content.Context;

import com.justcorrections.grit.R;
import com.justcorrections.grit.data.category.Category;
import com.justcorrections.grit.data.category.CategoryDataSource;
import com.justcorrections.grit.data.category.CategoryDataSource.GetCategoriesCallback;
import com.justcorrections.grit.data.resource.Resource;
import com.justcorrections.grit.data.resource.ResourcesDataSource;
import com.justcorrections.grit.data.resource.ResourcesDataSource.GetResourcesCallback;
import com.justcorrections.grit.utils.ArrayUtils;
import com.justcorrections.grit.utils.Preferences;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * Created by ianwillis on 11/27/17.
 */

public class MapPresenter {

    private MapFragment mapFragment;

    private Category[] categories;
    private boolean[] selected;
    private SortedMap<String, List<Resource>> sortedResources; // maps: a category's name -> the resources that fall under said category; sorted a-z by key

    private boolean categoriesHaveLoaded, resourcesHaveLoaded;

    public MapPresenter(MapFragment mapFragment) {
        this.mapFragment = mapFragment;
    }

    public void start() {
        categoriesHaveLoaded = false;
        resourcesHaveLoaded = false;
        mapFragment.onFilterDataLoading();
        loadCategories();
        loadResources();
    }

    // TODO: onPause, dereference mapFragment to prevent memory leak? not sure if needed

    public void loadCategories() {
        CategoryDataSource.getInstance().getCategories(new GetCategoriesCallback() {
            @Override
            public void onCategoriesLoaded(List<Category> loadedCategories) {
                categories = new Category[loadedCategories.size()];
                loadedCategories.toArray(categories);
                selected = new boolean[loadedCategories.size()];
                categoriesHaveLoaded = true;
                if (resourcesHaveLoaded) {
                    getFilterSelectedPreference();
                    mapFragment.onFilterDataLoaded();
                }
            }

            @Override
            public void onDataNotAvailable() {
                //TODO: err handling
            }
        });
    }

    public void loadResources() {
        ResourcesDataSource.getInstance().getResources(new GetResourcesCallback() {
            @Override
            public void onResourcesLoaded(List<Resource> loadedResources) {
                resourcesHaveLoaded = true;
                sortResources(loadedResources);
                resourcesHaveLoaded = true;
                if (categoriesHaveLoaded) {
                    getFilterSelectedPreference();
                    mapFragment.onFilterDataLoaded();
                }
            }

            @Override
            public void onDataNotAvailable() {
                //TODO: err handling
            }
        });
    }

    private void sortResources(List<Resource> resources) {
        sortedResources = new TreeMap<>();
        for (Resource r : resources) {
            String categoryName = r.getCategory();
            if (!sortedResources.containsKey(categoryName)) {
                sortedResources.put(categoryName, new ArrayList<Resource>());
            }
            sortedResources.get(categoryName).add(r);
        }
    }

    public void onFilterButtonPressed() {
        if (categoriesHaveLoaded && resourcesHaveLoaded) {
            String[] categories = new String[this.categories.length];
            for (int i = 0; i < this.categories.length; i++)
                categories[i] = this.categories[i].getName();
            boolean[] selectedCopy = Arrays.copyOf(selected, selected.length); // use copy so original values aren't changed
            mapFragment.openFilterMenu(categories, selectedCopy);
        }
    }

    public void onFilterChanged(String[] categoryNames, boolean[] updatedSelected) {
        boolean[] changes = ArrayUtils.findArrayChanges(selected, updatedSelected);
        for (int i = 0; i < changes.length; i++) {
            if (changes[i] && updatedSelected[i]) {
                mapFragment.setMarkers(categories[i], sortedResources.get(categoryNames[i]));
            } else if (changes[i] && !updatedSelected[i]) {
                mapFragment.removeMarkers(categoryNames[i]);
            }
        }
        selected = updatedSelected;
        updateFilterSelectedPreference();
    }

    private void getFilterSelectedPreference() {
        Context context = mapFragment.getContext();
        Set<String> selectedFilters = Preferences.getStringSetPreference(context, context.getString(R.string.MAP_FILTER_SELECTED));
        if (selectedFilters != null) {
            for (int i = 0; i < categories.length; i++) {
                String categoryName = categories[i].getName();
                if (selectedFilters.contains(categoryName)) {
                    selected[i] = true;
                    mapFragment.setMarkers(categories[i], sortedResources.get(categories[i].getName()));
                }
            }
        }
    }

    private void updateFilterSelectedPreference() {
        Set<String> selectedFilters = new HashSet<>();
        for (int i = 0; i < categories.length; i++) {
            if (selected[i])
                selectedFilters.add(categories[i].getName());
        }
        Context context = mapFragment.getContext();
        Preferences.setStringSetPreference(context, context.getString(R.string.MAP_FILTER_SELECTED), selectedFilters);
    }
}
