/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.services;

import com.google.gson.JsonObject;
import com.handlers.PaymentLinkHandler;
import javax.inject.Inject;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;

/**
 * REST Web Service
 *
 * @author centricgateway
 */
@Path("PaymentLink/v1")
public class PaymentLinkService {
    
     JsonObject obj;

    @Context
    private UriInfo context;
    
    @Inject
    PaymentLinkHandler handler;
    /**
     * Creates a new instance of PaymentLinkService
     */
    public PaymentLinkService() {
    }

    
    @Path("get/link/{key}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String generateLink(@PathParam("key")String key) {
       return handler.generateLink(key);
    }

   
}
