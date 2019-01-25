package com.uber.test.data.api;

import org.json.JSONObject;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Naresh on 20/01/19.
 */
public class SearchImagesApi extends Api {
    private static final String TAG = SearchImagesApi.class.getSimpleName();
    private static final String SEARCH_TEXT_KEY = "&text=";
    private static final String SEARCH_PAGE_KEY = "&page=";

    public SearchImagesApi() {
        super();
    }

    // Getting Images
    public JSONObject getImages(String searchText, int pageNo) throws ClassCastException, NoSuchAlgorithmException, IOException {
        setUri(getImagesUrl(searchText, pageNo));
        JSONObject result;
        Object object = execute(RequestMethod.GET, ContentType.APPLICATION_JSON, null);
        try {
            result = (JSONObject) object;
        } catch (ClassCastException e) {
            e.printStackTrace();
            throw e;
        }
        return result;
    }

    private String getImagesUrl(String searchText, int pageNo){
        String url = MAIN_BASE_URL;
        url += GET_SEARCH_IMAGES_URL;
        url += SEARCH_TEXT_KEY + searchText;
        url += SEARCH_PAGE_KEY + pageNo;
        return url;
    }
}
