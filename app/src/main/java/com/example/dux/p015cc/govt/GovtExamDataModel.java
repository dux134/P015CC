package com.example.dux.p015cc.govt;

public class GovtExamDataModel {
    private String imageUrl;
    private String title;
    private String description;
    private String examDate;

    public GovtExamDataModel(String mTitle,String mDescription,String mExamDate,String mImageUrl) {
        imageUrl = mImageUrl;
        title = mTitle;
        description = mDescription;
        examDate = mExamDate;
    }

    public String getDescription() {
        return description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getTitle() {
        return title;
    }

    public String getExamDate() {
        return examDate;
    }
}
