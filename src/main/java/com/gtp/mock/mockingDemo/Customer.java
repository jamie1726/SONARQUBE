package com.gtp.mock.mockingDemo;

import java.io.ByteArrayOutputStream;
import java.util.List;

public class Customer {
    private Waiter waiter;


    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    public Customer(){
        this.waiter = new Waiter(null, new Cook());
    }
    public Customer(Waiter waiter){
        this.waiter = waiter;
    }

    public void give(String order) throws InterruptedException {
        waiter.takeOrder(order);
    }

    public void confirm() throws InterruptedException {
        waiter.relayOrder();
    }

    public void followUp(){
        waiter.pressureCook();
        System.out.println("Waiter has pressured the cook to finish order.");
    }

    public boolean eat(){
        if(waiter.repeatOrder().contains("You haven't ordered anything yet.")){
            return false;
        }
        else return waiter.serveOrder().contains("Now serving:");
    }
}
