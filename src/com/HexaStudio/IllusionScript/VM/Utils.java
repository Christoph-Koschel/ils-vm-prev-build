package com.HexaStudio.IllusionScript.VM;

public class Utils {
    public static boolean includes(Object[] arr, Object search) {
        for (Object item : arr) {
            if (item.equals(search)) {
                return true;
            }
        }

        return false;
    }
}
