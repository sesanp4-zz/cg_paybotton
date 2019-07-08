/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.reponses;

/**
 *
 * @author centricgateway
 */
public class TransactionResponse {
    

    String code,message,reference,redirecturl,linkingref;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getRedirecturl() {
        return redirecturl;
    }

    public void setRedirecturl(String redirecturl) {
        this.redirecturl = redirecturl;
    }

    public String getLinkingref() {
        return linkingref;
    }

    public void setLinkingref(String linkingref) {
        this.linkingref = linkingref;
    }
    
}

    

