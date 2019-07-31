/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 *
 * @author centricgateway
 */
public class Account {
     String sender;
    String senderbankcode;
    
    //@JsonIgnore
    String name;

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getSenderbankcode() {
        return senderbankcode;
    }

    public void setSenderbankcode(String senderbankcode) {
        this.senderbankcode = senderbankcode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    
    
}
