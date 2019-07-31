/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.services;

import java.util.Set;
import javax.ws.rs.core.Application;

/**
 *
 * @author centricgateway
 */
@javax.ws.rs.ApplicationPath("api")
public class ApplicationConfig extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<>();
        addRestResourceClasses(resources);
        return resources;
    }

    /**
     * Do not modify addRestResourceClasses() method.
     * It is automatically populated with
     * all resources defined in the project.
     * If required, comment out calling this method in getClasses().
     */
    private void addRestResourceClasses(Set<Class<?>> resources) {
        resources.add(com.authentication.AuthenticationHandler.class);
        resources.add(com.services.AccountService.class);
        resources.add(com.services.CardService.class);
        resources.add(com.services.MerchantService.class);
        resources.add(com.services.PaymentLinkService.class);
        resources.add(com.services.Service.class);
        resources.add(com.services.TestService.class);
        resources.add(com.services.ThirdPartyService.class);
        resources.add(com.services.WalleTransferService.class);
        resources.add(com.services.WebhookService.class);
        resources.add(com.util.Cors.class);
        resources.add(com.util.GenericExceptionMapper.class);
        resources.add(com.webhook.service.webhook.class);
    }
    
    
   


    
}
