package com.example.segregation;

public interface Repository<T,ID> extends Add<T>,Update<T,ID>, Remove<T,ID> {
    
}
