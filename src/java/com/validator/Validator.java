/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.validator;

import com.card.operations.CardAPI;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.util.Utilities;
import java.io.IOException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;

/**
 *
 * @author centricgateway
 */
public class Validator {
    
   CardAPI cardapi =  new CardAPI();
    Utilities util = new Utilities();
   JsonObject obj; 
    
    public boolean validateMerchant(String public_key,String currency,String amount) throws IOException{
          System.out.println("sending to fetch merchant info....");
          obj = util.fectchMerchantInfo(public_key);
          System.out.println("response abt merchant info..."+obj);
        if(obj.get("code").getAsString().equals("00")){            
            Double min_amount = Double.parseDouble(obj.get("min_amount").getAsString());
            Double max_amount = Double.parseDouble(obj.get("max_amount").getAsString());
            String currencyCode = obj.get("currency").getAsString();
            Double real_amount=Double.parseDouble(amount);
          if(real_amount>=min_amount&&real_amount<=max_amount&&currencyCode.equals(currency)){
                return true;
            }else{
                return false;
            }
        
        }else{
             return false;
        }
        
    }
    
    
    
    
}
