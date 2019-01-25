package com.uber.test.utils;

import android.content.Context;
import android.content.res.Resources;
import android.support.v4.app.Fragment;
import com.uber.test.ui.base.BaseFragment;

public class FragmentUtil {
    public static String getTag(Fragment fragment) {
        return fragment.getClass().getSimpleName();
    }

    public static String getTag(Class<? extends Fragment> fragmentClass) {
        return fragmentClass.getSimpleName();
    }

    public static Context getActivity(Fragment fragment) {
        while (fragment.getParentFragment() != null) {
            fragment = fragment.getParentFragment();
        }

        if (fragment instanceof BaseFragment) {
            return ((BaseFragment) fragment).getContext();

        } else {
            return fragment.getActivity();
        }
    }


    public static Resources getResources(Fragment fragment) {
        return getActivity(fragment).getResources();
    }

    public static String getString(Fragment fragment, int res) {
        return getResources(fragment).getString(res);
    }
}
