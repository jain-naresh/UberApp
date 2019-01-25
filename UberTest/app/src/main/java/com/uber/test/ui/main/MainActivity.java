package com.uber.test.ui.main;

import android.Manifest;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.uber.test.R;
import com.uber.test.ui.base.BaseActivity;
import com.uber.test.ui.common.UserPermission;
import com.uber.test.utils.FragmentUtil;

public class MainActivity extends BaseActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

    private UserPermission mStoragePermissions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkStoragePermissions();
    }

    private void initializeFragment() {
        if (getFragmentByTag(FragmentUtil.getTag(HomeFragment.class)) == null) {
            HomeFragment homeFragment = new HomeFragment();
            homeFragment.setArguments(getIntent().getExtras());
            addFragment(R.id.fragmentContainer, homeFragment, FragmentUtil.getTag(homeFragment), false);
        }
    }
    private void checkStoragePermissions() {
        if (mStoragePermissions == null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                mStoragePermissions = new UserPermission(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 165, new UserPermission.IPermissionsService() {
                    @Override
                    public void onPermissionGranted() {
                        initializeFragment();
                    }

                    @Override
                    public void onPermissionDenied() {
                        finish();
                    }
                });
            } else {
                initializeFragment();
            }
        }

        if (mStoragePermissions.hasPermissionsGranted()) {
            initializeFragment();
        } else {
            mStoragePermissions.requestPermission();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (mStoragePermissions != null) {
            mStoragePermissions.processResult(requestCode, permissions, grantResults);
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
}
