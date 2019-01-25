package com.uber.test.data.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Naresh on 20/01/19.
 */
public class SearchResponsePO implements Serializable {
    private static final String TAG = SearchResponsePO.class.getSimpleName();

    private int pages;
    private List<FlickrImagePO> flickrPhotoList;

    public SearchResponsePO(int pages, List<FlickrImagePO> flickrPhotoList) {
        this.pages = pages;
        this.flickrPhotoList = flickrPhotoList;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public List<FlickrImagePO> getFlickrPhotoList() {
        return flickrPhotoList;
    }

    public void setFlickrPhotoList(List<FlickrImagePO> flickrPhotoList) {
        this.flickrPhotoList = flickrPhotoList;
    }
}
