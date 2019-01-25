package com.uber.test.data.service;

/**
 * Created by Naresh on 20/01/19.
 */
public interface AsyncTaskListener<T, P> {
    void onTaskCompleted(T... params);
    void onProgressUpdate(P... params);
}
