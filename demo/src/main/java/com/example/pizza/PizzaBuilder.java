package com.example.pizza;

import java.util.UUID;

public interface PizzaBuilder {
    public PizzaBuilder setId(UUID id);
    public PizzaBuilder setName(String id);
    public PizzaBuilder setDescription(String id);
    public PizzaBuilder setUrl(String id);
    public Pizza build();      
}
