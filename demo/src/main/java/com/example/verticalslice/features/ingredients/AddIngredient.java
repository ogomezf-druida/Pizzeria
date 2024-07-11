package com.example.verticalslice.features.ingredients;

import java.util.UUID;

import com.example.pizza.Ingredient;
import com.example.segregation.Add;

public class AddIngredient {

    public record Request(String name, double price) {
    }    
    public record Response(UUID id, String name, double price) {
    }   
    
    private final UseCase useCase;
    protected AddIngredient(final UseCase useCase){
        this.useCase = useCase;
    }
    public Response add(Request req){
        return useCase.add(req);
    }

    private interface UseCase {        
        Response add(Request req);
    }

    private static class UseCaseImpl implements UseCase{

        private final Add<Ingredient> repositoy;
        public UseCaseImpl(Add<Ingredient> repositoy) {
            this.repositoy = repositoy;
        }
        @Override
        public Response add(Request req) {
            var ingredient = Ingredient.create(
                UUID.randomUUID(), 
                req.name(), 
                req.price());
            
                repositoy.add(ingredient);

            return new Response(
                ingredient.getId(), 
                ingredient.getName(), 
                ingredient.getPrice()
            );

        }

    }   
    public static AddIngredient build(Add<Ingredient> repository){                
        var useCase = new UseCaseImpl(repository);
        return new AddIngredient(useCase);
    }
    
}