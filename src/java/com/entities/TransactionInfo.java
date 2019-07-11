/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.entities;

import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *
 * @author centricgateway
 */
@Entity
@Table(name = "TransactionInfo")
public class TransactionInfo{

  //  private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id; 

    
    @Column(name="public_key")
    String public_key;   
 
    
    @Column(name="reference")
    String reference;

    
    @Column(name="description")
    String description;
    
    @Column(name="amount")
    String  amount;
        
    @Column(name="clientAppCode")
    String  clientAppCode;
    
    
    @Column(name="datetime")
    String  datetime;

    
    @Column(name="callbackurl")
    String  callbackurl;
    
    @Column(name="redirecturl")
    String  redirecturl;
    
    @Column(name="channel")
    String channel;
    
    @Column(name="channelType")
    String channelType;
    
    @Column(name="number")
    String number;
    
    
    @Column(name="sourceIP")
    String sourceIP;
    
    @Column(name="deviceType")
    String deviceType;
    
    
    
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name= "transactionEvent_fk")       
    TransactionEvent transactionEvent;
    

    public String getRedirecturl() {
        return redirecturl;
    }

    public void setRedirecturl(String redirecturl) {
        this.redirecturl = redirecturl;
    }

    public String getPublic_key() {
        return public_key;
    }

    public void setPublic_key(String public_key) {
        this.public_key = public_key;
    }
    
   
    
    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

   
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

   

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    
    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }


    public String getCallbackurl() {
        return callbackurl;
    }

    public void setCallbackurl(String callbackurl) {
        this.callbackurl = callbackurl;
    }
    
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getClientAppCode() {
        return clientAppCode;
    }

    public void setClientAppCode(String clientAppCode) {
        this.clientAppCode = clientAppCode;
    }

    public TransactionEvent getTransactionEvent() {
        return transactionEvent;
    }

    public void setTransactionEvent(TransactionEvent transactionEvent) {
        this.transactionEvent = transactionEvent;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getChannelType() {
        return channelType;
    }

    public void setChannelType(String channelType) {
        this.channelType = channelType;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getSourceIP() {
        return sourceIP;
    }

    public void setSourceIP(String sourceIP) {
        this.sourceIP = sourceIP;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    
    
    
}
