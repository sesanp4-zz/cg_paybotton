/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.util;

import com.entities.Event;
import com.entities.TransactionEvent;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.io.Serializable;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.enterprise.context.RequestScoped;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;



/**
 *
 * @author centricgateway
 */
@RequestScoped
public class Dao {
     
    SessionFactory sf;
    Session session;
    Query query ;
    
     JsonObject obj,obj2;
     Gson gson = new Gson();
     Utilities util = new Utilities();
     
     
     
     ExecutorService es = Executors.newFixedThreadPool(1);
    
         public int addObject(Object obj){
         try{
            // Long status=0;          
          session= HibernateUtil.getSessionFactory().openSession();
          session.beginTransaction();
          Long id= (Long) session.save(obj);             
          session.getTransaction().commit();         
          System.out.println("user added successfully with id "+id); 
          return id.intValue();
        }catch(Exception e ){
             System.out.println(" could not save object cause.."+e.getMessage());
             return 0;
        } finally{
          if (session!=null) {
                if(session.isOpen()) {
                    session.flush();
                    session.close();
                }
             }
        }         
    }
    

    
     
    public String saveObject(Object o){
      try{
           session= HibernateUtil.getSessionFactory().openSession();
           session.beginTransaction();
           session.save(o);
           session.getTransaction().commit();
      }catch(Exception e){
          System.out.println(e.getMessage());
      }     
      finally{
           if (session!=null) {
              if (session.isOpen()) {
                  session.flush();
                    session.close();
                }
    }
      }
      return "successful";
    }

    
    public com.entities.Transaction ngetTransactionDetails(String reference){
      com.entities.Transaction transaction=null;
      try{
          System.out.println("=========== Getting transaction Details started ========");
           session= HibernateUtil.getSessionFactory().openSession();
          session.beginTransaction();
          System.out.println("=========== About to fetch Transaction Details =========");
          query =  session.createQuery("from Transaction  transaction where transaction.ref=:reference");
          query.setParameter("reference", reference);
          transaction =(com.entities.Transaction) query.uniqueResult();
          session.getTransaction().commit();
           System.out.println("=========== Done fetch Transaction Details =========");
      }catch(Exception e){
           System.out.println("=========== An Error has Occured =========");
          System.out.println(e.getMessage());
      }finally{
         if (session!=null) {
           if (session.isOpen()) {
                   session.flush();
                    session.close();
                    //HibernateUtil.getSessionFactory().close();
                }
         }
      }   
      return transaction;
      
    }
    
   
      public JsonObject nlogTransactionEvent(String gateway,String reference,String linkingref,String responseCode,String responseMessage){
      try{
          System.out.println(gateway+"..."+reference+"..."+linkingref+"...."+responseCode+"...."+responseMessage);
            session= HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            query =  session.createQuery("from Transaction  transaction where transaction.ref=:ref");
            query.setParameter("ref", reference);
            com.entities.Transaction transaction =(com.entities.Transaction) query.uniqueResult();
            
            // Log transaction Event
               TransactionEvent evt = transaction.getUserinfo().getTransactionInfo().getTransactionEvent();
               System.out.println(gson.toJson(evt));
               evt.setGateway(gateway);
               evt.setGatewayCode(responseCode);
               evt.setGatewayMessage(responseMessage);
               evt.setGatewayref(linkingref);
               evt.setTransactionRef(reference);

               System.out.println("event created");
            
            obj = new JsonObject();
            obj.addProperty("code", "S4");
            obj.addProperty("message", "transactionEvent logged");
            session.getTransaction().commit();
            return obj;
      }catch(Exception e){
          obj = new JsonObject();
          obj.addProperty("code", "S3");
          obj.addProperty("message", "operation failed");
          System.out.println("...."+e.getMessage());
          return obj;
      }finally{
        if (session!=null) {
            if (session.isOpen()) {
                  session.flush();
                    session.close();
                }
           }
        
      }
            
    }
     // Transaction transaction;
    public String nupdateTransactionEvent(String gatewayref,String responseCode,String responseMessage,String trxn_ref){
      try{
            session= HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            query =  session.createQuery("from Transaction  transaction where transaction.ref=:trxn_ref");
            query.setParameter("trxn_ref", trxn_ref);
            com.entities.Transaction transaction =(com.entities.Transaction) query.uniqueResult();
            
            // Update transaction Event
              transaction.getUserinfo().getTransactionInfo().getTransactionEvent().setGatewayCode(responseCode);
              transaction.getUserinfo().getTransactionInfo().getTransactionEvent().setGatewayMessage(responseMessage);
           
              obj = new JsonObject();
              obj.addProperty("code", "S4");
              obj.addProperty("message", "transactionEvent updated");
              session.getTransaction().commit();

//            //multithread needs to be used for sending to thread              
              es.submit(()->{
                    util.sendToSettlement(transaction);             
              });
               //util.sendToSettlement(transaction);
              
            return obj.toString();
      }catch(Exception e){
          obj = new JsonObject();
          obj.addProperty("code", "S3");
          obj.addProperty("message", "operation failed");
          System.out.println("...."+e.getMessage());
          return obj.toString();
      }finally{
        if (session!=null) {
             if (session.isOpen()) {
                    session.flush();
                    session.close();
                }
           }
        
      }
            
    }
      
    
     public String nupdateTransactionWithSettlementInfo(String trxn_ref,String settlementCode, String settlementMessage, String time_to_settlement){
      try{
            session= HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            query =  session.createQuery("from Transaction  transaction where transaction.ref=:trxn_ref");
            query.setParameter("trxn_ref", trxn_ref);
            com.entities.Transaction transaction =(com.entities.Transaction) query.uniqueResult();
            
            System.out.println("updating the settlement info on transaction table");
            
            transaction.setSettlementCode(settlementCode);
            transaction.setSettlementMessage(settlementMessage);
            transaction.setTime_to_settlement(time_to_settlement);
            
             System.out.println("update done");
            
              obj = new JsonObject();
              obj.addProperty("code", "S4");
              obj.addProperty("message", "transaction updated with settlement info");
              session.getTransaction().commit();

              
            return obj.toString();
      }catch(Exception e){
          obj = new JsonObject();
          obj.addProperty("code", "S3");
          obj.addProperty("message", "operation failed");
          System.out.println("...."+e.getMessage());
          return obj.toString();
      }finally{
        if (session!=null) {
             if (session.isOpen()) {
                 session.flush();
                    session.close();
                }
           }
        
      }
            
    }
      
      
      public String ngetTransactionStatus(String ref){
      try{
            session= HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            query =  session.createQuery("from Transaction  transaction where transaction.ref=:ref");
            query.setParameter("ref", ref);
            com.entities.Transaction transaction =(com.entities.Transaction) query.uniqueResult();
            obj = new JsonObject();
            
            obj.addProperty("customerName", transaction.getUserinfo().getFullname());
            obj.addProperty("customerMobile", transaction.getUserinfo().getMobile());
            obj.addProperty("customerEmail", transaction.getUserinfo().getEmail());
            obj.addProperty("amount", transaction.getUserinfo().getTransactionInfo().getAmount());
            obj.addProperty("code", transaction.getUserinfo().getTransactionInfo().getTransactionEvent().getGatewayCode());
            if(transaction.getUserinfo().getTransactionInfo().getTransactionEvent().getGatewayMessage().equals("APPROVED")){
                 obj.addProperty("message", "Successful");
            }else{
                obj.addProperty("message", transaction.getUserinfo().getTransactionInfo().getTransactionEvent().getGatewayMessage());
            }
            obj2 = new JsonObject();
            obj2.addProperty("linkingreference", transaction.getUserinfo().getTransactionInfo().getTransactionEvent().getGatewayref());
            obj2.addProperty("reference", transaction.getRef());
            obj2.addProperty("callbackurl", transaction.getUserinfo().getTransactionInfo().getCallbackurl());
            obj.add("transaction", obj2);
            
            System.out.println(obj.toString());
            session.getTransaction().commit();
            return obj.toString();
      }catch(Exception e){
          obj = new JsonObject();
          obj.addProperty("code", "S23");
          obj.addProperty("message", "No transaction found");
          System.out.println("...."+e.getMessage());
          return obj.toString();
      }finally{
         if (session!=null) {
             if (session.isOpen()) {
                 session.flush();
                    session.close();
                }
          }
      }
            
    }
      
