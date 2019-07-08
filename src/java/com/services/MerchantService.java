/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.services;

import com.card.operations.CardTransactionRequestProxy;
import com.card.operations.ValidateOTPRequest;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.util.Dao;
import com.util.Utilities;
import java.io.IOException;
import java.net.URI;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.inject.Inject;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
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
@Path("merchant/v1")
public class MerchantService {

    @Context
    private UriInfo context;
    
    @Inject
     Dao dao;
     
     @Inject
     com.card.operations.CardAPI cardApi; 
     
    JsonObject obj;
    CloseableHttpClient client = HttpClients.createDefault();
    HttpPost post;
    HttpGet get;
    CloseableHttpResponse response;
    Gson gson = new Gson();
    
     ExecutorService executorservice;
    
   // Dao dao = new Dao();
    
    
    Utilities util = new Utilities();

    /**
     * Creates a new instance of MerchantService
     */
    public MerchantService() {
    }

    /**
     * Retrieves representation of an instance of com.services.MerchantService
     * @return an instance of java.lang.String
     */
    
     @Path("card/init/transaction")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String cardInitTransaction(CardTransactionRequestProxy transaction) throws IOException, Exception{
          String msg =  cardApi.ninitiate_Transaction(transaction);             
           return msg;   
    }
       
    
    @Path("card/validate/otp")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String cardOTPResponse(ValidateOTPRequest request) throws IOException, Exception{                  
          String msg = cardApi.nvalidateOTP(request);
          return msg;        
    
    }
    
   
    @Path("card/transaction/update")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateTransaction(@QueryParam("linkingreference")String linkingref,@QueryParam("code")String code,@QueryParam("message")String message,@QueryParam("reference")String reference) throws IOException, Exception{
        // need to do status check
        executorservice = Executors.newFixedThreadPool(1);
             executorservice.execute(()->{
              String status = new Dao().updateTransactionEvent(linkingref,code,message,reference);
              System.out.println(status);
            });
       // dao.updateTransactionEvent(linkingref, message, code);
        com.entities.Transaction transaction = dao.ngetTransactionDetails(reference);
        return Response.seeOther(new URI(transaction.getUserinfo().getTransactionInfo().getRedirecturl()+"?linkingreference="+linkingref+"&code="+code+"&message="+message+"&callback="+transaction.getUserinfo().getTransactionInfo().getCallbackurl())).build();
    
    }
    
    @GET
    @Path("card/get/transaction/status/{ref}")
    @Produces(MediaType.APPLICATION_JSON)   
    public String getTransactionStatus(@PathParam("ref")String ref){
      return dao.getTransactionStatus(ref);
    }
    
    
    
    
}
