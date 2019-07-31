/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.handlers;


import com.acct.operations.AccountActions;
import com.entities.TransactionEvent;
import com.entities.TransactionInfo;
import com.entities.UserInfo;
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
import com.util.Dao;
import com.util.Utilities;
import com.validator.Validator;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.core.Response;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

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
    
    Dao dao = new Dao();
     ExecutorService executorservice  = Executors.newFixedThreadPool(1);
  
     /*
    public String initiate(TransactionInitiationPayloadProxy proxy){
         try{

                 // format the date
           DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy:MM:dd hh:mm:ss");
           String datetime=df.format(LocalDateTime.now());

        
        // Initiate Entities
        com.entities.Transaction trx = new com.entities.Transaction();
        com.entities.TransactionEvent trx_evt = new TransactionEvent();
        com.entities.TransactionInfo trx_info = new TransactionInfo();
        com.entities.UserInfo user_info = new UserInfo();
        
        // Set properties of the entities
           
           trx_info.setAmount(proxy.getAmount());
           trx_info.setFee(proxy.getFee());
           trx_info.setCallbackurl(proxy.getCallbackurl());
           trx_info.setClientAppCode(proxy.getClientappcode());
           trx_info.setDatetime(datetime);
           trx_info.setDescription(proxy.getDescription());
           trx_info.setPublic_key(proxy.getPublic_key());
           trx_info.setRedirecturl(proxy.getRedirecturl());
           trx_info.setReference(proxy.getReference());
           trx_info.setChannel("account");
           trx_info.setChannelType("bank account");
           trx_info.setNumber(proxy.getAccount().getSender());
           trx_info.setTransactionEvent(trx_evt);
           trx_info.setSourceIP(proxy.getSourceIP());
           trx_info.setDeviceType(proxy.getDeviceType());

           user_info.setCountry(proxy.getCountry());
           user_info.setCurrency(proxy.getCurrency());
           user_info.setEmail(proxy.getEmail());
           user_info.setFullname(proxy.getFullname());
           user_info.setMobile(proxy.getMobile());
           user_info.setTransactionInfo(trx_info);
           
                                 
           trx.setRef(proxy.getReference());
           trx.setUserinfo(user_info);
           
        
          Validator validator = new Validator();
           if(validator.validateMerchant(trx_info.getPublic_key(), user_info.getCurrency(), trx_info.getAmount())){
                
                // save the object first then send to core
               if(dao.addObject(trx)<0){
                 obj = new JsonObject();
                 obj.addProperty("code", "S31");
                 obj.addProperty("message", "operation failed");
                 System.out.println("could not save object");
                 return obj.toString();
               }
               
               
                obj = new JsonParser().parse(util.getThirdPartyApi()).getAsJsonObject();
             System.out.println("obj is ...."+obj);
             
             if(!obj.has("access_token")){
                 obj2 = new JsonObject();
                 obj2.addProperty("code", "S7");
                 obj2.addProperty("message", "operation failed");
                 System.out.println("cuase......"+obj);
                return obj2.toString();
             }
             
             
                
                 // Construct the object
                 
                 TransactionInitiationPayload payload = new TransactionInitiationPayload();
                 InitiateTransactionObject transaction = new InitiateTransactionObject();
                 Order order = new Order();
                 InitiateSource source = new InitiateSource();
                 Account account = new Account();
                 
                 account.setSender(proxy.getAccount().getSender());
                 account.setSenderbankcode(proxy.getAccount().getSenderbankcode());
                 
                 source.setAccount(account);
                 System.out.println("the account..."+gson.toJson(account)+"......."+proxy.getAccount().getName());
                 source.setFirstname(proxy.getAccount().getName());
                 source.setOperation(util.getAppProperties().getProperty("account_operation"));
                 
                 BigDecimal amount = new BigDecimal(proxy.getFee()).add(new BigDecimal(proxy.getAmount()));
                 
                 order.setAmount(amount.toString());
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
                
                    String message=obj.get("message").getAsString();
                    String code=obj.get("code").getAsString();
                    obj2 = obj.get("transaction").getAsJsonObject();
                    String linkref=null;
                
                //return obj.toString();
                  
          if(obj2.get("linkingreference").isJsonNull()){
               System.out.println("sending to log now.....");
                //Create a thread to log the event 
                executorservice.execute(()->{
                    System.out.println("sending to log the now.....");
              
                    // will need to determine the gateway used e.g MPGS,PIN, etc.. when i get the merchant info
                    String res  = new Dao().nlogTransactionEvent("MPGS", proxy.getReference(), linkref, code, message).toString();
                    System.out.println("response....after log"+res);
                });
                
               return obj.toString();
             }else{
                  String  validlinkref=obj2.get("linkingreference").getAsString();           
                //Creating  thread to log the event 
                  executorservice.execute(()->{
                   System.out.println("sending to log the now.....");             
                    // will need to determine the gateway used e.g MPGS,PIN, etc..
                    String res  = new Dao().nlogTransactionEvent("account", proxy.getReference(), validlinkref, code, message).toString();
                    System.out.println("response....after log"+res);
                 });
                    return obj.toString();
              
          }
       }else{
            obj = new JsonObject();
            obj.addProperty("code", "S18");
            obj.addProperty("message","Operation not permitted to this merchant");
            return obj.toString(); 
        }     
         }catch(Exception e){
           obj = new JsonObject();
           obj.addProperty("code", "S7");
           obj.addProperty("message", "operation failed"); 
           System.out.println(" ====== something is wrong ===== "+e.getMessage());
           return obj.toString();
         }
    } 
    */
    
    
    
     public String initiate(TransactionInitiationPayloadProxy proxy){
    
          try{
           
                 // format the date
           DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy:MM:dd hh:mm:ss");
           String datetime=df.format(LocalDateTime.now());

        
        // Initiate Entities
        com.entities.Transaction trx = new com.entities.Transaction();
        com.entities.TransactionEvent trx_evt = new TransactionEvent();
        com.entities.TransactionInfo trx_info = new TransactionInfo();
        com.entities.UserInfo user_info = new UserInfo();
        
        // Set properties of the entities
           
           trx_info.setAmount(proxy.getAmount());
           trx_info.setFee(proxy.getFee());
           trx_info.setCallbackurl(proxy.getCallbackurl());
           trx_info.setClientAppCode(proxy.getClientappcode());
           trx_info.setDatetime(datetime);
           trx_info.setDescription(proxy.getDescription());
           trx_info.setPublic_key(proxy.getPublic_key());
           trx_info.setRedirecturl(proxy.getRedirecturl());
           trx_info.setReference(proxy.getReference());
           trx_info.setChannel("account");
           trx_info.setChannelType("bank account");
           trx_info.setNumber(proxy.getAccount().getSender());
           trx_info.setTransactionEvent(trx_evt);
           trx_info.setSourceIP(proxy.getSourceIP());
           trx_info.setDeviceType(proxy.getDeviceType());

           user_info.setCountry(proxy.getCountry());
           user_info.setCurrency(proxy.getCurrency());
           user_info.setEmail(proxy.getEmail());
           user_info.setFullname(proxy.getFullname());
           user_info.setMobile(proxy.getMobile());
           user_info.setTransactionInfo(trx_info);
           
                                 
           trx.setRef(proxy.getReference());
           trx.setUserinfo(user_info);
           
        
          Validator validator = new Validator();
          
          if(validator.validateMerchant(trx_info.getPublic_key(), user_info.getCurrency(), trx_info.getAmount())){
               
                       // save the object first then send to core
                JsonObject  status=dao.addObject(trx);
                System.out.println("--------status----"+status);
           if(status.get("code").getAsString().equals("00")){
               
               
            
                    // Get Access Token //        
             obj = new JsonParser().parse(util.getThirdPartyApi()).getAsJsonObject();
             System.out.println("obj is ...."+obj);
             
             if(!obj.has("access_token")){
                 obj2 = new JsonObject();
                 obj2.addProperty("code", "S7");
                 obj2.addProperty("message", "operation failed");
                 System.out.println("cuase......"+obj);
                return obj2.toString();
             }else{
                 
                         // Construct the request to be sent to seerbit api         
                  TransactionInitiationPayload payload = new TransactionInitiationPayload();
                 InitiateTransactionObject transaction = new InitiateTransactionObject();
                 Order order = new Order();
                 InitiateSource source = new InitiateSource();
                 Account account = new Account();
                 
                 account.setSender(proxy.getAccount().getSender());
                 account.setSenderbankcode(proxy.getAccount().getSenderbankcode());
                 
                 source.setAccount(account);
                 System.out.println("the account..."+gson.toJson(account)+"......."+proxy.getAccount().getName());
                 source.setFirstname(proxy.getAccount().getName());
                 source.setOperation(util.getAppProperties().getProperty("account_operation"));
                 
                 BigDecimal amount = new BigDecimal(proxy.getFee()).add(new BigDecimal(proxy.getAmount()));
                 
                 order.setAmount(amount.toString());
                 order.setCountry(proxy.getCountry());
                 order.setCurrency(proxy.getCurrency());
                 order.setDescription(proxy.getDescription());
                 
                 transaction.setReference(proxy.getReference());
                 
                 payload.setOrder(order);
                 payload.setPublickey(util.getAppProperties().getProperty("publickey"));
                 payload.setSource(source);
                 payload.setTransaction(transaction);
                 
               
             
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
                
                    String message=obj.get("message").getAsString();
                    String code=obj.get("code").getAsString();
                    obj2 = obj.get("transaction").getAsJsonObject();
                    String linkref=null;
                
                //return obj.toString();
                  
          if(obj2.get("linkingreference").isJsonNull()){
               System.out.println("sending to log now.....");
                //Create a thread to log the event 
                executorservice.execute(()->{
                    System.out.println("sending to log the now.....");
              
                    // will need to determine the gateway used e.g MPGS,PIN, etc.. when i get the merchant info
                    String res  = new Dao().nlogTransactionEvent("MPGS", proxy.getReference(), linkref, code, message).toString();
                    System.out.println("response....after log"+res);
                });
                
               return obj.toString();
             }else{
                  String  validlinkref=obj2.get("linkingreference").getAsString();           
                //Creating  thread to log the event 
                  executorservice.execute(()->{
                   System.out.println("sending to log the now.....");             
                    // will need to determine the gateway used e.g MPGS,PIN, etc..
                    String res  = new Dao().nlogTransactionEvent("account", proxy.getReference(), validlinkref, code, message).toString();
                    System.out.println("response....after log"+res);
                 });
                    return obj.toString();
              
          }
        }
                 
             
           
           }else{
            
              return status.toString();
               
           }
              
          }else{
                            //  If validation failed
                            
            obj = new JsonObject();
            obj.addProperty("code", "S18");
            obj.addProperty("message","Transaction failed, amount out of range");
            return obj.toString(); 
          
          }
              
              
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
                System.out.println("message===="+obj.get("message").getAsString());
                System.out.println("code===="+obj.get("code").getAsString());
                System.out.println("reference===="+obj.get("transaction").getAsJsonObject().get("reference").getAsString());
                System.out.println("response from core..."+obj);
                dao.nupdateTransactionEvent(proxy.getLinkingreference(),obj.get("code").getAsString(),obj.get("message").getAsString(),obj.get("transaction").getAsJsonObject().get("reference").getAsString());
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
