/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.services;

import com.google.gson.JsonObject;
import com.handlers.AccountHandler;
import com.model.TransactionInitiationPayloadProxy;
import com.model.Transactionvalidationproxy;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * REST Web Service
 *
 * @author centricgateway
 */
@Path("account/v1")
public class AccountService {

   
    
    @Context
    private UriInfo context;
    
    @Inject
    AccountHandler handler ;
    
    Logger mylog = Logger.getLogger("Service.class");
    JsonObject obj;

    /**
     * Creates a new instance of GenericResource
     */
    public AccountService() {
    }

    /**
     * Retrieves representation of an instance of com.sersvice.AccountService
     * @return an instance of java.lang.String
     */
    
    @GET
    @Path("banklist")
    @Produces(MediaType.APPLICATION_JSON)   
    public Response getBankList() {      
      return handler.getBankList();
      
    }
    
    
        @POST
        @Path("initiate/transaction")
        @Consumes(MediaType.APPLICATION_JSON)
        @Produces(MediaType.APPLICATION_JSON)
        public String initiate(TransactionInitiationPayloadProxy proxy){
          return handler.initiate(proxy);
        }
        
        @POST
        @Path("validate/transaction")
        @Consumes(MediaType.APPLICATION_JSON)
        @Produces(MediaType.APPLICATION_JSON)
        public String validate(Transactionvalidationproxy proxy){
          return handler.validate(proxy);
        }
    
    
    
}
