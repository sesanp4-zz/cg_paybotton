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
public class TransactionValidationPayload {
    String publickey;
    ValidateTransactionObject transaction;
    ValidationSource source;

    public String getPublickey() {
        return publickey;
    }

    public void setPublickey(String publickey) {
        this.publickey = publickey;
    }

    public ValidateTransactionObject getTransaction() {
        return transaction;
    }

    public void setTransaction(ValidateTransactionObject transaction) {
        this.transaction = transaction;
    }

    public ValidationSource getSource() {
        return source;
    }

    public void setSource(ValidationSource source) {
        this.source = source;
    }

}
