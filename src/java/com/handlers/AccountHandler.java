/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.handlers;


import com.acct.operations.AccountActions;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.model.Account;
import com.model.InitiateSource;
import com.model.InitiateTransactionObject;
import com.model.Order;
import com.model.TransactionInitiationPayload;
import com.model.TransactionInitiationPayloadProxy;
import com.model.TransactionValidationPayload;
import com.model.Transactionvalidationproxy;
import com.model.ValidateTransactionObject;
import com.model.ValidationSource;
import com.util.Utilities;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.core.Response;
import javax.xml.soap.SOAPException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;

/**
 *
 * @author centricgateway
 */
@RequestScoped
public class AccountHandler {
    
     HttpPost post;
    CloseableHttpResponse response;
    CloseableHttpClient client = HttpClients.createDefault();;
    JsonObject obj,obj2;
    Gson gson= new Gson();
    StringEntity ent;
    
    Utilities util = new Utilities();
    
    @Inject
    AccountActions actions;
    
    public String initiate(TransactionInitiationPayloadProxy proxy){
         try{
                
                obj = new JsonParser().parse(util.getThirdPartyApi()).getAsJsonObject();
                System.out.println(" ====== Token Response ====== "+obj);
                
                 // Construct the object
                 
                 TransactionInitiationPayload payload = new TransactionInitiationPayload();
                 InitiateTransactionObject transaction = new InitiateTransactionObject();
                 Order order = new Order();
                 InitiateSource source = new InitiateSource();
                 Account account = new Account();
                 
                 account.setSender(proxy.getSender());
                 account.setSenderbankcode(proxy.getBankcode());
                 
                 source.setAccount(account);
                 source.setFirstname(proxy.getFullname());
                 source.setOperation(util.getAppProperties().getProperty("account_operation"));
                 
                 order.setAmount(proxy.getAmount());
                 order.setCountry(proxy.getCountry());
                 order.setCurrency(proxy.getCurrency());
                 order.setDescription(proxy.getDescription());
                 
                 transaction.setReference(proxy.getReference());
                 
                 payload.setOrder(order);
                 payload.setPublickey(util.getAppProperties().getProperty("publickey"));
                 payload.setSource(source);
                 payload.setTransaction(transaction);
                 
                if(!obj.has("access_token")){
                    obj2 = new JsonObject();
                    obj2.addProperty("code", "S7");
                    obj2.addProperty("message", "operation failed");
                    System.out.println("cuase......"+obj);
                   return obj2.toString();
                }
             
                System.out.println(" ======== object sending ======="+gson.toJson(payload));
                
                post = new HttpPost(util.getAppProperties().getProperty("account_initiate_endpoint"));
                post.setHeader("Authorization", "Bearer "+obj.get("access_token").getAsString());
                post.setHeader("Content-Type", "application/json");
                ent = new StringEntity(gson.toJson(payload));
                post.setEntity(ent);
                response=client.execute(post);
                String msg = EntityUtils.toString(response.getEntity());
                obj = new JsonParser().parse(msg).getAsJsonObject();
                System.out.println("response from core..."+obj);
                
                return obj.toString();
             
         }catch(Exception e){
           obj = new JsonObject();
           obj.addProperty("code", "S7");
           obj.addProperty("message", "operation failed"); 
           System.out.println(" ====== something is wrong ===== "+e.getMessage());
           return obj.toString();
         }
    } 
    
    public String validate(Transactionvalidationproxy proxy){
    
        try{
        
            obj = new JsonParser().parse(util.getThirdPartyApi()).getAsJsonObject();
            System.out.println(" ====== Token Response ====== "+obj);
            
            TransactionValidationPayload payload = new TransactionValidationPayload();
            ValidationSource source = new ValidationSource();
            ValidateTransactionObject transaction = new ValidateTransactionObject();

            source.setOperation("pg_account_charge_validate");
            transaction.setLinkingreference(proxy.getLinkingreference());
            transaction.setOtp(proxy.getOtp());
            payload.setPublickey(util.getAppProperties().getProperty("publickey"));
            payload.setSource(source);
            payload.setTransaction(transaction);
            
            if(!obj.has("access_token")){
                    obj2 = new JsonObject();
                    obj2.addProperty("code", "S7");
                    obj2.addProperty("message", "operation failed");
                    System.out.println("cuase......"+obj);
                   return obj2.toString();
                }
             
                System.out.println(" ======== object sending ======="+gson.toJson(payload));
                
                post = new HttpPost(util.getAppProperties().getProperty("account_otp_validation_endpoint"));
                post.setHeader("Authorization", "Bearer "+obj.get("access_token").getAsString());
                post.setHeader("Content-Type", "application/json");
                ent = new StringEntity(gson.toJson(payload));
                post.setEntity(ent);
                response=client.execute(post);
                String msg = EntityUtils.toString(response.getEntity());
                obj = new JsonParser().parse(msg).getAsJsonObject();
                System.out.println("response from core..."+obj);
                
                return obj.toString();
            
        }catch(Exception e){
            obj = new JsonObject();
           obj.addProperty("code", "S7");
           obj.addProperty("message", "operation failed"); 
           System.out.println(" ====== something is wrong ===== "+e.getMessage());
           return obj.toString();
        }
        
    } 
    
    
    public Response getBankList(){
         try{
             System.out.println("======checking=======");
            return actions.getLiveBankList();
         }catch(Exception e){
             obj = new JsonObject();
             obj.addProperty("code", "S7");
             obj.addProperty("message", "operation failed");
             System.out.println("======= cause ======="+e.getMessage());
              return Response.status(Response.Status.BAD_REQUEST).entity(obj.toString()).build();
         }
    }
    
}
