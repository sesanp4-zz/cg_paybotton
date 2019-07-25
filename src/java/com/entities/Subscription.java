/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author centricgateway
 */
@Entity
@Table(name="Subscription")
public class Subscription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    Long id;
    
    @Column(name="notificationurl")
    String notificationurl;
   
    @Column(name="merchantkey")
    String merchantkey;
    
    @Column(name="datetime")
    String datetime;

    public String getNotificationurl() {
        return notificationurl;
    }

    public void setNotificationurl(String notificationurl) {
        this.notificationurl = notificationurl;
    }

    public String getMerchantkey() {
        return merchantkey;
    }

    public void setMerchantkey(String merchantkey) {
        this.merchantkey = merchantkey;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    
    
}
