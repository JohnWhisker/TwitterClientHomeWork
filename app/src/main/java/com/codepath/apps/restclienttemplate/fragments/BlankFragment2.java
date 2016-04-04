package com.codepath.apps.restclienttemplate.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codepath.apps.restclienttemplate.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class BlankFragment2 extends Fragment {


    public BlankFragment2() {
        // Required empty public constructor
    }

    private static BlankFragment fragment2;

    public static BlankFragment getInStance()
    {
        if(fragment2 ==null)
            fragment2 = new BlankFragment();
        return fragment2;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment2
        return inflater.inflate(R.layout.fragment_blank_fragment2, container, false);
    }

}
