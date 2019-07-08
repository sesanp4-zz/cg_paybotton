/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.util;

import com.entities.TransactionInfo;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Calendar;
import java.util.Properties;
import java.util.Scanner;
import java.util.Set;
import java.util.TimeZone;
import javax.validation.Constraint;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

/**
 *
 * @author centricgateway
 */
public class DaoTest {
    
    public static void main(String[] args) throws FileNotFoundException, IOException {
      //  Dao dao = new Dao();
      //  dao.fetchUser("09008088");
        //dao.updateTransactionCID("1c0c27a7ddab4ff0813fcd077bb59400", "ci1", "PIN");
        //dao.getTransactionDetails("3f317e33e82b43838f2314ae68152cee");
        
      //  String pan="1234509876234112";
        
        //get Calendar instance
    //Calendar now = Calendar.getInstance();
    
    //get current TimeZone using getTimeZone method of Calendar class
   // TimeZone timeZone = now.getTimeZone();
    
    //display current TimeZone using getDisplayName() method of TimeZone class
   // System.out.println("Current TimeZone is : " + timeZone.getDisplayName());
//    TransactionInfo transaction =new Dao().getTransactionDetails("F165058511557849661236");
//        System.out.println(new Gson().toJson(transaction));
//
//         ValidatorFactory vf = Validation.buildDefaultValidatorFactory();
//         Validator v = vf.getValidator();
//         com.util.User u = new User("","west");
//         Set<ConstraintViolation<User>> c= v.validate(u);
//         
//         //Show errors
//        if (c.size() > 0) {
//            for (ConstraintViolation<User> violation : c) {
//                System.out.println(violation.getMessage());
//            }
//        } else {
//            System.out.println("Valid Object");
//        }
        
    }
    
    
    
}
