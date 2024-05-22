package com.example.viajada.helper;

import android.content.Context;

public class LayoutHelper {

    private final Context context;

    public LayoutHelper(Context context){
        this.context = context;
    }

    // Helper method to convert dp to pixels
    public int dpToPx(int dp) {
        float density = context.getResources().getDisplayMetrics().density;
        return Math.round(dp * density);
    }
}
