package com.justcorrections.grit.modules.map;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;

/**
 * Created by ianwillis on 12/4/17.
 */

class GoogleMapUtils {

    static float hue(String color) {
        switch (color) {
            case "RED":
                return BitmapDescriptorFactory.HUE_RED;
            case "BLUE":
                return BitmapDescriptorFactory.HUE_BLUE;
            case "GREEN":
                return BitmapDescriptorFactory.HUE_GREEN;
            case "YELLOW":
                return BitmapDescriptorFactory.HUE_YELLOW;
            case "ORANGE":
                return BitmapDescriptorFactory.HUE_ORANGE;
            default:
                // in case new category is added
                return BitmapDescriptorFactory.HUE_VIOLET;
        }
    }

}
