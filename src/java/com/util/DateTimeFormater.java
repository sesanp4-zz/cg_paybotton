/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.util;

import com.google.gson.JsonObject;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author centricgateway
 */
public class DateTimeFormater {
    
       
   static JsonObject obj;
    
    public  static String getRealTimeFormat(){
      try{
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy:MM:dd hh:mm:ss");
        return  df.format(LocalDateTime.now());
      }catch(Exception e){
          obj= new JsonObject();
          obj.addProperty("code", "S7");
          obj.addProperty("message", "operation failed");         
          System.out.println("could not fomat time cause ========== "+e.getMessage());
        return "failed";
      }
    }
    
    
}
