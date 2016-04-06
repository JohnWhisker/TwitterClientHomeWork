package com.codepath.apps.restclienttemplate.Others;

import android.content.Context;
import android.content.res.TypedArray;

import com.codepath.apps.restclienttemplate.R;

/**
 * Created by JohnWhisker on 4/5/16.
 */
public class Utils {
    public static int getToolbarHeight(Context context) {
        final TypedArray styledAttributes = context.getTheme().obtainStyledAttributes(
                new int[]{R.attr.actionBarSize});
        int toolbarHeight = (int) styledAttributes.getDimension(0, 0);
        styledAttributes.recycle();

        return toolbarHeight;
    }
}
