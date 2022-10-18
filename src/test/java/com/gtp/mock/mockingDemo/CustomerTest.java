package com.gtp.mock.mockingDemo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
class CustomerTest {

    private Customer customer;
    private Waiter waiter;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @BeforeEach
    public void setup(){
        waiter = Mockito.mock(Waiter.class);
        customer = new Customer(waiter);
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @Test
    public void should_take_correct_order_given_giveOrder() throws InterruptedException {
        customer.give("Shawarma Rice");


        Mockito.verify(waiter).takeOrder("Shawarma Rice");
    }
    @Test
    public void should_relay_order_when_confirm() throws InterruptedException {
        customer.confirm();


        Mockito.verify(waiter).relayOrder();
    }
    @Test
    public void should_pressureCook_when_followUp() throws InterruptedException {
        customer.followUp();

        List<String> outputStream = Arrays.asList(outputStreamCaptor.toString().split("\n"));

        Mockito.verify(waiter).pressureCook();
        assertEquals("Waiter has pressured the cook to finish order.",outputStream.get(outputStream.size()-1).trim());
    }
    @Test
    public void should_repeatOrder_and_output_message_when_eat_given_no_orders() throws InterruptedException {
        when(waiter.repeatOrder()).thenReturn("You haven't ordered anything yet.");

        assertFalse(customer.eat());
    }
    @Test
    public void should_serveOrder_and_output_message_when_eat_given_orders() throws InterruptedException {
        customer.give("Chicken");
        
        when(waiter.serveOrder()).thenReturn("Now serving:Chicken");        
        when(waiter.repeatOrder()).thenReturn("Chicken");

        assertTrue(customer.eat());
    }

}