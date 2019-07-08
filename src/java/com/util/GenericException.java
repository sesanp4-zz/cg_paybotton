/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.util;

import java.io.Serializable;

/**
 *
 * @author centricgateway
 */
public class GenericException extends Exception implements Serializable{
    
     private static final long serialVersionUID = 1169426381288170661L;
     
     public GenericException(){
      super();
     }
     
     public GenericException(String message){
      super(message);
     }
     
     public GenericException(String message, Exception e){
      super(message, e);
     }
     
     
     
}
