/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.handlers;

import com.entities.Event;
import com.entities.Subscription;
import com.entities.WebhookEvent;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.model.SubscriptionObject;
import com.util.Dao;
import com.util.DateTimeFormater;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.enterprise.context.RequestScoped;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

/**
 *
 * @author centricgateway
 */
@RequestScoped
public class WebhookHandler {
    
      Dao dao = new Dao();
    
        ExecutorService executorservice  = Executors.newFixedThreadPool(1);
        JsonObject obj,obj2;   
        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost post;
        HttpGet get;
        CloseableHttpResponse response;
        Gson gson = new Gson();
    
    
    
    public String subscribe(SubscriptionObject o){
      Subscription sub = new Subscription();
      sub.setNotificationurl(o.getNotificationUrl());
      sub.setMerchantkey(o.getMerchantkey());
      sub.setDatetime(DateTimeFormater.getRealTimeFormat());
      int status = dao.addObject(sub);
      if(status>0){
       obj = new JsonObject();
       obj.addProperty("code", "00");
       obj.addProperty("message", "success");
      }else{
        obj = new JsonObject();
        obj.addProperty("code", "00");
        obj.addProperty("message", "success");
      }
      return obj.toString();
    }
    
    public void sendNotification(String merchantKey,WebhookEvent event){ 
       // String eventtype,String eventmessage,String transactionReference
       try {
          executorservice.submit(()->{
              
             try{
                 String sub = new Dao().getSubscription(merchantKey);
                 obj = new JsonParser().parse(sub).getAsJsonObject();
                 System.out.println(" ===== The subscription Object is ====="+sub);
                 System.out.println(" ====== The Event is ====="+gson.toJson(event));
                 event.setGeneratedAt(DateTimeFormater.getRealTimeFormat());
                 event.setSubscriptionId(obj.get("id").getAsString());
                 System.out.println(" ======== After updating the event is ======"+gson.toJson(event));
                 System.out.println(" ====== Adding Event ======");
                  dao.addObject(event);
                 System.out.println(" ======= Event Added ===== ");
                 obj = new JsonParser().parse(sub).getAsJsonObject();
                 post = new HttpPost(obj.get("notificationurl").getAsString());
                 post.setHeader("Content-Type", "application/json");
                StringEntity ent = new StringEntity(gson.toJson(event));
                post.setEntity(ent);
                 response=client.execute(post);
                System.out.println("response code...."+response.getStatusLine());
             }catch(Exception e){
                 System.out.println("an error occured....."+e.getMessage());
             }
          });
       }catch(Exception e){
         obj =new JsonObject();
         obj.addProperty("code", "S7");
         obj.addProperty("message", "operation failed");
       }
       
    
    }
    
    
}
