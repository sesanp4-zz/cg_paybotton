
    
package com.services;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.util.Dao;
import com.util.Utilities;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

/**
 * REST Web Service
 *
 * @author centricgateway
 */
@Path("v1")
public class Service {

     @Context
    private UriInfo context;
     
     @Inject
     Dao dao ;       
  
       
    String billerid="NIBSS0000000130";
    String billername="UBACENTRIC";
    
      
 
    Utilities util = new Utilities();
    JsonObject obj;
    
     Logger mylog = Logger.getLogger("Service.class");
    
    /**
     * Creates a new instance of Service
     */
    public Service() {
         //new SSLManager().disableSSL();
    }

    /**
     * Retrieves representation of an instance of com.acct.operations.Service
     * @return an instance of java.lang.String
     */
    
    @POST
    @Path("auth")
    @Produces(MediaType.APPLICATION_JSON) 
    @Consumes(MediaType.APPLICATION_JSON)
    public String Authenticate(String req) {
            // new SSLManager().disableSSL();
           mylog.log(Level.INFO,"=================Request=====================");
           mylog.logp(Level.INFO, "Controller.class", "TokenAuthentication", req);
           obj = new JsonParser().parse(req).getAsJsonObject();
           String response=util.generateToken(obj.get("clientId").getAsString(),obj.get("clientSecret").getAsString());
           mylog.log(Level.INFO,"=================Response=====================");
           mylog.logp(Level.INFO, "Controller.class", "TokenAuthentication", response);
           return response;
    }
    
    
////    @GET
////    @Path("transaction/status/{status}")
////    @Produces(MediaType.APPLICATION_JSON)   
////    public String getTransactionByStatus(@PathParam("status") String status,@QueryParam("start")String start,@QueryParam("size")String size) {
////        System.out.println(size);
////        System.out.println(start);
////       return dao.getTransactionByStatus(status,start,size);
////    }
////    
////    @GET
////    @Path("all/transactions")
////    @Produces(MediaType.APPLICATION_JSON)   
////    public String getAllTransaction(@QueryParam("start")String start,@QueryParam("size")String size) {
////        System.out.println(size);
////        System.out.println(start);
////       return dao.getAllTransaction(start,size);
////    }
////    
////    @GET
////    @Path("transaction/summary")
////    @Produces(MediaType.APPLICATION_JSON)   
////    public String getTransactionSummary() {
////       return dao.getTransactionSummary();
////    }
////    
    
//    @POST
//    @Path("transaction/info/update")
//    @Produces(MediaType.APPLICATION_JSON)   
//    public String updatePersonal_Info(Personal_Info_Proxy proxy) {
//       return dao.updateUserInfo(proxy);
//    }
    
    
//    @GET
//    @Path("acct/banklist")
//    @Produces(MediaType.APPLICATION_JSON)   
//    public String getBankList() throws SOAPException, IOException, MalformedURLException, JSONException, NoSuchAlgorithmException, KeyManagementException{
//      // new SSLManager().disableSSL();
//      return acct_servie.getBankList(billerid);
//    }
    
//    @POST
//    @Path("test")
//    @Produces(MediaType.TEXT_PLAIN) 
//    @Consumes(MediaType.APPLICATION_JSON)  
//    public String test(@Valid com.util.User user){
//      return "as e dey hot";
//    } 
    
}