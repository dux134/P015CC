package com.example.dux.p015cc.ccsu;

public class CCSUnotesDataModel {
    private String title;
    private String imageUrl;

    public CCSUnotesDataModel(String mTitle,String mImageUrl) {
        title = mTitle;
        imageUrl = mImageUrl;
    }

    public String getTitle() {
        return title;
    }

    public String getImageUrl() {
        return imageUrl;
    }

}
