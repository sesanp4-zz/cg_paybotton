/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.payload.objects;

/**
 *
 * @author centricgateway
 */
public class ValidateMandateCreationObject {
    String mandatecode, bankcode,  otp, amount;

    public String getMandatecode() {
        return mandatecode;
    }

    public void setMandatecode(String mandatecode) {
        this.mandatecode = mandatecode;
    }

    public String getBankcode() {
        return bankcode;
    }

    public void setBankcode(String bankcode) {
        this.bankcode = bankcode;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
    
    
    
}
