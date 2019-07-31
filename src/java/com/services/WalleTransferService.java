/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.services;

import com.handlers.WalletTransferHandler;
import com.transfer.WalletTransferTransacionRequestProxy;
import javax.inject.Inject;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

/**
 * REST Web Service
 *
 * @author centricgateway
 */
@Path("transfer/v1")
public class WalleTransferService {

    @Context
    private UriInfo context;
    
    @Inject
    WalletTransferHandler handler;

    /**
     * Creates a new instance of TransferService
     */
    public WalleTransferService() {
    }

    /**
     * Retrieves representation of an instance of com.services.TransferService
     * @return an instance of java.lang.String
     */
    
    @Path("initiate/wallet/transfer")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String initiateWalletTransfer(WalletTransferTransacionRequestProxy payload){
     return handler.initiateTransfer(payload);   
    }
    
    
    
}
