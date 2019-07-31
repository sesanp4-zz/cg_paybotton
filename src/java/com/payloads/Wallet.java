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
public class Wallet {
    
     String walletname,amountcontrol,settlementaccount,amount, walletdaysactive,walletminutesactive,settlementaccountname,extradata;

    public String getWalletname() {
        return walletname;
    }

    public void setWalletname(String walletname) {
        this.walletname = walletname;
    }

    public String getAmountcontrol() {
        return amountcontrol;
    }

    public void setAmountcontrol(String amountcontrol) {
        this.amountcontrol = amountcontrol;
    }

    public String getSettlementaccount() {
        return settlementaccount;
    }

    public void setSettlementaccount(String settlementaccount) {
        this.settlementaccount = settlementaccount;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getWalletdaysactive() {
        return walletdaysactive;
    }

    public void setWalletdaysactive(String walletdaysactive) {
        this.walletdaysactive = walletdaysactive;
    }

    public String getWalletminutesactive() {
        return walletminutesactive;
    }

    public void setWalletminutesactive(String walletminutesactive) {
        this.walletminutesactive = walletminutesactive;
    }

    public String getSettlementaccountname() {
        return settlementaccountname;
    }

    public void setSettlementaccountname(String settlementaccountname) {
        this.settlementaccountname = settlementaccountname;
    }

    public String getExtradata() {
        return extradata;
    }

    public void setExtradata(String extradata) {
        this.extradata = extradata;
    }
     
     
    
}
