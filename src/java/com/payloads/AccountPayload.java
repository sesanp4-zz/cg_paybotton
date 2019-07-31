/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.payloads;

/**
 *
 * @author centricgateway
 */
public class AccountPayload {
    Transaction transaction;
    AccountSource source;
    Order order;
    String publickey;

    public Transaction getTransaction() {
        return transaction;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public String getPublickey() {
        return publickey;
    }

    public void setPublickey(String publickey) {
        this.publickey = publickey;
    }

    public AccountSource getSource() {
        return source;
    }

    public void setSource(AccountSource source) {
        this.source = source;
    }
    
    
    
}