      public String nupdateEvent(String ref, String event){  
          try{
            session= HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            query =  session.createQuery("from Event event where event.ref=:ref");
            query.setParameter("ref", ref);
            Event eventObj =(Event) query.uniqueResult();
            eventObj.setEvents(eventObj.getEvents().concat(", "+event));
              System.out.println("...."+gson.toJson(eventObj));
            obj = new JsonObject();
            obj.addProperty("code", "00");
            obj.addProperty("message", "update done");
            
            session.getTransaction().commit();
            
            return obj.toString();
          }catch(Exception e){
           obj = new JsonObject();
          obj.addProperty("code", "S23");
          obj.addProperty("message", "No transaction found");
          System.out.println("...."+e.getMessage());
          return obj.toString();
      }finally{
         if (session!=null) {
             if (session.isOpen()) {
                 session.flush();
                    session.close();
                }
          }
      }
    }
      
      
    public JsonObject nUpdateAndSendToSettlement(String reference, String linkingref,String responseCode,String responseMessage){
      try{
          System.out.println(reference+"...."+linkingref+"....."+responseCode+"..."+responseMessage);
            session= HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            query =  session.createQuery("from Transaction  transaction where transaction.ref=:ref");
            query.setParameter("ref", reference);
            com.entities.Transaction transaction =(com.entities.Transaction) query.uniqueResult();
            System.out.println("...."+gson.toJson(transaction));
            
            // set transactionEvent parameters
             transaction.getUserinfo().getTransactionInfo().getTransactionEvent().setGatewayMessage(responseMessage);
             transaction.getUserinfo().getTransactionInfo().getTransactionEvent().setGatewayCode(responseCode);
             System.out.println("after updating transactionEvent...."+gson.toJson(transaction));
             System.out.println("transactionEvent updated");
            
            obj = new JsonObject();
            obj.addProperty("code", "S4");
            obj.addProperty("message", "transaction updated");
            session.getTransaction().commit();
             System.out.println("after commiting...."+gson.toJson(transaction));
             System.out.println("transaction commited");
             
              //multithread needs to be used for sending to thread            
              es.submit(()->{
                    util.sendToSettlement(transaction);             
              });
             
            return obj;
      }catch(Exception e){
          obj = new JsonObject();
          obj.addProperty("code", "S3");
          obj.addProperty("message", "operation failed");
          System.out.println("error side...."+e.getMessage());
          return obj;
      }finally{
        if (session!=null) {
            if (session.isOpen()) {
                session.flush();
                    session.close();
                }
           }
        
      }
            
    }
      
      
    
      
    
      
//      
//      public String getTransactionByStatus(String status,String start, String pagesize){
//      session.getTransaction().begin();
//      try{
//          // get the count
//      query = session.createQuery("select count(transaction) from TransactionDetails transaction where transaction.status=:status");
//      query.setParameter("status", status);
//      Long count = (Long)query.uniqueResult();
//      
//      // get the transactions
//      query =  session.createQuery("select  transaction  from TransactionDetails  transaction where  transaction.status=:status");
//      query.setParameter("status", status);
//      query.setMaxResults(Integer.parseInt(pagesize));
//      query.setFirstResult(Integer.parseInt(start)-1);
//      List <TransactionDetails> transactionlist = query.list();
//      obj = new JsonObject();
//      obj.addProperty("code", "S5");
//      obj.addProperty("message", "get transaction by status successful");
//       obj.addProperty("total", count);
//      obj.add("transactions", new JsonParser().parse(gson.toJson(transactionlist)).getAsJsonArray());
//      session.getTransaction().commit();
//      session.close();
//      return obj.toString();
//      }catch(Exception e){
//          obj = new JsonObject();
//          obj.addProperty("code", "S3");
//          obj.addProperty("message", "operation failed");
//          System.out.println("...."+e.getMessage());
//          return obj.toString();
//      }finally{
//          if (session != null) {
//             session.close();
//          }
//      }
//            
//    }
//      
//      
  
