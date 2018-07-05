package com.example.dux.p015cc.home;

import android.widget.ImageView;
import android.widget.TextView;

import java.security.PrivateKey;

public class MoreWebsiteDataModel {
    private String websiteImageUrl;
    private String websiteName;
    private String websiteLinkUrl;

    public MoreWebsiteDataModel(String name,String linkUrl,String imageUrl) {
        websiteImageUrl = imageUrl;
        websiteLinkUrl = linkUrl;
        websiteName = name;
    }

    public String getWebsiteImageUrl() {
        return websiteImageUrl;
    }

    public String getWebsiteLinkUrl() {
        return websiteLinkUrl;
    }

    public String getWebsiteName() {
        return websiteName;
    }
}
