package io.rollout.example;

import org.junit.BeforeClass;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

/**
 * Only one of these will pass
 */
public class TestSimpleXKCDClient {

    private static SimpleXKCDClient simpleXKCDClient;

    @BeforeClass
    public static void setupTest() {
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