                            // old one 
      
      
      
    public com.entities.Transactions getTransactionDetails(String reference){
      com.entities.Transactions transactions=null;
      try{
          session= HibernateUtil.getSessionFactory().openSession();
          session.beginTransaction();
          query =  session.createQuery("from Transactions  transactions where transactions.reference=:reference");
          query.setParameter("reference", reference);
          transactions =(com.entities.Transactions) query.uniqueResult();
          if(transactions==null){
            return null;
          }
            System.out.println("showing obj..."+gson.toJson(transactions));
            session.getTransaction().commit();
            return transactions;
      }catch(Exception e){
          System.out.println("cause...."+e.getMessage());
          return null;
      }finally{
         if (session != null) {
            session.close();
         }
      }   
     // return transactions;
      
    }
    
   
      public JsonObject updateTransaction(String reference, String linkingref,String responseCode,String responseMessage){
      try{
          System.out.println(reference+"...."+linkingref+"....."+responseCode+"..."+responseMessage);
            session= HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            query =  session.createQuery("from Transactions  transactions where transactions.reference=:reference");
            query.setParameter("reference", reference);
            com.entities.Transactions transaction =(com.entities.Transactions) query.uniqueResult();
            System.out.println("...."+gson.toJson(transaction));
            // Log transaction Event
             transaction.setLinkingref(linkingref);
             transaction.setGatewayMessage(responseMessage);
             transaction.setGatewayCode(responseCode);
             System.out.println("when updated...."+gson.toJson(transaction));
             System.out.println("transaction updated");
            
            obj = new JsonObject();
            obj.addProperty("code", "S4");
            obj.addProperty("message", "transaction updated");
            session.getTransaction().commit();
             System.out.println("after commiting...."+gson.toJson(transaction));
             System.out.println("transaction commited");
             
              //multithread needs to be used for sending to thread
//              and this is not suposed to be here
//              ExecutorService es = Executors.newFixedThreadPool(1);              
//              es.submit(()->{
//                    util.sendToSettlementV2(transaction);             
//              });
             
            return obj;
      }catch(Exception e){
          obj = new JsonObject();
          obj.addProperty("code", "S3");
          obj.addProperty("message", "operation failed");
          System.out.println("error side...."+e.getMessage());
          return obj;
      }finally{
        if (session != null) {
             session.close();
           }
        
      }
            
    }
      
      
      
      
   public JsonObject UpdateAndSendToSettlement(String reference, String linkingref,String responseCode,String responseMessage){
      try{
          System.out.println(reference+"...."+linkingref+"....."+responseCode+"..."+responseMessage);
            session= HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            query =  session.createQuery("from Transactions  transactions where transactions.reference=:reference");
            query.setParameter("reference", reference);
            com.entities.Transactions transaction =(com.entities.Transactions) query.uniqueResult();
            System.out.println("...."+gson.toJson(transaction));
            // Log transaction Event
             transaction.setLinkingref(linkingref);
             transaction.setGatewayMessage(responseMessage);
             transaction.setGatewayCode(responseCode);
             System.out.println("when updated...."+gson.toJson(transaction));
             System.out.println("transaction updated");
            
            obj = new JsonObject();
            obj.addProperty("code", "S4");
            obj.addProperty("message", "transaction updated");
            session.getTransaction().commit();
             System.out.println("after commiting...."+gson.toJson(transaction));
             System.out.println("transaction commited");
             
              //multithread needs to be used for sending to thread            
              es.submit(()->{
                    util.sendToSettlementV2(transaction);             
              });
             
            return obj;
      }catch(Exception e){
          obj = new JsonObject();
          obj.addProperty("code", "S3");
          obj.addProperty("message", "operation failed");
          System.out.println("error side...."+e.getMessage());
          return obj;
      }finally{
        if (session != null) {
             session.close();
           }
        
      }
            
    }
      
      
     // Transaction transaction;
    public String updateTransactionEvent(String gatewayref,String responseCode,String responseMessage,String trxn_ref){
      try{
            session= HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            query =  session.createQuery("from Transaction  transaction where transaction.ref=:trxn_ref");
            query.setParameter("trxn_ref", trxn_ref);
            com.entities.Transaction transaction =(com.entities.Transaction) query.uniqueResult();
            
            // Update transaction Event
              transaction.getUserinfo().getTransactionInfo().getTransactionEvent().setGatewayCode(responseCode);
              transaction.getUserinfo().getTransactionInfo().getTransactionEvent().setGatewayMessage(responseMessage);
           
              obj = new JsonObject();
              obj.addProperty("code", "S4");
              obj.addProperty("message", "transactionEvent updated");
              session.getTransaction().commit();

//            //multithread needs to be used for sending to thread
              ExecutorService es = Executors.newFixedThreadPool(1);              
              es.submit(()->{
                    util.sendToSettlement(transaction);             
              });
 
              
            return obj.toString();
      }catch(Exception e){
          obj = new JsonObject();
          obj.addProperty("code", "S3");
          obj.addProperty("message", "operation failed");
          System.out.println("...."+e.getMessage());
          return obj.toString();
      }finally{
        if (session != null) {
             session.close();
           }
        
      }
            
    }
      
      
      
