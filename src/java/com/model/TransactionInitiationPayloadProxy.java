/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 *
 * @author centricgateway
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class TransactionInitiationPayloadProxy {
    
    String reference;
    String fullname;
    String sender;
    String bankcode;
    String currency;
    String description;
    String country;
    String amount;
    
    
  //  String fullname, email, mobile, public_key, reference, currency, description,
  //  country,amount,clientappcode, redirecturl, callbackurl, type,channelType,deviceType,sourceIP,fee;

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getBankcode() {
        return bankcode;
    }

    public void setBankcode(String bankcode) {
        this.bankcode = bankcode;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
    
    
    
}
