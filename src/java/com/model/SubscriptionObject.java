/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.model;

/**
 *
 * @author centricgateway
 */
public class SubscriptionObject {
     String notificationUrl;   
    String merchantkey;

    public String getNotificationUrl() {
        return notificationUrl;
    }

    public void setNotificationUrl(String notificationUrl) {
        this.notificationUrl = notificationUrl;
    }

    public String getMerchantkey() {
        return merchantkey;
    }

    public void setMerchantkey(String merchantkey) {
        this.merchantkey = merchantkey;
    }
    
}
