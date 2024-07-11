package com.example.segregation;

public class UserRepository implements Get<User,Integer> {

    @Override
    public User get(Integer id) {        
        throw new UnsupportedOperationException("Unimplemented method 'get'");
    }
    
}
