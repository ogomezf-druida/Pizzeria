package com.example;

import java.util.Set;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import org.reflections.Reflections;

import com.example.segregation.Add;
import com.example.segregation.Get;
import com.example.segregation.Remove;


import jakarta.persistence.Entity;

public class Configuration {
    private static Class<?>[] getAnottationClass() {
        Reflections reflections = new Reflections("com.example");
        Set<Class<?>> importantClasses = reflections.getTypesAnnotatedWith(Entity.class);
        Class<?>[] clazz = new Class<?>[importantClasses.size()];
        return importantClasses.toArray(clazz); 
    }

    private static SessionFactory createSesionFactory(){
         final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
          .build();
        return new MetadataSources(registry)
          .addAnnotatedClasses(getAnottationClass())    
          .buildMetadata()
          .buildSessionFactory();
    }
    public static Session creatSession(){
        var session = createSesionFactory().openSession();                
        return session;
    }
    public static void closeSession(Session session){
        session.clear();
        session.close();
    }
    public static void UOW(Session session){
        Transaction tx = session.beginTransaction();
        try{
            tx.commit();
        }
        catch(Exception ex){
            tx.rollback();            
        }
        finally{
            closeSession(session);
        }
    }
    
    public static <T,ID> Get<T,ID> createGetRepository(Session session, Class<T> entityType){
        return new Get<T,ID>() {
            @Override
            public T get(ID id) {                
                var entity =  session.get(entityType, id);
                if (entity==null){
                    throw new RuntimeException();
                }
                return entity;
            }            
        };
    }
    public static <T> Add<T> createAddRepository(Session session){
        return new Add<T>() {
            @Override
            public void add(T entity) {
                session.persist(entity);
            }                                  
        };
    }    
    public static <T,ID> Remove<T,ID> createRemoveRepository(Session session, Class<T> entityType){
        var repositoryGet =Configuration.<T,ID>createGetRepository(session, entityType);
        return new Remove<T,ID>() {
            @Override
            public T get(ID id) {
                return repositoryGet.get(id);
            }
            @Override
            public void remove(T entity) {
                session.remove(entity);
            }            
        };
    }
    
}
