package com.justcorrections.grit.modules.map;

import android.content.Context;

import com.justcorrections.grit.R;
import com.justcorrections.grit.data.mapdata.categories.Category;
import com.justcorrections.grit.data.mapdata.categories.CategoryDataSource;
import com.justcorrections.grit.data.mapdata.categories.CategoryDataSource.GetCategoriesCallback;
import com.justcorrections.grit.data.mapdata.resources.Resource;
import com.justcorrections.grit.data.mapdata.resources.ResourcesDataSource;
import com.justcorrections.grit.data.mapdata.resources.ResourcesDataSource.GetResourcesCallback;
import com.justcorrections.grit.utils.ArrayUtils;
import com.justcorrections.grit.utils.Preferences;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;


public class MapPresenter {

    private MapFragment view;

    private Category[] categories; // array of resource categories to be used in filter menu
    private boolean[] selected; // array stating which categories are selected
    /*
     *  maps: a category's name -> the resources that fall under said category; sorted a-z by key
     *  sorts categories in a-z order for filter menu
     *  stores resources available for each category
     */
    private SortedMap<String, List<Resource>> sortedResources;

    private boolean categoriesHaveLoaded, resourcesHaveLoaded; // flags; need because we can't return to view until both have loaded

    public MapPresenter() {
        this.categoriesHaveLoaded = false;
        this.resourcesHaveLoaded = false;

    }

    public void start(MapFragment mapFragment) {
        this.view = mapFragment;
        if (!categoriesHaveLoaded && !resourcesHaveLoaded) {
            view.onFilterDataLoading(); // notify data is loading
            loadCategories();
            loadResources();
        } else {
            view.onFilterDataLoaded(); // notify data already loaded
        }
    }

    public void pause() {
        this.view = null; // prevent memory leak
        //TODO: preferences should be set here
    }

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
                    view.onFilterDataLoaded(); // notify data finished loading
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
                sortResources(loadedResources);
                resourcesHaveLoaded = true;
                if (categoriesHaveLoaded) {
                    getFilterSelectedPreference();
                    view.onFilterDataLoaded(); // notify data finished loading
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
            view.openFilterMenu(categories, selectedCopy);
        }
    }

    public void onFilterChanged(String[] categoryNames, boolean[] updatedSelected) {
        boolean[] changes = ArrayUtils.findArrayChanges(selected, updatedSelected);
        for (int i = 0; i < changes.length; i++) {
            if (changes[i] && updatedSelected[i]) {
                view.setMarkers(categories[i], sortedResources.get(categoryNames[i]));
            } else if (changes[i] && !updatedSelected[i]) {
                view.removeMarkers(categoryNames[i]);
            }
        }
        selected = updatedSelected;
        updateFilterSelectedPreference();
    }

    private void getFilterSelectedPreference() {
        Context context = view.getContext();
        Set<String> selectedFilters = Preferences.getStringSetPreference(context, context.getString(R.string.MAP_FILTER_SELECTED));
        if (selectedFilters != null) {
            for (int i = 0; i < categories.length; i++) {
                String categoryName = categories[i].getName();
                if (selectedFilters.contains(categoryName)) {
                    selected[i] = true;
                    view.setMarkers(categories[i], sortedResources.get(categories[i].getName()));
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
        Context context = view.getContext();
        Preferences.setStringSetPreference(context, context.getString(R.string.MAP_FILTER_SELECTED), selectedFilters);
    }
}
