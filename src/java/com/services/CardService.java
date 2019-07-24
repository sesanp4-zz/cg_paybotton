/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.services;


import com.card.operations.CardTransactionRequestProxy;
import com.card.operations.ValidateOTPRequest;
import com.entities.Event;
import com.entities.Transactions;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.payload.objects.EventUpdateObject;
import com.util.Dao;
import com.util.Utilities;
import java.io.IOException;
import java.net.URI;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.inject.Inject;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

/**
 * REST Web Service
 *
 * @author centricgateway
 */
@Path("card/v1")
public class CardService {

   
    @Context
    private UriInfo context;
     
     @Inject
     Dao dao;
     
     @Inject
     com.card.operations.CardAPI cardApi; 
     
     ExecutorService executorservice;
     
     JsonObject obj;
     CloseableHttpClient client = HttpClients.createDefault();
     HttpPost post;
     HttpGet get;
     CloseableHttpResponse response;
     Gson gson = new Gson();
    
   
    
    
    Utilities util = new Utilities();
    

    /**
     * Creates a new instance of CardService
     */
    public CardService() throws IOException {
        

        
    }

   
    
    @Path("init/transaction")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String ncardInitTransaction(CardTransactionRequestProxy transaction) throws IOException, Exception{
        
          String msg =  cardApi.ninitiate_Transaction(transaction);             
           return msg;   
    }
       
    
    @Path("validate/otp")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String ncardOTPResponse(ValidateOTPRequest request) throws IOException, Exception{                  
          String msg = cardApi.nvalidateOTP(request);
          return msg;        
    
    }
    
   
    @Path("transaction/update")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response nupdateTransaction(@QueryParam("linkingreference")String linkingref,@QueryParam("code")String code,@QueryParam("message")String message,@QueryParam("reference")String reference) throws IOException, Exception{
        if(cardApi.checkStatusFromThirdParty(linkingref, reference)){
             String url=cardApi.nupdateTransaction(reference,linkingref,code,message);
             System.out.println("the url..."+url);
             return Response.seeOther(new URI(url)).build();
        } else{       
        return Response.status(Response.Status.NOT_ACCEPTABLE).build();
        }
    }
    
    @GET
    @Path("get/transaction/status/{ref}")
    @Produces(MediaType.APPLICATION_JSON)   
    public String ngetTransactionStatus(@PathParam("ref")String ref){
      return dao.ngetTransactionStatus(ref);
    }
    
    @GET
    @Path("fetch/transaction/status/{linkingref}")
    @Produces(MediaType.APPLICATION_JSON)   
    public String fetchTransactionStatusFromCore(@PathParam("linkingref")String linkingref){
      return cardApi.fetchStatusFromCore(linkingref);
    }
    
    @POST
    @Path("refund/{reference}")
    @Produces(MediaType.APPLICATION_JSON)   
    public String refund(@PathParam("reference")String reference){
      return cardApi.initiateRefund(reference).toString();
    }
    
    @Path("poll/status")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String poolStatus(@QueryParam("linkingreference")String linkingref,@QueryParam("reference")String reference) throws IOException, Exception{
      return  cardApi.poolandupdatestatuscheck(linkingref, reference);
    }
    
    
    @POST
    @Path("add/event")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)   
    public String naddEvent(Event event){        
      return cardApi.naddEvent(event);
    }
    
    @POST
    @Path("update/event")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)   
    public String nUpdateEvent(EventUpdateObject update){
      return cardApi.nupdateEvent(update.getRef(), update.getEvent());
    }
    
    
    
    
    
    
    // old one
    
     
    @Path("oinit/transaction")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String cardInitTransaction(Transactions transaction) throws IOException, Exception{
          String msg =  cardApi.initi(transaction);             
           return msg;   
    }
       
    
    @Path("ovalidate/otp")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String cardOTPResponse(ValidateOTPRequest request) throws IOException, Exception{                  
          String msg = cardApi.validateOTP(request);
          return msg;        
    
    }
    
   
    @Path("otransaction/update")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateTransaction(@QueryParam("linkingreference")String linkingref,@QueryParam("code")String code,@QueryParam("message")String message,@QueryParam("reference")String reference) throws IOException, Exception{
        String url=cardApi.updateTransaction(reference,linkingref,code,message);
        return Response.seeOther(new URI(url)).build();
   
    }
    
    @GET
    @Path("oget/transaction/status/{ref}")
    @Produces(MediaType.APPLICATION_JSON)   
    public String getTransactionStatus(@PathParam("ref")String ref){
      return dao.getTransactionStatus(ref);
    }
    
    
    @POST
    @Path("oadd/event")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)   
    public String addEvent(Event event){        
      return cardApi.addEvent(event);
    }
    
    @POST
    @Path("oupdate/event")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)   
    public String UpdateEvent(EventUpdateObject update){
      return cardApi.updateEvent(update.getRef(), update.getEvent());
    }
    
    
   
    
    
}


