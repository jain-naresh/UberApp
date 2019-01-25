package com.uber.test.data.model;

/**
 * Created by Naresh on 20/01/19.
 */
public class FlickrImagePO {
    private static final String TAG = FlickrImagePO.class.getSimpleName();

    private String id;
    private String owner;
    private String secret;
    private String server;
    private int farm;
    private String title;

    public FlickrImagePO(String id, String owner, String secret, String server, int farm, String title) {
        this.id = id;
        this.owner = owner;
        this.secret = secret;
        this.server = server;
        this.farm = farm;
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public int getFarm() {
        return farm;
    }

    public void setFarm(int farm) {
        this.farm = farm;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageURL(){
        // URL Format-> http://farm{farm}.static.flickr.com/{server}/{id}_{secret}.jpg
        String imageUrl = "http://farm{farm}.static.flickr.com/{server}/{id}_{secret}.jpg";
        imageUrl = imageUrl.replace("{farm}", String.valueOf(farm));
        imageUrl = imageUrl.replace("{server}", server);
        imageUrl = imageUrl.replace("{id}", id);
        imageUrl = imageUrl.replace("{secret}", secret);
        return imageUrl;
    }
}
