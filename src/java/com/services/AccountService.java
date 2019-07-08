/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.services;

import com.acct.operations.AccountActions;
import com.google.gson.JsonObject;
import com.payload.objects.CancelMandateObject;
import com.payload.objects.GenerateManadateTransactionOTPObject;
import com.payload.objects.GenerateOTPNoRegObject;
import com.payload.objects.MandateCreationObject;
import com.payload.objects.RequeryMandateObject;
import com.payload.objects.ValidateMandateCancelationObject;
import com.payload.objects.ValidateMandateCreationObject;
import com.payload.objects.ValidateMandateTransactionOTPObject;
import com.payload.objects.ValidateOTPNoRegObject;
import java.io.IOException;
import java.net.MalformedURLException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.core.MediaType;
import javax.xml.soap.SOAPException;
import org.json.JSONException;

/**
 * REST Web Service
 *
 * @author centricgateway
 */
@Path("acct")
public class AccountService {

    @Context
    private UriInfo context;
    
     JsonObject obj;
     
    AccountActions acct = new AccountActions();
    /**
     * Creates a new instance of AccountService
     */
    public AccountService() {
    }

    /**
     * Retrieves representation of an instance of com.service.AccountService
     * @return an instance of java.lang.String
     */
    
    @GET
    @Path("banklist")
    @Produces(MediaType.APPLICATION_JSON)   
    public String getBankList() throws SOAPException, IOException, MalformedURLException, JSONException, NoSuchAlgorithmException, KeyManagementException{      
      return acct.getBankList();
      
    }
    
    
    @POST
    @Path("create/mandate")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)   
    public String createMandate(MandateCreationObject payload) throws SOAPException, IOException, MalformedURLException, JSONException, NoSuchAlgorithmException, KeyManagementException{      
      return acct.createMandate(payload.getAcctnum(), payload.getAcctname(), payload.getBankcode());
      
    }
    
    @POST
    @Path("mandate/validate")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)   
    public String validateMandate(ValidateMandateCreationObject payload) throws SOAPException, IOException, MalformedURLException, JSONException, NoSuchAlgorithmException, KeyManagementException{      
      return acct.validateMandateCreation(payload.getMandatecode(), payload.getBankcode(), payload.getOtp(), payload.getAmount());
      
    }
    
    @POST
    @Path("cancel/mandate")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)   
    public String cancelMandate(CancelMandateObject payload) throws SOAPException, IOException, MalformedURLException, JSONException, NoSuchAlgorithmException, KeyManagementException{      
      return acct.cancelMandate(payload.getMandatecode(), payload.getBankcode());
      
    }
    
    @POST
    @Path("cancel/mandate/validate")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)   
    public String cancelMandateValidate(ValidateMandateCancelationObject payload) throws SOAPException, IOException, MalformedURLException, JSONException, NoSuchAlgorithmException, KeyManagementException{      
      return acct.validateMandateCancelation(payload.getMandatecode(), payload.getBankcode(), payload.getOtp(), payload.getAmount());
      
    }
    
    
     @POST
    @Path("generate/opt/mandate/trans")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)   
    public String generateManadateTransactionOTP(GenerateManadateTransactionOTPObject payload) throws SOAPException, IOException, MalformedURLException, JSONException, NoSuchAlgorithmException, KeyManagementException{      
      return acct.generateManadateTransactionOTP(payload.getMandatecode(), payload.getBankcode(), payload.getAmount());
      
    }
    
    
    @POST
    @Path("mandate/trans/validate")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)   
    public String validateMandateTransactionOTP(ValidateMandateTransactionOTPObject payload) throws SOAPException, IOException, MalformedURLException, JSONException, NoSuchAlgorithmException, KeyManagementException{      
      return acct.validateMandateTransactionOTP(payload.getMandatecode(), payload.getBankcode(), payload.getOtp(), payload.getAmount());
      
    }
    
    @POST
    @Path("generate/otp/trans")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)   
    public String generateOTPNoReg(GenerateOTPNoRegObject payload) throws SOAPException, IOException, MalformedURLException, JSONException, NoSuchAlgorithmException, KeyManagementException{      
      return acct.generateOTPNoReg(payload.getAcctnum(), payload.getAcctname(), payload.getBankcode(), payload.getAmount());
      
    }
    
    
    @POST
    @Path("transaction/validate")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)   
    public String validateOTPNoReg(ValidateOTPNoRegObject payload) throws SOAPException, IOException, MalformedURLException, JSONException, NoSuchAlgorithmException, KeyManagementException{      
      return acct.validateOTPNoReg(payload.getAcctnum(), payload.getAcctname(), payload.getMandatecode(), payload.getBankcode(), payload.getOtp(), payload.getAmount());
      
    }
    
    @POST
    @Path("query/mandate")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)   
    public String requesryMandate(RequeryMandateObject payload) throws SOAPException, IOException, MalformedURLException, JSONException, NoSuchAlgorithmException, KeyManagementException{      
      return acct.requeryMandate(payload.getMandatecode());
      
    }
    
    
    
}
