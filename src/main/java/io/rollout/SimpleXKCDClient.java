package io.rollout;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.rollout.rox.server.Rox;


import java.io.IOException;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Optional;

public class SimpleXKCDClient {

    private Flags flags;

    private String REST_URI = "https://xkcd.com/386/info.0.json";

    OkHttpClient httpClient = new OkHttpClient();

    public static void main(String args[]) throws Exception {
        SimpleXKCDClient simpleXKCDClient = new SimpleXKCDClient();
        XKCDComic comic = simpleXKCDClient.getComic();
        System.err.println("Got " + comic.getTitle());
    }


    SimpleXKCDClient() throws Exception {

        // Uncomment to use properties file
        //InputStream props = new FileInputStream("src/test/resources/application.properties");
        //System.getProperties().load(props);
        //holidaySeason = Boolean.parseBoolean(Optional.of(System.getProperty("holidaySeason")).orElse("false"));

        // Create Rollout container
        flags = new Flags();

        // Register container with Rollout
        Rox.register("Flags", flags);

        // Initialize Rollout
        Rox.setup("5ab7cac73827af14484e440b");
        Thread.sleep(1000);

    }


    public XKCDComic getComic() {

        System.err.println("Holiday season is " + flags.getHolidaySeason().isEnabled());

        if (flags.getHolidaySeason().isEnabled()) {
            REST_URI = "https://xkcd.com/521/info.0.json";
        }

        Request request = new Request.Builder().url(REST_URI).build();

        try (Response response = httpClient.newCall(request).execute()) {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(response.body().bytes(), XKCDComic.class);
        } catch(IOException ioe) {
            return null;
        }

    }

}








