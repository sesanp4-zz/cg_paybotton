/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.card.operations;

/**
 *
 * @author centricgateway
 */
public class ValidateOTPRequest {
    String publickey;
    ValidateOTPTransaction transaction;
    ValidateOTPSource source;

    public String getPublickey() {
        return publickey;
    }

    public void setPublickey(String publickey) {
        this.publickey = publickey;
    }

    public ValidateOTPTransaction getTransaction() {
        return transaction;
    }

    public void setTransaction(ValidateOTPTransaction transaction) {
        this.transaction = transaction;
    }

    public ValidateOTPSource getSource() {
        return source;
    }

    public void setSource(ValidateOTPSource source) {
        this.source = source;
    }
    
     
    
}
