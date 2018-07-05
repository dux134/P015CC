package com.example.dux.p015cc.home;

public class NewsAndAlertDataModel {
    private String notificationTitle;
    private String notificationDescription;
    private String notificationUrl;
    private String notificationType;

    public NewsAndAlertDataModel(String mNotificationTitle,String mNotificationDescription,String mNotificationUrl,String mNotificationType) {
        notificationDescription = mNotificationDescription;
        notificationTitle = mNotificationTitle;
        notificationUrl = mNotificationUrl;
        notificationType = mNotificationType;
    }

    public String getNotificationDescription() {
        return notificationDescription;
    }

    public String getNotificationTitle() {
        return notificationTitle;
    }

    public String getNotificationUrl() {
        return notificationUrl;
    }

    public String getNotificationType() {
        return notificationType;
    }
}
