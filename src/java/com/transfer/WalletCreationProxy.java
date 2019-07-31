/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.transfer;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 *
 * @author centricgateway
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class WalletCreationProxy {
    String Walletname,amountcontrol,walletdaysactive,walletminutesactive,extradata,settlementaccount,settlementaccountname;

    public String getWalletname() {
        return Walletname;
    }

    public void setWalletname(String Walletname) {
        this.Walletname = Walletname;
    }

    public String getAmountcontrol() {
        return amountcontrol;
    }

    public void setAmountcontrol(String amountcontrol) {
        this.amountcontrol = amountcontrol;
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

    public String getExtradata() {
        return extradata;
    }

    public void setExtradata(String extradata) {
        this.extradata = extradata;
    }

    public String getSettlementaccount() {
        return settlementaccount;
    }

    public void setSettlementaccount(String settlementaccount) {
        this.settlementaccount = settlementaccount;
    }

    public String getSettlementaccountname() {
        return settlementaccountname;
    }

    public void setSettlementaccountname(String settlementaccountname) {
        this.settlementaccountname = settlementaccountname;
    }
    
    
}
