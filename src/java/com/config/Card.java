/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.config;

/**
 *
 * @author centricgateway
 */
public class Card {
    
    MasterCard master;
    
    VisaCard visa;
    
    VerveCard verve;

    public MasterCard getMaster() {
        return master;
    }

    public void setMaster(MasterCard master) {
        this.master = master;
    }

    public VisaCard getVisa() {
        return visa;
    }

    public void setVisa(VisaCard visa) {
        this.visa = visa;
    }

    public VerveCard getVerve() {
        return verve;
    }

    public void setVerve(VerveCard verve) {
        this.verve = verve;
    }
    
       
         
    
}
