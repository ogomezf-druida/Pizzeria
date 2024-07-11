package com.example.pizza;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;


@Entity
@Table(name="Pizzas")
public class Pizza extends EntityBase{
 
    private static final double PROFIT = 1.2D;
    private String name;   
    private String description;
    private String url;
    @ManyToMany
    private Set<Ingredient> ingredients;
 
    protected Pizza(){
        super(null);
    }
    protected Pizza(UUID id, String name, String description, String url, Set<Ingredient> ingredients) {
        super(id);
        this.name = name;        
        this.description = description;
        this.url = url;        
        this.ingredients = ingredients;
    }
    public void update(String name, String description, String url){
        this.name = name;
        this.description = description;
        this.url = url;
    }
    public String getName(){
        return name;
    }
    public Double getPrice(){
        double price = 0D;
        for (Ingredient ingredient : ingredients) {
            price +=ingredient.getPrice();
        }
        return price*Pizza.PROFIT;
    }
    public String getDescription(){
        return description;
    }
    public String getUrl(){
        return url;
    }
    public List<Ingredient> getIngredients(){                      
        //ingredients.stream().toList() inmutabilidad
        return new ArrayList<>(ingredients);
    }
    public void addIngredient(Ingredient ingredient){        
        ingredients.add(ingredient);        
    }
    public void removeIngredient(Ingredient ingredient){
        ingredients.remove(ingredient);
    }
    
    public static Pizza create(UUID id, String name, String Description, String url){
        //Lanzar evento
        return new Pizza(id, name, Description, url, new HashSet<>());
    }

    public static PizzaBuilder builder(){
        return new Builder();
    } 
    private static class Builder implements PizzaBuilder{
        private UUID id;
        private String name;   
        private String description;
        private String url;        
        public Builder setName(String value){
            this.name = value;
            return this;
        }
        public Builder setDescription(String value){
            this.description = value;
            return this;
        }
        public Builder setUrl(String value){
            this.url = value;
            return this;
        }
        public Builder setId(UUID id){
            this.id = id;
            return this;
        }
        public Pizza build(){
            return new Pizza(id, name, description, url, new HashSet<>());
        }
    }
}

