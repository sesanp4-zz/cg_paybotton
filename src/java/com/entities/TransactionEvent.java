
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.entities;

import java.io.Serializable;
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
@Table(name = "TransactionEvent")
public class TransactionEvent {
    
 //   private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    
    @Column(name="gateway")
    String gateway;
    
    @Column(name="gatewayMessage")
    String gatewayMessage;
    
    @Column(name="gatewayCode")
    String gatewayCode;
    
    @Column(name="transactionRef")
    String  transactionRef;
    
    @Column(name="gatewayref")
    String  gatewayref;
    
//    @Column(name="source")
//    String source;
//    
//    @Column(name="sourcename")
//    String sourcename;
//    
//    @Column(name="sourcenumber")
//    String sourcenumber;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGateway() {
        return gateway;
    }

    public void setGateway(String gateway) {
        this.gateway = gateway;
    }

    public String getGatewayMessage() {
        return gatewayMessage;
    }

    public void setGatewayMessage(String gatewayMessage) {
        this.gatewayMessage = gatewayMessage;
    }

    public String getGatewayCode() {
        return gatewayCode;
    }

    public void setGatewayCode(String gatewayCode) {
        this.gatewayCode = gatewayCode;
    }

    public String getTransactionRef() {
        return transactionRef;
    }

    public void setTransactionRef(String transactionRef) {
        this.transactionRef = transactionRef;
    }

    public String getGatewayref() {
        return gatewayref;
    }

    public void setGatewayref(String gatewayref) {
        this.gatewayref = gatewayref;
    }

    
    
}
