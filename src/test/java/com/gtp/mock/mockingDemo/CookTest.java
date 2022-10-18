package com.gtp.mock.mockingDemo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(MockitoJUnitRunner.class)
class CookTest {

    private Cook cook;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @BeforeEach
    public void setup(){
        cook = new Cook(Collections.singletonList("Siomai Rice"));
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @Test
    public void should_return_orders_when_readOrder_given_queued_orders_are_cooked() throws InterruptedException {
        cook.cookAllOrders();
        assertEquals(Collections.singletonList("Siomai Rice"),cook.plateOrder());
    }

    @Test
    public void should_add_order_to_queue_when_readOrder_given_order_list(){
        cook.readOrder(Collections.singletonList("Siomai Rice"));

        List<String> outputStream = Arrays.asList(outputStreamCaptor.toString().split("\n"));
        assertEquals("Order has been added to queue", outputStream.get(outputStream.size()-1).trim());
    }

    @Test
    public void should_return_single_order_whenCookSingleOrder() throws InterruptedException {
        List<String> orders = Collections.singletonList("Siomai Rice");
        cook.cookSingleOrder(orders);

        List<String> outputStream = Arrays.asList(outputStreamCaptor.toString().split("\n"));
        assertEquals("Siomai Rice is/are cooked and ready", outputStream.get(outputStream.size()-1).trim());
    }

    @Test
    public void shouldCookOrder_given_order_string() throws InterruptedException {
        cook.cookOrder("Lamb Rice");

        List<String> outputStream = Arrays.asList(outputStreamCaptor.toString().split("\n"));
        assertEquals("Cooking Lamb Rice", outputStream.get(outputStream.size()-1).trim());

    }

    @Test
    public void should_return_null_when_plateOrder_given_empty_completedOrders(){
        assertNull(cook.plateOrder());
    }

    @Test
    public void shouldReturn_false_when_isBusy_given_empty_queuedOrders() throws InterruptedException {
        cook.cookAllOrders();
        assertFalse(cook.isBusy());
    }







}