/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.util;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 *
 * @author centricgateway
 */
@Provider
public class GenericExceptionMapper implements ExceptionMapper<GenericException>{

 

//    @Override
//    public Response toResponse(Throwable exception) {
//        ErrorResponse res = new ErrorResponse("S90", exception.getMessage());
//        return Response.ok().entity(res).build();
//    }

    @Override
    public Response toResponse(GenericException exception) {
       ErrorResponse res = new ErrorResponse("S90", exception.getMessage());
        return Response.ok().entity(res).build();
    }
    
}
