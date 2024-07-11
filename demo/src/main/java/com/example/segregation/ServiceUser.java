package com.example.segregation;

public class ServiceUser {
    private final Get<User,Integer> repository;
    public ServiceUser(Get<User,Integer> repository){
        this.repository = repository;
        this.repository.get(null);
    }
}
