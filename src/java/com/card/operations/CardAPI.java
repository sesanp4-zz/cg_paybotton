/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.card.operations;

import com.entities.Event;
import com.entities.TransactionEvent;
import com.entities.TransactionInfo;
import com.entities.Transactions;
import com.entities.UserInfo;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.util.Dao;
import com.util.GenericException;
import com.util.Utilities;
import com.validator.Validator;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import org.apache.http.ParseException;
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
@RequestScoped
public class CardAPI {
    
       
     @Inject
     Dao dao ;       
    
    JsonObject obj,obj2;   
    CloseableHttpClient client = HttpClients.createDefault();
    HttpPost post;
    HttpGet get;
    CloseableHttpResponse response;
    Gson gson = new Gson();
    Utilities util = new Utilities();
    ExecutorService executorservice  = Executors.newFixedThreadPool(1);;
    
    Logger logger = Logger.getLogger("CardApi.class");
    
    
   //  Properties props = util.getProp();
    
    public String ninitiate_Transaction(CardTransactionRequestProxy trx_proxy) throws GenericException{
        
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
           
           trx_info.setAmount(trx_proxy.getAmount());
           trx_info.setFee(trx_proxy.getFee());
           trx_info.setCallbackurl(trx_proxy.getCallbackurl());
           trx_info.setClientAppCode(trx_proxy.getClientappcode());
           trx_info.setDatetime(datetime);
           trx_info.setDescription(trx_proxy.getDescription());
           trx_info.setPublic_key(trx_proxy.getPublic_key());
           trx_info.setRedirecturl(trx_proxy.getRedirecturl());
           trx_info.setReference(trx_proxy.getTranref());
           trx_info.setChannel("card");
           trx_info.setChannelType(trx_proxy.getChannelType());
           trx_info.setNumber(trx_proxy.getCard().getNumber());
           trx_info.setTransactionEvent(trx_evt);
           trx_info.setSourceIP(trx_proxy.getSourceIP());
           trx_info.setDeviceType(trx_proxy.getDeviceType());

           user_info.setCountry(trx_proxy.getCountry());
           user_info.setCurrency(trx_proxy.getCurrency());
           user_info.setEmail(trx_proxy.getEmail());
           user_info.setFullname(trx_proxy.getFullname());
           user_info.setMobile(trx_proxy.getMobile());
           user_info.setTransactionInfo(trx_info);
           
                                 
           trx.setRef(trx_proxy.getTranref());
           trx.setUserinfo(user_info);
           
        
          Validator validator = new Validator();
        if(validator.validateMerchant(trx_info.getPublic_key(), user_info.getCurrency(), trx_info.getAmount())){

            //Create a thread to  add a copy of the transaction to database
//             executorservice.execute(()->{
//              String status = new Dao().addObject(trx);
//              System.out.println(status);
//            });
             
                // save the object first then send to core
               if(dao.addObject(trx)<0){
                 obj = new JsonObject();
                 obj.addProperty("code", "S31");
                 obj.addProperty("message", "operation failed");
                 System.out.println("could not save object");
                 return obj.toString();
               }
               
               // Sending to cores
             
             // Get Access Token //        
             obj = new JsonParser().parse(util.getThirdPartyApi()).getAsJsonObject();
             System.out.println("obj is ...."+obj);
             
             if(!obj.has("access_token")){
                 obj2 = new JsonObject();
                 obj2.addProperty("code", "S7");
                 obj2.addProperty("message", "operation failed");
                 System.out.println("cuase......"+obj);
                return obj2.toString();
             }
             
              // Construct the request to be sent to seerbit api         
          CardTransactionRequest cr = new CardTransactionRequest();
          Card card = new Card();
          Source source = new Source();
          Order order = new Order();
          card.setCvv(trx_proxy.getCard().getCvv());
          card.setNumber(trx_proxy.getCard().getNumber());
          card.setExpirymonth(trx_proxy.getCard().getExpirymonth());
          card.setExpiryyear(trx_proxy.getCard().getExpiryyear());
          
           //i will fetch the config from user_management
          // get how to charge the source e.g with 3dsecure or other ones
          if(trx_proxy.getType().equals("PIN")){
            card.setPin(trx_proxy.getCard().getPin());
          }
          source.setOperation("pg_charge");
          source.setEmail(trx_proxy.getEmail());
          source.setType(trx_proxy.getType());
          source.setCard(card);
          Double fee=Double.parseDouble(trx_proxy.getFee());
          Double amount=Double.parseDouble(trx_proxy.getAmount());
          String grossamount=Double.toString(fee+amount);
          order.setAmount(grossamount);
          order.setCountry(trx_proxy.getCountry());
          order.setCurrency(trx_proxy.getCurrency());
          order.setDescription(trx_proxy.getDescription());
          
          Transaction transactionobj = new Transaction();
          transactionobj.setCallbackurl(util.getAppProperties().getProperty("internal_callback"));
          transactionobj.setReference(trx_proxy.getTranref());
          
          cr.setOrder(order);
          cr.setSource(source);
          cr.setPublickey("test");
          cr.setTransaction(transactionobj);

            System.out.println("printing out the request to be sent to core.....");
            System.out.println(gson.toJson(cr));

         // initiating the charge
          post = new HttpPost(util.getAppProperties().getProperty("card_initiate_endpoint"));
          post.setHeader("Authorization", "Bearer "+obj.get("access_token").getAsString());
          post.setHeader("Content-Type", "application/json");
          StringEntity ent = new StringEntity(gson.toJson(cr));
          post.setEntity(ent);
          response=client.execute(post);
          String msg = EntityUtils.toString(response.getEntity());
          obj = new JsonParser().parse(msg).getAsJsonObject();
          System.out.println("response from core..."+obj);
          
            
          String message=obj.get("message").getAsString();
          String code=obj.get("code").getAsString();
          obj2 = obj.get("transaction").getAsJsonObject();
          String linkref=null;

           // check the response returned from core
          
          if(obj2.get("linkingreference").isJsonNull()){
               System.out.println("sending to log now.....");
                //Create a thread to log the event 
                executorservice.execute(()->{
                    System.out.println("sending to log the now.....");
              
                    // will need to determine the gateway used e.g MPGS,PIN, etc.. when i get the merchant info
                    String res  = new Dao().nlogTransactionEvent("MPGS", trx_proxy.getTranref(), linkref, code, message).toString();
                    System.out.println("response....after log"+res);
                });
                
               return obj.toString();
             }else{
                  String  validlinkref=obj2.get("linkingreference").getAsString();           
                //Creating  thread to log the event 
                  executorservice.execute(()->{
                   System.out.println("sending to log the now.....");             
                    // will need to determine the gateway used e.g MPGS,PIN, etc..
                    String res  = new Dao().nlogTransactionEvent("MPGS", trx_proxy.getTranref(), validlinkref, code, message).toString();
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
        
         }catch(JsonSyntaxException | IOException | ParseException e){
            throw new GenericException(e.getMessage());
         }

    }
    
    //
    public String nvalidateOTP(ValidateOTPRequest request) throws IOException{
    
       // set the parameters to be sent to the api
          ValidateOTPSource source = new ValidateOTPSource();
          source.setOperation("pg_charge_validate");
          request.setPublickey("test");
          request.setSource(source);
          
          
          // Get authentication Token
          obj = new JsonParser().parse(util.getThirdPartyApi()).getAsJsonObject();
          
          // initiating otp validation
          post = new HttpPost(util.getAppProperties().getProperty("card_otp_validate_url"));
          post.setHeader("Authorization", "Bearer "+obj.get("access_token").getAsString());
          post.setHeader("Content-Type", "application/json");
          StringEntity ent = new StringEntity(gson.toJson(request));
          post.setEntity(ent);
          response=client.execute(post);
          String msg = EntityUtils.toString(response.getEntity());
          obj = new JsonParser().parse(msg).getAsJsonObject();
          dao.nupdateTransactionEvent(request.getTransaction().getLinkingreference(), obj.get("message").getAsString(),obj.get("code").getAsString(),obj.get("reference").getAsString());
          return msg;   
    } 
    
    
    
    public String naddEvent(Event event){
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy:MM:dd hh:mm:ss");
        String created = df.format(LocalDateTime.now());
        event.setCreated_at(created);
        dao.addObject(event);
        
        obj = new JsonObject();
        obj.addProperty("code", "00");
        obj.addProperty("message","Event added");
        
        return obj.toString();
     
    }
    
    
    public String nupdateEvent(String ref,String event){
        dao.updateEvent(ref, event);
        
        obj = new JsonObject();
        obj.addProperty("code", "00");
        obj.addProperty("message","Event added");
        
        return obj.toString();

    }
    
        public String nupdateTransaction(String reference, String linkingref, String code,String message) throws GenericException{
          try{
             // need to do status check before doing this operation 
             executorservice.execute(()->{
              String status = new Dao().nUpdateAndSendToSettlement(reference, linkingref, code, message).toString();
              System.out.println(status);
            });
        com.entities.Transaction transaction = dao.ngetTransactionDetails(reference);
        
        if(transaction==null){
             obj = new JsonObject();
             obj.addProperty("code", "S7");
             obj.addProperty("message", "Operation Failed");
             return obj.toString();
        }
        
        System.out.println("after getting details...."+gson.toJson(transaction));
        String url=transaction.getUserinfo().getTransactionInfo().getRedirecturl()+"?"+URLEncoder.encode("linkingreference="+linkingref+"&code="+code+"&message="+message+"&reference="+transaction.getRef()+"&callback="+transaction.getUserinfo().getTransactionInfo().getCallbackurl(),"UTF-8");
        System.out.println("______ the encoded redirect url _______"+url);
        return url;
      }catch(Exception e){     
        throw new GenericException("operation failed");
      }
      
    };
    
    
    
                                            // Old one 

    
      public String initi(Transactions trans) throws GenericException{
         try{ 
          
           // format the date
          
            DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy:MM:dd hh:mm:ss");
           String datetime=df.format(LocalDateTime.now());
           
            trans.setDatetime(datetime);
            
            //call get user method to get userinfo
             Validator validator = new Validator();
             System.out.println("validating merchant now!!!!");
             
        if(validator.validateMerchant(trans.getMid(), trans.getCurrency(), trans.getAmount())){

            //Create a thread to  add a copy of the transaction to database
//            System.out.println("sending obj to database now...");
//             executorservice.execute(()->{
//              String status = new Dao().addObject(trans);
//              System.out.println("status when transaction was saved..."+status);
//            });
             
                 // Get Access Token // 
                 System.out.println("checking access token now");
                 obj = new JsonParser().parse(util.getThirdPartyApi()).getAsJsonObject();
                 System.out.println("access token returned obj ...."+obj);
             
             //check for accesstoken returned
             
             if(!obj.has("access_token")){
                 obj2 = new JsonObject();
                 obj2.addProperty("code", "S7");
                 obj2.addProperty("message", "operation failed");
                 System.out.println("cuase......"+obj);
                return obj2.toString();
             }
                          
              // Construct the request to be sent to seerbit api         
          CardTransactionRequest cr = new CardTransactionRequest();
          Card card = new Card();
          Source source = new Source();
          Order order = new Order();
          card.setCvv(trans.getCard().getCvv());
          card.setNumber(trans.getCard().getNumber());
          card.setExpirymonth(trans.getCard().getExpirymonth());
          card.setExpiryyear(trans.getCard().getExpiryyear());
          
           //i will fetch the config from user_management
          // get how to charge the source
         System.out.println("type.."+trans.getType());
          if(trans.getType().equals("PIN")){
            card.setPin(trans.getCard().getPin());
          }
          source.setOperation("pg_charge");
          source.setEmail(trans.getEmail());
          source.setType(trans.getType());
          source.setCard(card);
          
          order.setAmount(trans.getAmount());
          order.setCountry(trans.getCountry());
          order.setCurrency(trans.getCurrency());
          order.setDescription(trans.getDescription());
          
          Transaction transactionobj = new Transaction();
          transactionobj.setCallbackurl(util.getAppProperties().getProperty("internal_callback"));
          transactionobj.setReference(trans.getReference());
          
          cr.setOrder(order);
          cr.setSource(source);
          cr.setPublickey("test");
          cr.setTransaction(transactionobj);

            System.out.println("printing out the request to be sent to core.....");
            System.out.println(gson.toJson(cr));

         // initiating the charge
          post = new HttpPost(util.getAppProperties().getProperty("card_initiate_endpoint"));
          post.setHeader("Authorization", "Bearer "+obj.get("access_token").getAsString());
          post.setHeader("Content-Type", "application/json");
          StringEntity ent = new StringEntity(gson.toJson(cr));
          post.setEntity(ent);
          response=client.execute(post);
          String msg = EntityUtils.toString(response.getEntity());
          obj = new JsonParser().parse(msg).getAsJsonObject();
          System.out.println("response from core..."+obj);
          
            
          String message=obj.get("message").getAsString();
          String code=obj.get("code").getAsString();
          obj2 = obj.get("transaction").getAsJsonObject();
          String linkref=null;
          
          // check the response returned from core
          
          if(obj2.get("linkingreference").isJsonNull()){
                 System.out.println("message..."+message);
                 executorservice.execute(()->{
                     System.out.println("sending to log now.....");
                     System.out.println("message..."+message);
                     String res  = new Dao().updateTransaction(trans.getReference(), linkref, code, message).toString();
                     System.out.println("response....after log"+res);
                 });
               return obj.toString();
             }else{
          
                  System.out.println("linkingref is not null");
                  String  validlinkref=obj2.get("linkingreference").getAsString();           
                //Creating  thread to log the event 
                  executorservice.execute(()->{
                   System.out.println("sending to log now.....");
                   String res  = new Dao().updateTransaction(trans.getReference(), validlinkref, code, message).toString();
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
        
         }catch(JsonSyntaxException | IOException | ParseException e){
            throw new GenericException(e.getMessage());
         }
      

      }
      
      
      
    
    public String validateOTP(ValidateOTPRequest request) throws IOException{
    
       // set the parameters to be sent to the api
          ValidateOTPSource source = new ValidateOTPSource();
          source.setOperation("pg_charge_validate");
          request.setPublickey("test");
          request.setSource(source);
          
          System.out.println("request...."+gson.toJson(request));
          
          // Get authentication Token
          obj = new JsonParser().parse(util.getThirdPartyApi()).getAsJsonObject();
          
          // initiating otp validation
          post = new HttpPost(util.getAppProperties().getProperty("card_otp_validate_url"));
          post.setHeader("Authorization", "Bearer "+obj.get("access_token").getAsString());
          post.setHeader("Content-Type", "application/json");
          StringEntity ent = new StringEntity(gson.toJson(request));
          post.setEntity(ent);
          response=client.execute(post);
          String msg = EntityUtils.toString(response.getEntity());
          obj = new JsonParser().parse(msg).getAsJsonObject();
          System.out.println("response...."+obj);
          obj2= obj.get("transaction").getAsJsonObject();
          dao.UpdateAndSendToSettlement(obj2.get("reference").getAsString(), request.getTransaction().getLinkingreference(), obj.get("code").getAsString(), obj.get("message").getAsString());
                  
          return msg;   
    } 
    
    
    
    public String addEvent(Event event){
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy:MM:dd HH:mm:ss");
        String created = df.format(LocalDateTime.now());
        event.setCreated_at(created);
        dao.addObject(event);
        
        obj = new JsonObject();
        obj.addProperty("code", "00");
        obj.addProperty("message","Event added");
        
        return obj.toString();
     
    }
    
    
    public String updateEvent(String ref,String event){
        dao.updateEvent(ref, event);
        
        obj = new JsonObject();
        obj.addProperty("code", "00");
        obj.addProperty("message","Event added");
        
        return obj.toString();

    }
    
    
    public boolean checkStatusFromThirdParty(String linkingref,String ref){
          dao = new Dao();
          boolean status=false;
          System.out.println("......inside");
         try{ 
          //retrieve the transaction by ref
          com.entities.Transaction transaction = dao.ngetTransactionDetails(ref);
          
             System.out.println("transaction gotten....."+gson.toJson(transaction));
          
          if(transaction==null){
            throw new GenericException();
          }
          
             System.out.println("getting the auth token ");
          
          //Call thirdparty authentication endpoint
          obj = new JsonParser().parse(util.getThirdPartyApi()).getAsJsonObject();
          
            System.out.println("......after getting token.."+obj);
          
          //set parameters for thirdparty status check
          post = new HttpPost(util.getAppProperties().getProperty("thirdparty_status_check"));
          post.setHeader("Authorization", "Bearer "+obj.get("access_token").getAsString());
          post.setHeader("Content-Type", "application/json");
          
          //Consruct Payload to send
            System.out.println("Contructing object to send to the third party for status check");
            
          obj2 = new JsonObject();
          obj2.addProperty("publickey", util.getAppProperties().getProperty("publickey"));
          
          JsonObject obj3 = new JsonObject();
          obj3.addProperty("linkingreference", linkingref);
          
          obj2.add("transaction", obj3);
          
         JsonObject obj4 = new JsonObject();
         obj4.addProperty("operation", "pg_charge_status");
         
         obj2.add("source", obj4);
             System.out.println("checking.......");
         StringEntity ent = new StringEntity(obj2.toString());
         System.out.println("...request sent to third party status endpoint...."+obj2);
         post.setEntity(ent);
         response=client.execute(post);
         String msg = EntityUtils.toString(response.getEntity());
         obj = new JsonParser().parse(msg).getAsJsonObject();
         System.out.println("response from third party status endpoint.."+obj);
             
          //Needs to check the amount and status the core sent back and compare with what my system charge
          
              String code=obj.get("code").getAsString();
              String message=obj.get("message").getAsString();
              JsonObject transactionObj=obj.get("transaction").getAsJsonObject();
              JsonObject orderObj=obj.get("order").getAsJsonObject();
              
                        // code.equals("00")&&message.equals("Successful")&&
                        
                BigDecimal amount=new BigDecimal(transaction.getUserinfo().getTransactionInfo().getAmount());
                BigDecimal fee =  new BigDecimal(transaction.getUserinfo().getTransactionInfo().getFee());
                BigDecimal totalamount=amount.add(fee);
                System.out.println("total...."+totalamount);
                if(orderObj.get("amount").getAsString().equals(totalamount.toString())){
                    System.out.println("third party transaction verification succesful");
                    System.out.println("response from thirdparty..."+obj);
                    System.out.println("transaction..."+gson.toJson(transaction));
                    status=true;
                }else{
                    System.out.println("third party transaction verification  not succesful");
                    status=false;
                }

          
         }catch(Exception e){
            obj = new JsonObject();
            obj.addProperty("code","S7");
            obj.addProperty("message", "Operation failed");
            logger.logp(Level.SEVERE, "CardApi.class", "calling checkstatusFromThirdparty", e.getMessage());
         }
         return status;

    }
    
    
     public String poolandupdatestatuscheck(String linkingref,String ref){
          dao = new Dao();
         try{ 
          //retrieve the transaction by ref
          com.entities.Transaction transaction = dao.ngetTransactionDetails(ref);
          if(transaction==null){
            throw new GenericException();
          }
          //Call thirdparty authentication endpoint
          obj = new JsonParser().parse(util.getThirdPartyApi()).getAsJsonObject();
          
          //set parameters for thirdparty status check
          post = new HttpPost(util.getAppProperties().getProperty("thirdparty_status_check"));
          post.setHeader("Authorization", "Bearer "+obj.get("access_token").getAsString());
          post.setHeader("Content-Type", "application/json");
          
          //Consruct Payload to send
          obj2 = new JsonObject();
          obj2.addProperty("publickey", util.getAppProperties().getProperty("publickey"));
          
          JsonObject obj3 = new JsonObject();
          obj3.addProperty("linkingreference", linkingref);
          
          obj2.add("transaction", obj3);
          
         JsonObject obj4 = new JsonObject();
         obj4.addProperty("operation", "pg_charge_status");
         
         obj2.add("source", obj4);
          
         StringEntity ent = new StringEntity(obj2.getAsString());
         System.out.println("...request sent to third party status endpoint...."+obj2);
         post.setEntity(ent);
         response=client.execute(post);
         String msg = EntityUtils.toString(response.getEntity());
         obj = new JsonParser().parse(msg).getAsJsonObject();
         System.out.println("response from third party status endpoint.."+obj);
             
          //Needs to check the amount and status the core sent back and compare with what my system charge
          
              String code=obj.get("code").getAsString();
              String message=obj.get("message").getAsString();
              JsonObject transactionObj=obj.get("transaction").getAsJsonObject();
              JsonObject orderObj=obj.get("transaction").getAsJsonObject();
              

                executorservice.submit(()->{
                   dao.nlogTransactionEvent("Mpgs", ref, linkingref, code, message);
              }); 
               
              return obj.toString();
          
         }catch(Exception e){
            obj = new JsonObject();
            obj.addProperty("code","S7");
            obj.addProperty("message", "Operation failed");
            logger.logp(Level.SEVERE, "CardApi.class", "calling checkstatusFromThirdparty", e.getMessage());
            return obj.toString();
         }
         


    }
     
     
      public String fetchStatusFromCore(String linkingref){
          dao = new Dao();
         try{ 

          //Call thirdparty authentication endpoint
          obj = new JsonParser().parse(util.getThirdPartyApi()).getAsJsonObject();
          
          //set parameters for thirdparty status check
          post = new HttpPost(util.getAppProperties().getProperty("thirdparty_status_check"));
          post.setHeader("Authorization", "Bearer "+obj.get("access_token").getAsString());
          post.setHeader("Content-Type", "application/json");
          
          //Consruct Payload to send
          obj2 = new JsonObject();
          obj2.addProperty("publickey", util.getAppProperties().getProperty("publickey"));
          
          JsonObject obj3 = new JsonObject();
          obj3.addProperty("linkingreference", linkingref);
          
          obj2.add("transaction", obj3);
          
         JsonObject obj4 = new JsonObject();
         obj4.addProperty("operation", "pg_charge_status");
         
         obj2.add("source", obj4);
          
         StringEntity ent = new StringEntity(obj2.getAsString());
         System.out.println("...request sent to third party status endpoint...."+obj2);
         post.setEntity(ent);
         response=client.execute(post);
         String msg = EntityUtils.toString(response.getEntity());
         obj = new JsonParser().parse(msg).getAsJsonObject();
         System.out.println("response from third party status endpoint.."+obj);
             
              return obj.toString();
          
         }catch(Exception e){
            obj = new JsonObject();
            obj.addProperty("code","S7");
            obj.addProperty("message", "Operation failed");
            logger.logp(Level.SEVERE, "CardApi.class", "calling checkstatusFromThirdparty", e.getMessage());
            return obj.toString();
         }
         


    }

    
    
    public String updateTransaction(String reference, String linkingref, String code,String message) throws GenericException{
      try{
         // need to do status check    
             executorservice.execute(()->{
              String status = new Dao().UpdateAndSendToSettlement(reference, linkingref, code, message).toString();
              System.out.println(status);
            });
        com.entities.Transactions transaction = dao.getTransactionDetails(reference);
        
        if(transaction==null){
             obj = new JsonObject();
             obj.addProperty("code", "S7");
             obj.addProperty("message", "Operation Failed");
             return obj.toString();
        }
        
        System.out.println("after getting details...."+gson.toJson(transaction));
        String url=transaction.getRedirecturl()+"?"+URLEncoder.encode("linkingreference="+linkingref+"&code="+code+"&message="+message+"&callback="+transaction.getCallbackurl(),"UTF-8");
        System.out.println("______ the encoded redirect url _______"+url);
        return url;
      }catch(Exception e){     
        throw new GenericException("operation failed");
      }
      
    };
    
    
    public JsonObject initiateRefund(String reference){
        
       try{ 
            com.entities.Transaction transaction =null;
            Dao d = new Dao();
            transaction=d.ngetTransactionDetails(reference); 

            
            System.out.println("======= Message gotten from dao =======");
            System.out.println(gson.toJson(transaction));
            
         if(transaction!=null){

          //Consruct Payload to send
          JsonObject  payload = new JsonObject();
          JsonObject  transaction_obj = new JsonObject();
          JsonObject  source_obj = new JsonObject();
          JsonObject  order_obj = new JsonObject();
          
          payload.addProperty("publickey", util.getAppProperties().getProperty("publickey"));
          transaction_obj.addProperty("linkingreference",transaction.getUserinfo().getTransactionInfo().getTransactionEvent().getGatewayref());        
          payload.add("transaction", transaction_obj);
          source_obj.addProperty("operation", "card_refund");
          payload.add("source", source_obj);
          order_obj.addProperty("currency", transaction.getUserinfo().getCurrency());
          order_obj.addProperty("description", "refund");
          order_obj.addProperty("country", transaction.getUserinfo().getCountry());
          order_obj.addProperty("amount", transaction.getUserinfo().getTransactionInfo().getAmount());
          payload.add("order", order_obj);
          
          //Call thirdparty authentication endpoint
          obj = new JsonParser().parse(util.getThirdPartyApi()).getAsJsonObject();
          
          //set parameters for core transaction refund         
          post = new HttpPost(util.getAppProperties().getProperty("refund_endpoint"));
          post.setHeader("Authorization", "Bearer "+obj.get("access_token").getAsString());
          post.setHeader("Content-Type", "application/json");
          StringEntity ent = new StringEntity(payload.toString());
          System.out.println("...request sent to refund  endpoint...."+payload);
          post.setEntity(ent);
          response=client.execute(post);
          String msg = EntityUtils.toString(response.getEntity());
          obj = new JsonParser().parse(msg).getAsJsonObject();
          JsonObject response = new JsonParser().parse(gson.toJson(transaction)).getAsJsonObject();
          response.add("metadata", obj);
          return response;  
            }else{
               obj = new JsonObject();
               obj.addProperty("code", "S1");
               obj.addProperty("message", "Transaction not found");
               return obj;
            } 
       }catch(Exception e){
           System.out.println("somethig is wrong");
           System.out.println(e.getMessage());
           obj = new JsonObject();
           obj.addProperty("code", "S1");
           obj.addProperty("message", "Operation failed");
           return obj;
       } 
        
    }
    
    
    public static void main(String[] args) {
       // System.out.println(new CardAPI().checkStatusFromThirdParty("123", " CGS_29"));
       
//       
//       String p="[a-zA-Z]";
//       String text="-";
//       
//        Pattern pt =Pattern.compile(p);
//        Matcher m   = pt.matcher(text);
//        
//        System.out.println(m.matches());

          System.out.println(new CardAPI().initiateRefund("TH01"));
           
//        Dao d = new Dao();
//        Gson g = new Gson();
//        System.out.println(g.toJson(d.ngetTransactionDetails("TH01")));
       
    }
    
}
