/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.payloads;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.Map;

/**
 *
 * @author centricgateway
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class WalletSource {
    String operation;
    String name;
    String mobile;
    String email;
    Wallet metadata;

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Wallet getMetadata() {
        return metadata;
    }

    public void setMetadata(Wallet metadata) {
        this.metadata = metadata;
    }

   
   

}
