package com.justcorrections.grit.utils;

/**
 * Created by ianwillis on 12/4/17.
 */

public class ArrayUtils {

    public static boolean[] findArrayChanges(boolean[] original, boolean[] updated) {
        if (original.length != updated.length)
            throw new IllegalStateException("Array must have the same length");
        boolean[] changes = new boolean[original.length];
        for (int i = 0; i < original.length; i++)
            changes[i] = original[i] ^ updated[i];
        return changes;
    }

}
