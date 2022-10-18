package com.gtp.mock.mockingDemo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Collections;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
class WaiterTest {

    private Waiter waiter;
    private Cook mockedCook;
    @BeforeEach
    public void setup(){
        waiter = new Waiter(Collections.singletonList("Chicken"),null);
        mockedCook = Mockito.mock(Cook.class);
    }

    @Test
    public void takeOrder_should_return_orders(){
        assertThat(waiter.takeOrder("Chicken")).isEqualTo("Took order Chicken");
    }

    @Test
    public void repeatOrder_should_return_current_order(){
        assertThat(waiter.repeatOrder()).isEqualTo("Your current order/s :Chicken");
    }

    @Test
    public void relayOrder_should_return_read_one_order() throws InterruptedException {
        waiter = new Waiter(Collections.singletonList("Chicken"),mockedCook);
        waiter.relayOrder();

        Mockito.verify(mockedCook, Mockito.atLeastOnce()).readOrder(any());
    }

    @Test
    public void serveOrder_should_serve_one_order(){
        when(mockedCook.isBusy()).thenReturn(false);
        when(mockedCook.plateOrder()).thenReturn(Collections.singletonList("Pizza"));
        waiter = new Waiter(Collections.singletonList("Chicken"),mockedCook);
        String returnValue = waiter.serveOrder();

        verify(mockedCook, atLeastOnce()).isBusy();
        verify(mockedCook, atLeastOnce()).plateOrder();
        assertThat(returnValue).isEqualTo("Now serving:Pizza");
    }


    @Test
    public void serveOrder_should_not_serve_when_cook_isBusy(){
        when(mockedCook.isBusy()).thenReturn(true);
        when(mockedCook.plateOrder()).thenReturn(Collections.singletonList("Pizza"));
        waiter = new Waiter(Collections.singletonList("Chicken"),mockedCook);
        String returnValue = waiter.serveOrder();

        verify(mockedCook, atLeastOnce()).isBusy();
        assertThat(returnValue).isEqualTo("All orders are not ready yet. Please wait");
    }

}