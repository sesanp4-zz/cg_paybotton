/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.util;

import com.acct.operations.AccountActions;
import com.card.operations.CardAPI;
import com.google.gson.Gson;
import com.validator.Validator;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;
import javax.xml.bind.DatatypeConverter;
import javax.xml.soap.SOAPException;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.http.ProtocolException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author centricgateway
 */
public class Test {
//    public static void main(String[] args) {
//        String username="QUCOONAPIUSER";
//        String password="bASMFLp7D9uEDb";
//        String toencode=username+":"+password;
//        String token =new String(Base64.getEncoder().encode(toencode.getBytes()));
//        String nonce="342353523355241";
//        String timestamp="20190417143312";
//        String apikey="T888MdaepNXyNpE5RMsE32VRcDsKrYmX";
//        String signaturecal=nonce+":"+timestamp+":"+username+":"+apikey;
//        String signature = DigestUtils.sha512Hex(signaturecal);
//        System.out.println(token);
//        System.out.println(signature);
//
////                DateFormat dateFormatter = new SimpleDateFormat("yyyyMMddHHmmss");
////                Date today = Calendar.getInstance().getTime();
////	        String created = dateFormatter.format(today);
////                System.out.println(created);
//        
//    }
    
    public static void main(String[] args) throws IOException, SOAPException, MalformedURLException, JSONException {
      //    System.out.println(new Dao().updateTransactionStatus("F479100871559924740237", "APPROVED", "00"));
          //new Validator().validateMerchant("000001", "NGN", "1.0"); 
         // System.out.println(new Gson().toJson(new Dao().getTransactionDetails("F810499671559568472426")));
     //    System.out.println("testing.....");
     //    System.out.println(new AccountActions().getBankList());
     
     //   new Test().check();
     
      //    System.out.println(new String(Base64.getDecoder().decode("c3RyYXRmb3JkMl8hNzA6ZHlhcF95dXd5IWh5ZDgw")));
      //  System.out.println(Base64.getEncoder().encodeToString("handset:1234".getBytes()));
      
      //new Dao().
      
    }
    
    public  void check() throws IOException{
     
         Properties  prop = new Properties();
         prop.load(new FileReader("properties/app.properties"));
         System.out.println(prop.getProperty("test"));
     
        
    }
   
}
