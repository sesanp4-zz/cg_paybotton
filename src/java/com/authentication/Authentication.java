/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.authentication;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

/**
 *
 * @author centricgateway
 */
public class Authentication {
    
    JsonObject obj;
    CloseableHttpClient client = HttpClients.createDefault();
    HttpPost post;
    HttpGet get;
    CloseableHttpResponse response;
    Gson gson = new Gson();
    
//    public boolean authenyicateMerchant(String mid){
//        
//          post = new HttpPost("");
//          post.setHeader("Authorization", "Bearer "+obj.get("access_token").getAsString());
//          post.setHeader("Content-Type", "application/json");
//          StringEntity ent = new StringEntity(gson.toJson(cr));
//          System.out.println("request........"+gson.toJson(cr));
//          post.setEntity(ent);
//          response=client.execute(post);
//          String msg = EntityUtils.toString(response.getEntity());
//          
//          obj = new JsonParser().parse(msg).getAsJsonObject();
//          obj = obj.get("transaction").getAsJsonObject();
//          System.out.println(obj);
//    
//    }
    
    
}