      public String getTransactionStatus(String linkingref){
      try{
            session= HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            query =  session.createQuery("from Transactions  transactions where transactions.linkingref=:linkingref");
            query.setParameter("linkingref", linkingref);
            com.entities.Transactions transactions =(com.entities.Transactions) query.uniqueResult();
            obj = new JsonObject();
            
            obj.addProperty("customerName", transactions.getFullname());
            obj.addProperty("customerMobile", transactions.getMobile());
            obj.addProperty("customerEmail", transactions.getEmail());
            obj.addProperty("amount", transactions.getAmount());
            obj.addProperty("code", transactions.getGatewayCode());
            if(transactions.getGatewayMessage().equals("APPROVED")){
                 obj.addProperty("message", "Successful");
            }else{
                obj.addProperty("message", transactions.getGatewayMessage());
            }
            obj2 = new JsonObject();
            obj2.addProperty("linkingreference", transactions.getLinkingref());
            obj2.addProperty("callbackurl", transactions.getCallbackurl());
            obj.add("transaction", obj2);
            
            System.out.println(obj.toString());
            session.getTransaction().commit();
            return obj.toString();
      }catch(Exception e){
          obj = new JsonObject();
          obj.addProperty("code", "S23");
          obj.addProperty("message", "No transaction found");
          System.out.println("...."+e.getMessage());
          return obj.toString();
      }finally{
         if (session != null) {
             session.close();
          }
      }
            
    }
      
