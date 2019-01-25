package com.uber.test.data.service;

import android.os.AsyncTask;
import com.uber.test.data.api.SearchImagesApi;
import com.uber.test.data.model.SearchResponsePO;
import com.uber.test.data.parser.APIJSONParser;

import org.json.JSONObject;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Naresh on 20/01/19.
 */
public class SearchImagesAsyncTask extends AsyncTask<Void, SearchResponsePO, SearchResponsePO> {
    private static final String TAG = SearchImagesAsyncTask.class.getSimpleName();

    private AsyncTaskListener asyncTaskListener;
    private String searchText;
    private int pageNo;

    public SearchImagesAsyncTask(AsyncTaskListener asyncTaskListener,
                                 String searchText, int pageNo) {
        this.asyncTaskListener = asyncTaskListener;
        this.searchText = searchText;
        this.pageNo = pageNo;
    }

    @Override
    protected SearchResponsePO doInBackground(Void... params) {
        SearchImagesApi searchImagesApi = new SearchImagesApi();
        SearchResponsePO searchResponsePO = null;
        try {
            JSONObject jsonObject = searchImagesApi.getImages(searchText, pageNo);
            APIJSONParser jsonParser = new APIJSONParser();
            searchResponsePO = jsonParser.parseSearchListResponse(jsonObject);

        } catch (ClassCastException | NoSuchAlgorithmException | IOException e) {
            e.printStackTrace();
        }
        return searchResponsePO;
    }

    @Override
    protected void onPostExecute(SearchResponsePO searchResponsePO) {
        super.onPostExecute(searchResponsePO);
        asyncTaskListener.onTaskCompleted(searchResponsePO, TAG);
    }
}
