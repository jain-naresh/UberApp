package com.uber.test.data.parser;

import com.uber.test.data.model.FlickrImagePO;
import com.uber.test.data.model.SearchResponsePO;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Naresh on 20/01/19.
 */
public class APIJSONParser {
    private static final String TAG = APIJSONParser.class.getSimpleName();

    private static final String KEY_PHOTOS_JSON = "photos";
    private static final String KEY_STAT = "stat";
    private static final String KEY_OK = "ok";
    private static final String KEY_PAGES = "pages";
    private static final String KEY_PHOTOS_ARRAY = "photo";
    private static final String KEY_PHOTO_ID = "id";
    private static final String KEY_OWNER = "owner";
    private static final String KEY_SECRET = "secret";
    private static final String KEY_SERVER = "server";
    private static final String KEY_FARM = "farm";
    private static final String KEY_TITLE = "title";

    public SearchResponsePO parseSearchListResponse(JSONObject jsonObject) {
        SearchResponsePO searchResponsePO = null;
        try {
            if (jsonObject.has(KEY_STAT)) {
                if (jsonObject.getString(KEY_STAT).contentEquals(KEY_OK)) {
                    JSONObject resultObject = jsonObject.getJSONObject(KEY_PHOTOS_JSON);
                    JSONArray jsonArray = resultObject.getJSONArray(KEY_PHOTOS_ARRAY);
                    searchResponsePO = new SearchResponsePO(
                            resultObject.getInt(KEY_PAGES),
                            parseSearchArray(jsonArray)
                    );
                }
            } else {
                return searchResponsePO;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return searchResponsePO;
    }

    private List<FlickrImagePO> parseSearchArray(JSONArray jsonArray) {
        List<FlickrImagePO> flickrPhotoList = new ArrayList<>();
        try {
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObj = jsonArray.getJSONObject(i);
                FlickrImagePO flickrImagePO = new FlickrImagePO(
                        jsonObj.getString(KEY_PHOTO_ID),
                        jsonObj.getString(KEY_OWNER),
                        jsonObj.getString(KEY_SECRET),
                        jsonObj.getString(KEY_SERVER),
                        jsonObj.getInt(KEY_FARM),
                        jsonObj.getString(KEY_TITLE));
                flickrPhotoList.add(flickrImagePO);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return flickrPhotoList;
    }
}
