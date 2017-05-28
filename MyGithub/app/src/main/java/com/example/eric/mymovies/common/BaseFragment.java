package com.example.eric.mymovies.common;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

public class BaseFragment extends Fragment {
    protected void hideKeyboard() {
        if (getActivity() == null || !isAdded()) {
            return;
        }
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context
                .INPUT_METHOD_SERVICE);
        View rootView = getView();
        if (rootView != null) {
            Log.v(this.getClass().getSimpleName(), "root view not null " + rootView);
            rootView.requestFocus();
            imm.hideSoftInputFromWindow(rootView.getWindowToken(), 0);
        }
    }
}
