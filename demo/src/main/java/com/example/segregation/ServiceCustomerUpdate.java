package com.example.segregation;

public class ServiceCustomerUpdate {
    private final Update<Customer,Integer> repository;
    public ServiceCustomerUpdate(Update<Customer,Integer> repository){
        this.repository = repository;
        this.repository.get(1);

    }
    public void update(Integer id){        
        //Customer customer = this.repository.get(id);        
        //this.repository.update(customer);
    }

}
