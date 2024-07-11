package com.example;


import java.util.UUID;



import com.example.pizza.Ingredient;
import com.example.pizza.Pizza;
import com.example.verticalslice.features.ingredients.AddIngredient;
import com.example.verticalslice.features.pizza.AddPizza;
import com.example.verticalslice.features.pizza.AddPizza.Request;





/**
 * Hello world!
 *
 */
public class App {

    public static void main(String[] args) {
        addIngredient();
        addPizza();
        //addIngredient();
        /*var session = Configuration.creatSession();          
        var repository = Configuration.<Events,Integer>createGetRepository(session, Events.class);
        repository.get(1);
        //setup();
        addPizza();
        addIngredient();

        CustomerRepository repositoy = new CustomerRepository();
        ServiceCustomerUpdate service = new ServiceCustomerUpdate(repositoy);
        service.update(1);        

        Consumer<Object> mock = (obj)->{};

        Pinguino pinguino = new Pinguino(5);
        Aguila aguila = new Aguila(20, 100);
        Writer.printAve(aguila, mock);
        Writer.printAve(pinguino,(obj)->{});
        //error de compilacion
        //Writer.printAvVoladora(pinguino);
        //Writer.printAvNoVoladora(aguila);
       Writer.printAvNoVoladora(pinguino,System.out::println);
       Writer.printAvVoladora(aguila,System.out::println);*/
    }    
    public static void addPizza(){ 

        
        var session = Configuration.creatSession();
        
        var repository = Configuration.<Pizza>createAddRepository(session);
        var repositoryIngredient = Configuration.<Ingredient,UUID>createGetRepository(session, Ingredient.class);
        var sql = "select i.id from Ingredient i";
        var query = session.createQuery(sql,UUID.class);                
        var ingredients = query.list();
        

        Request req = new Request(
            "carbonara", 
            "pizza buenisima", 
            "url", 
            ingredients);

        var response = AddPizza.build(repository,repositoryIngredient).add(req);
        System.out.println(response);
        Configuration.UOW(session);
    }
    public static void addIngredient(){
        var session = Configuration.creatSession();
        try{
            var request = new AddIngredient.Request("queso", 1D);            
            var repository = Configuration.<Ingredient>createAddRepository(session);
            var response = AddIngredient.build(repository).add(request);                
            Configuration.UOW(session);
            System.out.println(response);
        }
        catch(Exception ex){
            Configuration.closeSession(session);
        }
        
        
      }
   
}
