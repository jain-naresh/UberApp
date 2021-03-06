package com.uber.test.utils;

import android.os.AsyncTask;

public class AsyncTaskUtil {
    private static final String TAG = AsyncTaskUtil.class.getSimpleName();

    public static <Params, T extends AsyncTask<Params, ?, ?>> boolean executeAsyncTask(T asyncTask,boolean runParallel, Params... params) {
        if (params.length == 0) {
            params = null;
        }

        if (runParallel) {
            asyncTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, params);

        } else {
            asyncTask.execute(params);
        }
        return true;
    }
}
