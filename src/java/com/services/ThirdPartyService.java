/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.services;

import com.util.Utilities;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;

/**
 * REST Web Service
 *
 * @author centricgateway
 */
@Path("thirdparty")
public class ThirdPartyService {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of ThirdPartyService
     */
    public ThirdPartyService() {
    }

    /**
     * Retrieves representation of an instance of com.services.ThirdPartyService
     * @return an instance of java.lang.String
     */
    
    @Path("get/merchant/info/{key}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getMerchantInfo(@PathParam("key")String key) {
       return new Utilities().fectchMerchantInfoforTThirdParty(key).toString();
    }

    @Path("test")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String test() {
       return "Good";
    }
    
}
