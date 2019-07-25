/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.util;


import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.Security;
import java.security.spec.RSAPublicKeySpec;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.Cipher;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

/**
 *
 * @author centricgateway
 */
public class Utilities {
    CloseableHttpClient client = HttpClients.createDefault();
    HttpPost post;
    HttpGet get;
    CloseableHttpResponse response;
    Gson gson ;
    
    
 //   Dao dao= new Dao();
    
    public static String getAuthData(String version, String pan, String pin, String expiryDate, String cvv2) throws Exception
    {
    	String authData = "";
    	String authDataCipher = version + "Z" + pan + "Z" + pin + "Z" + expiryDate + "Z" + cvv2;
    	//System.out.println("Auth Data Cipher: " + authDataCipher);
		String modulus = "9c7b3ba621a26c4b02f48cfc07ef6ee0aed8e12b4bd11c5cc0abf80d5206be69e1891e60fc88e2d565e2fabe4d0cf630e318a6c721c3ded718d0c530cdf050387ad0a30a336899bbda877d0ec7c7c3ffe693988bfae0ffbab71b25468c7814924f022cb5fda36e0d2c30a7161fa1c6fb5fbd7d05adbef7e68d48f8b6c5f511827c4b1c5ed15b6f20555affc4d0857ef7ab2b5c18ba22bea5d3a79bd1834badb5878d8c7a4b19da20c1f62340b1f7fbf01d2f2e97c9714a9df376ac0ea58072b2b77aeb7872b54a89667519de44d0fc73540beeaec4cb778a45eebfbefe2d817a8a8319b2bc6d9fa714f5289ec7c0dbc43496d71cf2a642cb679b0fc4072fd2cf";
		String publicExponent = "010001";
		PublicKey publicKey = getPublicKey(modulus, publicExponent);
		Cipher encryptCipher = Cipher.getInstance("RSA/ECB/PKCS1Padding", "BC");
		encryptCipher.init(Cipher.ENCRYPT_MODE, publicKey);
    	byte[] authDataBytes = encryptCipher.doFinal(authDataCipher.getBytes("UTF8"));
    	authData = new String(org.bouncycastle.util.encoders.Base64.encode(authDataBytes)).trim();
    	return authData;
    }

public static PublicKey getPublicKey(String modulus, String publicExponent)  throws Exception
    {
        Security.addProvider(new BouncyCastleProvider());
        RSAPublicKeySpec publicKeyspec = new RSAPublicKeySpec(new BigInteger(modulus, 16), new BigInteger(publicExponent, 16));
        KeyFactory factory = KeyFactory.getInstance("RSA"); //, "JHBCI");
        PublicKey publicKey = factory.generatePublic(publicKeyspec);
        return publicKey;
    }



     // Not implemented yet
    public static String getName() throws FileNotFoundException, IOException{   
          InputStream in = new FileInputStream("resource/Config.properties");
          Properties prop = new Properties();
          prop.load(in);
          return prop.getProperty("name");       
    }
    
    JsonObject obj = new JsonObject();
    
    public  String generateToken(String clientId,String ClientSecret){
        
       if(clientId.equals("pk_225b6971c8032eed3d4643836c26f669a014fc12") && ClientSecret.equals("sk_225b6971c8032eed3d4643836c26f669a014f2c2")){
        Long exp=System.currentTimeMillis()+3600000;
        String token = Jwts.builder().claim("clientId", clientId).claim("ClientSecret", ClientSecret)
       .setExpiration(new Date(exp)).setIssuer("CG")    
       .signWith(SignatureAlgorithm.HS512, "centric@_key#".getBytes()).compact();                
        obj = new JsonObject();
        obj.addProperty("access_token", token);
        obj.addProperty("issued at", LocalDateTime.now().toString().replace("T",""));
        obj.addProperty("expires_in", LocalDateTime.ofInstant(Instant.ofEpochMilli(exp), ZoneId.systemDefault()).toString());
        obj.addProperty("ResponseMessage", "Successful");
        obj.addProperty("ResponseCode", "00");
       }else{
           obj.addProperty("ResponseMessage", "Invalid API Credentails");
           obj.addProperty("ResponseCode", "S3");
                }
        return obj.toString();
    }
    
    public  JsonObject validateToken(String token){
               
        try{
        Jwts.parser().setSigningKey("centric@_key#".getBytes()).parseClaimsJws(token);
        obj.addProperty("message", "Successful");
        obj.addProperty("code", "00");
        }catch(ExpiredJwtException e){
            obj.addProperty("message", "expired token");
            obj.addProperty("code", "S4");
        }catch(SignatureException e){
            obj.addProperty("message", "Cannot validate signature");
            obj.addProperty("code", "S5");
            System.out.println(e.getMessage());
        }
        
        return obj;
       
    }
    
    
      public String getThirdPartyApi() throws UnsupportedEncodingException, IOException{
        post = new HttpPost(getAppProperties().getProperty("seerbit_auth_endpoint"));
        post.setHeader("Content-Type", "application/x-www-form-urlencoded");
        List<NameValuePair>list= new ArrayList<NameValuePair>();
        list.add(new BasicNameValuePair("grant_type", "client_credentials"));
        list.add(new BasicNameValuePair("client_id", "test"));
        list.add(new BasicNameValuePair("client_secret", "test"));
        post.setEntity(new UrlEncodedFormEntity(list));
        response=client.execute(post);
        String msg = EntityUtils.toString(response.getEntity());
        return msg;
     }
      
