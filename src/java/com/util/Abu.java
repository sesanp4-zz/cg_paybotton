/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.util;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

/**
 *
 * @author centricgateway
 */
public class Abu {
    
    
    JsonObject obj,obj2;   
    CloseableHttpClient client = HttpClients.createDefault();
    HttpPost post;
    HttpGet get;
    CloseableHttpResponse response;
    Gson gson = new Gson();
    
    public String send(String cardnumber){
      try{
          String url ="https://lookup.binlist.net/".concat(cardnumber);
          get = new HttpGet(url);
          response = client.execute(get);
          String data = EntityUtils.toString(response.getEntity());
          obj = new JsonParser().parse(data).getAsJsonObject();
          System.out.println("response from core..."+obj);
          JsonObject finalresponse = new JsonObject();
          JsonObject payload = new JsonObject();
          payload.addProperty("scheme", obj.get("scheme").getAsString());
          payload.addProperty("type", obj.get("type").getAsString());
          payload.addProperty("bank", obj.get("bank").getAsJsonObject().get("name").getAsString());
          finalresponse.addProperty("success", "true");
          finalresponse.add("payload", payload);
          return finalresponse.toString();
         
      }catch(Exception e){
        obj = new JsonObject();
        obj.addProperty("code", "S7");
        obj.addProperty("message", "failed");
          System.out.println("failed....cause.."+e.getMessage());
        return obj.toString();
      }finally{
          try {
              client.close();
          } catch (IOException ex) {
              Logger.getLogger(Abu.class.getName()).log(Level.SEVERE, null, ex);
          }
              }
    }
    
    public static void main(String[] args) {
        
        
        System.out.println(new Abu().send("45717360"));
        
        
    }
    
}
