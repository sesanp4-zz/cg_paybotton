/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.services;

import com.payloads.AccountPayload;
import com.payloads.CardPayload;
import com.payloads.WalletPayload;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

/**
 * REST Web Service
 *
 * @author centricgateway
 */
@Path("test/v1")
public class TestService {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of TestService
     */
    public TestService() {
    }

    /**
     * Retrieves representation of an instance of com.services.TestService
     * @return an instance of java.lang.String
     */
       @POST
       @Path("test/acct")
       @Consumes(MediaType.APPLICATION_JSON)
       @Produces(MediaType.APPLICATION_JSON)
       public Response test(AccountPayload payload){      
         return Response.status(Status.OK).entity(payload).build();
         
       }
       
       
       @POST
       @Path("test/card")
       @Consumes(MediaType.APPLICATION_JSON)
       @Produces(MediaType.APPLICATION_JSON)
       public Response test(CardPayload payload){  
         return Response.status(Status.OK).entity(payload).build();
         
       }
       
       
       @POST
       @Path("test/wallet")
       @Consumes(MediaType.APPLICATION_JSON)
       @Produces(MediaType.APPLICATION_JSON)
       public Response test(WalletPayload payload){      
         return Response.status(Status.OK).entity(payload).build();
         
       }
    
}