      public String updateEvent(String ref, String event){  
          try{
            session= HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            query =  session.createQuery("from Event event where event.ref=:ref");
            query.setParameter("ref", ref);
            Event eventObj =(Event) query.uniqueResult();
            eventObj.setEvents(eventObj.getEvents().concat(", "+event));
              System.out.println("...."+gson.toJson(eventObj));
            obj = new JsonObject();
            obj.addProperty("code", "00");
            obj.addProperty("message", "update done");
            
            session.getTransaction().commit();
            
            return obj.toString();
          }catch(Exception e){
           obj = new JsonObject();
          obj.addProperty("code", "S23");
          obj.addProperty("message", "No transaction found");
          System.out.println("...."+e.getMessage());
          return obj.toString();
      }finally{
         if (session != null) {
             session.close();
          }
      }
    }
      
      public void getAllTransactions(){
      
            session= HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            query = session.createQuery("from Transaction");
            List list = query.list();
          
      }
      
    public static void main(String[] args) {
       // System.out.println(new Dao().getTransactionStatus("F086624541561296348775"));
      //  System.out.println(new Dao().updateTransaction("CS_001","null","S7","Generic Error Occurred"));
      //  System.out.println(new Dao().getTransactionDetails("CS_001"));
      //  System.out.println(new Gson().toJson(new Dao().ngetTransactionDetails("sp2")));
    }
      
}
