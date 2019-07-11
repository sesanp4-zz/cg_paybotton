/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.authentication;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.util.Utilities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

/**
 *
 * @author centricgateway
 */
@Provider
public class AuthenticationHandler implements ContainerRequestFilter{

       JsonObject obj = new JsonObject();
       Gson gson = new Gson();
       String[]token=null;
       Utilities util = new  Utilities();

    @Override
    public void filter(ContainerRequestContext requestContext) {
        

        System.out.println("======="+requestContext.getUriInfo().getPath());
        if(requestContext.getUriInfo().getPath().equals("/v1/auth")||requestContext.getUriInfo().getPath().equals("/v1/transaction/update")||requestContext.getUriInfo().getPath().startsWith("/v1/refund")){
            
      
        
        }else{
            
                 if(requestContext.getHeaders().containsKey("Authorization")&&requestContext.getHeaderString("Authorization").startsWith("Bearer")){
                
                String header=requestContext.getHeaderString("Authorization");
                  token =header.split(" ");
                  obj = util.validateToken(token[1]);
                  
                  if(!(obj.get("message").getAsString()).equals("Successful")){
                       
                      requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).entity(gson.toJson(obj)).build());
                  }
            
            }else{
                 obj = new JsonObject();
                 obj.addProperty("code", "S7");
                 obj.addProperty("message", "Please check the header format, atleast  (Authorization : Bearer {token}) should be provided");
                 requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).entity(gson.toJson(obj)).build());
            } 
        
        }
        
    }
    
    /*
    in handling the transaction switch he below is the algorighm
    
    1 admin will configure cardtype with its gateways for the merchant, e.g master card to two gateways
    2 when f.e send card type to me, i will fetch the gateways of the cardtype based on merchant configuration
    3 then, i will save the transaction and send to the first(primary gateway), if failed ?
    4 update the transaction table and the even  table also
    5 resend the transaction to the secondary gateway 
    6 update the tables 
    7 return response back to f.e
    */
}