     public JsonObject fectchMerchantInfo(String mid){
         try{
             // https://staging.seerbitapigateway.com/cg-userms/api/v1/user/verify/
           //  https://staging.seerbitapigateway.com/merchants/api/v1/auth/verify/
           // obj ;
             get = new HttpGet(getAppProperties().getProperty("verify_merchant_info")+mid);
             response=client.execute(get);
             String msg = EntityUtils.toString(response.getEntity());
             obj = new JsonParser().parse(msg).getAsJsonObject();
             System.out.println("merchant info response...."+obj);
             JsonObject obj2 = new JsonObject();
               if((obj.get("responseCode").getAsString()).equals("00")){
                   obj2.addProperty("code", "00");
                   obj2.addProperty("currency", obj.get("payload").getAsJsonObject().get("default_currency").getAsString());
                   obj2.addProperty("max_amount", obj.get("payload").getAsJsonObject().get("max_amount").getAsString());
                   obj2.addProperty("min_amount", obj.get("payload").getAsJsonObject().get("min_amount").getAsString());
               }else{
                   System.out.println("somefin is wrong");
                   obj2.addProperty("code", "S18");
                   obj2.addProperty("message","Merchant does not exist");
               }  
               return obj2;
             
             
         }catch(Exception e){
           obj = new JsonObject();
           obj.addProperty("code", "S23");
           obj.addProperty("message", "operation failed");
           System.out.println("cause...."+e.getMessage());
           return obj;
         }
    } 
      
     
     public JsonObject fectchMerchantInfoforTThirdParty(String mid){
         try{

             get = new HttpGet(getAppProperties().getProperty("verify_merchant_info")+mid);
             response=client.execute(get);
             String msg = EntityUtils.toString(response.getEntity());
             obj = new JsonParser().parse(msg).getAsJsonObject();
             System.out.println("merchant info response...."+obj);               
             return obj;
             
         }catch(Exception e){
           obj = new JsonObject();
           obj.addProperty("code", "S23");
           obj.addProperty("message", "operation failed");
           System.out.println("cause...."+e.getMessage());
           return obj;
         }
    } 
      
     
     
     
    public void sendToSettlement(com.entities.Transaction transaction){
        try{            
            String datetime=transaction.getUserinfo().getTransactionInfo().getDatetime().replaceAll(":", "").replaceAll(" ", "");
           obj = new JsonObject();
           obj.addProperty("public_key", transaction.getUserinfo().getTransactionInfo().getPublic_key());
           obj.addProperty("transactionRef", transaction.getUserinfo().getTransactionInfo().getReference());
           obj.addProperty("linkingref", transaction.getUserinfo().getTransactionInfo().getTransactionEvent().getGatewayref());
           obj.addProperty("customerName", transaction.getUserinfo().getFullname());
           obj.addProperty("customerEmail", transaction.getUserinfo().getEmail());
           obj.addProperty("country", transaction.getUserinfo().getCountry());
           obj.addProperty("gatewayResponseMessage", transaction.getUserinfo().getTransactionInfo().getTransactionEvent().getGatewayMessage());
           obj.addProperty("gatewayResponseCode", transaction.getUserinfo().getTransactionInfo().getTransactionEvent().getGatewayCode());
           obj.addProperty("amount", transaction.getUserinfo().getTransactionInfo().getAmount());
           obj.addProperty("customerIP", transaction.getUserinfo().getTransactionInfo().getSourceIP());
           obj.addProperty("attempts", "to be determined");
           obj.addProperty("deviceType", transaction.getUserinfo().getTransactionInfo().getDeviceType());
           obj.addProperty("channel", "card");
           obj.addProperty("channelType", transaction.getUserinfo().getTransactionInfo().getChannelType());
           obj.addProperty("number", transaction.getUserinfo().getTransactionInfo().getNumber());
           obj.addProperty("customerPhone", transaction.getUserinfo().getMobile());
           obj.addProperty("currency", transaction.getUserinfo().getCurrency());
           obj.addProperty("transactionTime", datetime);
           
          
           System.out.println("the object sending to settlement");
           System.out.println(obj);
           
           String key=getAppProperties().getProperty("clientid")+":"+getAppProperties().getProperty("clientsecret");
           String apikey=new String(Base64.getEncoder().encode(key.getBytes()));
           
           post = new HttpPost(getAppProperties().getProperty("settlement_endpoint"));
           post.setHeader("Content-Type", "application/json");
           post.setHeader("Authorization", "Basic "+apikey);
           StringEntity ent = new StringEntity(obj.toString());
           post.setEntity(ent);
           response=client.execute(post);
           String msg = EntityUtils.toString(response.getEntity());
           obj = new JsonParser().parse(msg).getAsJsonObject();
           System.out.println(" ========== response from settlement ========="+obj);
           
           //constructing object to update transaction with settlement info in another thread
           
            DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy:MM:dd hh:mm:ss");
            String date_and_time_to_settlement=df.format(LocalDateTime.now());
           
           ExecutorService es = Executors.newFixedThreadPool(1);
             es.submit(()->{
                   new Dao().nupdateTransactionWithSettlementInfo(transaction.getUserinfo().getTransactionInfo().getReference(), obj.get("responseCode").getAsString(), obj.get("responseMessage").getAsString(), date_and_time_to_settlement);
              });
           
           // get the response from settlement and log the time sent and settlement response messages, code and the transaction ref
            
        }catch(Exception e){
             System.out.println(e.getMessage());
        }
    
    } 
    
    
    public void sendToSettlementV2(com.entities.Transactions transaction){
        try{            
            String datetime=transaction.getDatetime().replaceAll(":", "").replaceAll(" ", "");
           obj = new JsonObject();
           obj.addProperty("merchantId", transaction.getMid());
           obj.addProperty("transactionRef", transaction.getReference());
           obj.addProperty("linkingref", transaction.getLinkingref());
           obj.addProperty("customerName", transaction.getFullname());
           obj.addProperty("customerEmail", transaction.getEmail());
           obj.addProperty("country", transaction.getCountry());
           obj.addProperty("gatewayResponseMessage", transaction.getGatewayMessage());
           obj.addProperty("gatewayResponseCode", transaction.getGatewayCode());
           obj.addProperty("amount", transaction.getAmount());
           obj.addProperty("customerPhone", transaction.getMobile());
           obj.addProperty("currency", transaction.getCurrency());
           obj.addProperty("transactionTime", datetime);
           obj.addProperty("customerIP", "to be determined");
           obj.addProperty("attempts", "to be determined");
           obj.addProperty("deviceType", "to be determined");
           obj.addProperty("channel", "card");
           obj.addProperty("channelType", "to be determined");
           obj.addProperty("number", "to be determined");

           
           System.out.println("the object sending to settlement");
           System.out.println(obj);
            System.out.println("************************************");
           String key=getAppProperties().getProperty("clientid")+":"+getAppProperties().getProperty("clientsecret");
           String apikey=new String(Base64.getEncoder().encode(key.getBytes()));
           
           post = new HttpPost(getAppProperties().getProperty("settlement_endpoint"));
           post.setHeader("Content-Type", "application/json");
           post.setHeader("Authorization", "Basic "+apikey);
           StringEntity ent = new StringEntity(obj.toString());
           post.setEntity(ent);
           response=client.execute(post);
           String msg = EntityUtils.toString(response.getEntity());
           obj = new JsonParser().parse(msg).getAsJsonObject();
           // needs to update the transaction with settlement response with date
           System.out.println("response from settlement...."+obj);
            
        }catch(Exception e){
             System.out.println(e.getMessage());
        }
    
    } 
     
