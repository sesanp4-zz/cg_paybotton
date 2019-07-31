/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.handlers;

import com.google.gson.JsonObject;
import com.util.Utilities;
import javax.enterprise.context.RequestScoped;

/**
 *
 * @author centricgateway
 */
@RequestScoped
public class PaymentLinkHandler {
    
    Utilities util = new Utilities();
    JsonObject obj;
    
    public String generateLink(String publickey){
        
      obj = util.getMerchantInfo(publickey);
      
      if(obj.has("payload")){
          
          String modal = util.getAppProperties().getProperty("paymentmodal");
          String link=modal.concat("/?m="+publickey);
          obj = new JsonObject();
          obj.addProperty("code", "00");
          obj.addProperty("message", "successful");
          obj.addProperty("paymentlink", link);         
          return obj.toString();
      }else{
        obj = new JsonObject();
        obj.addProperty("code", "S7");
        obj.addProperty("message", "operation failed");
        return obj.toString();
      }
        
    }
    
    
}
