package io.rollout;

import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class TestSimpleRestClient {



    @Test
    public void givenNormalDay_DutyCalls() {

        SimpleRestClient simpleRestClient = new SimpleRestClient();
        Response response = simpleRestClient.getState();
        assertEquals("Duty Calls", response.getTitle());
    }

    @Test
    public void givenHolidays_CleverGirl() {

        SimpleRestClient simpleRestClient = new SimpleRestClient();
        Response response = simpleRestClient.getState();
        assertEquals("2008 Christmas Special", response.getTitle());
    }



}
