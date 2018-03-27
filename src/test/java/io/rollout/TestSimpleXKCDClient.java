package io.rollout;

import org.junit.BeforeClass;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class TestSimpleXKCDClient {

    private static SimpleXKCDClient simpleXKCDClient;

    @BeforeClass
    public static void setupTest() throws Exception {
        simpleXKCDClient = new SimpleXKCDClient();


    }

    @Test
    public void givenRequest_ComicIsDutyCalls() {
        XKCDComic XKCDComic = simpleXKCDClient.getComic();
        assertEquals("Duty Calls", XKCDComic.getTitle());
    }

    @Test
    public void givenHolidays_ComicsIsXMasSpecial() {
        XKCDComic XKCDComic = simpleXKCDClient.getComic();
        assertEquals("2008 Christmas Special", XKCDComic.getTitle());
    }



}
