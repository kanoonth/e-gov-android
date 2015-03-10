package com.kanoon.egov.models;

/**
 * Created by Aof on 2/28/2015 AD.
 */
public class Document {
    public long id;
    public String name;
    public String description;
    public String photo_path;

    public String getPhotoFileName() {
        return this.photo_path.replace("/images/upload/","");
    }
}
