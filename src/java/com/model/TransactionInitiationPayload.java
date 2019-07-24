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
public class TransactionInitiationPayload {
    
     String publickey;
    InitiateTransactionObject transaction;
    InitiateSource source;
    Order order;

    public String getPublickey() {
        return publickey;
    }

    public void setPublickey(String publickey) {
        this.publickey = publickey;
    }

    public InitiateTransactionObject getTransaction() {
        return transaction;
    }

    public void setTransaction(InitiateTransactionObject transaction) {
        this.transaction = transaction;
    }

    public InitiateSource getSource() {
        return source;
    }

    public void setSource(InitiateSource source) {
        this.source = source;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
    

    
}
