/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author centricgateway
 */
public class Configuration {
    
    
    
    Account acct;
    
    
 public static void main(String[] args) {
      
         List o = Configuration.loadSourceTypeConfig("visacard");
         System.out.println(o);
         System.out.println(o.get(0).toString());
      
    }
    
    public static List loadSourceTypeConfig(String type){
    
        switch (type) {
            case "mastercard":
                return Arrays.asList(MasterCard.values());
            case "visacard":
                return Arrays.asList(VisaCard.values());
            default:
                return Arrays.asList(VerveCard.values()); 
        }
           
        
    
    }
    
    
}