    public static String getprop(){
    
        Properties props = new Properties();
        try {
            String path = System.getProperty("user.dir");
            String f = new File(".").getCanonicalPath();
            System.out.println(f);
            System.out.println("...."+path);
            System.out.println(System.getProperty("user.home"));
            props.load(new FileReader("/app_properties/app_properties.properties"));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Utilities.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Utilities.class.getName()).log(Level.SEVERE, null, ex);
        }
        return props.getProperty("west");
    }
    
    public static void generatecode(){
       DateTimeFormatter df =  DateTimeFormatter.ofPattern("yymmddhhmmss");
       String bankcode="000014";
       int unique=Instant.now().getNano();
       String code=bankcode.concat(df.format(LocalDateTime.now())).concat(Integer.toString(unique));
        System.out.println(code);
        System.out.println(unique);
    }
    
    public Properties getAppProperties(){    
        Properties prop= null;
       try{
        prop  = new Properties();
        prop.load(this.getClass().getResourceAsStream("/app.properties"));
        return prop;
       }catch(Exception e){
           System.out.println("====== caused by ===== "+e.getMessage());
          return prop;
       }
    }
    
    public void check() throws IOException{
        System.out.println(getAppProperties().getProperty("settlement_endpoint"));
    }
    
    public static void main(String[] args) throws IOException {       
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy:MM:dd hh:mm:ss");
           String datetime=df.format(LocalDateTime.now());
           System.out.println(datetime);
   
    }
    
    
   
}
