package com.example.verticalslice.features.pizza;

import java.util.List;
import java.util.UUID;

import com.example.pizza.Ingredient;
import com.example.pizza.Pizza;
import com.example.segregation.Add;
import com.example.segregation.Get;

public class AddPizza {
    // Request->Input
    public record Request(
            String name,
            String description,
            String url,
            List<UUID> ingredients) {
    }

    // Response->Output
    public record Response(
            UUID id,
            String name,
            String description,
            String url,
            Double price,
            List<IngredientResponse> ingredients) {
    }
    public record IngredientResponse(UUID id,String name){}

    private final UseCase useCase;

    //Controller
    protected AddPizza(final UseCase useCase) {
        this.useCase = useCase;
    }

    public Response add(Request req) {
        return useCase.add(req);
    }
    //EndController

    // UseCase
    private interface UseCase {

        Response add(Request req);
    }

    private static class UseCaseImpl implements UseCase {

        private final Add<Pizza> repository;
        private final Get<Ingredient,UUID> repositoryIngredient;
        public UseCaseImpl(
            final Add<Pizza> repository,
            final Get<Ingredient,UUID> repositoryIngredient
        ) {
            this.repository = repository;
            this.repositoryIngredient = repositoryIngredient;
        }

        @Override
        public Response add(Request req) {

            //Request->Entidad            
            var pizza = Pizza.create(
                    UUID.randomUUID(), req.name(),
                    req.description(), req.url());
            for (var ingedientId : req.ingredients()) {
                pizza.addIngredient(repositoryIngredient.get(ingedientId));
            }
            //persistencia
            repository.add(pizza);
            //Entidad->Response
            
            var ingrediensResponse = pizza.getIngredients().stream()
                .map(i->new IngredientResponse(i.getId(), i.getName()))
                .toList();

            return new Response(
                    pizza.getId(),
                    pizza.getName(),
                    pizza.getDescription(),
                    pizza.getUrl(),
                    pizza.getPrice(),
                    ingrediensResponse);
        }

    }
    //endUseCase    
    

    //IOC->D(solid)
    public static AddPizza build(Add<Pizza> repository, Get<Ingredient,UUID> repositoryIngredient) {        
        var useCase = new UseCaseImpl(repository, repositoryIngredient);
        return new AddPizza(useCase);
    }
}

// Feature:AddPizza->UseCase