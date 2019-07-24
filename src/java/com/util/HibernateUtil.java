/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.util;


import java.io.File;
import java.util.logging.Logger;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

/**
 * Hibernate Utility class with a convenient method to get Session Factory
 * object.
 *
 * @author centricgateway
 */
public class HibernateUtil {

//     private static  SessionFactory sessionFactory;
    
          
     private static  SessionFactory sessionFactory;
     private static  StandardServiceRegistry   serviceRegistry;
     
    public static final SessionFactory getSessionFactory() {
        if ( sessionFactory == null ) {
         StandardServiceRegistry   serviceRegistry = new StandardServiceRegistryBuilder()
                    .configure()
                    .build();
            sessionFactory = new MetadataSources(serviceRegistry).buildMetadata().buildSessionFactory();
        }
        return sessionFactory;
    }
    
    
/*   private static  SessionFactory sessionFactory;
     private static  StandardServiceRegistry   serviceRegistry;

    static {
        try {
              serviceRegistry = new StandardServiceRegistryBuilder()
              .configure()
              .build();
              sessionFactory = new MetadataSources(serviceRegistry).buildMetadata().buildSessionFactory();

        } catch (Throwable ex) {
            System.out.println("cause...."+ex.getMessage());
            throw new ExceptionInInitializerError(ex);
        }
    }
    
    
    
    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
   */
    
}





//public class HibernateUtil {
//
////     private static  SessionFactory sessionFactory;
////    
////    public static final SessionFactory getSessionFactory() {
////        if ( sessionFactory == null ) {
////         StandardServiceRegistry   serviceRegistry = new StandardServiceRegistryBuilder()
////                    .configure()
////                    .build();
////            sessionFactory = new MetadataSources(serviceRegistry).buildMetadata().buildSessionFactory();
////        }
////        return sessionFactory;
////    }
//    
//    
//     private static  SessionFactory sessionFactory;
//       private static  StandardServiceRegistry   serviceRegistry;
//
//    static {
//        try {
//           if (sessionFactory == null ) {
//              serviceRegistry = new StandardServiceRegistryBuilder()
//              .configure()
//              .build();
//              sessionFactory = new MetadataSources(serviceRegistry).buildMetadata().buildSessionFactory();
//          }
//        } catch (Throwable ex) {
//            // Log the exception. 
//            if(serviceRegistry!=null){
//             StandardServiceRegistryBuilder.destroy(serviceRegistry);
//            }
//            System.out.println("cause...."+ex.getMessage());
//            throw new ExceptionInInitializerError(ex);
//        }
//    }
//    
//    public static SessionFactory getSessionFactory() {
//        return sessionFactory;
//    }
//   
//    
//}


