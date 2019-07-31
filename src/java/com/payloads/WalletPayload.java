/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.payloads;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import javax.enterprise.context.RequestScoped;

/**
 *
 * @author centricgateway
 */
@RequestScoped
@JsonIgnoreProperties(ignoreUnknown = true)
public class WalletPayload {
    
    Transaction transaction;
    WalletSource source;
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

    public WalletSource getSource() {
        return source;
    }

    public void setSource(WalletSource source) {
        this.source = source;
    }
    
    
    
    
}
