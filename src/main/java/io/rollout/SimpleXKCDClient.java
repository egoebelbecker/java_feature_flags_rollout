package io.rollout;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.rollout.client.ConfigurationFetchedHandler;
import io.rollout.client.FetcherResults;
import io.rollout.rox.server.Rox;


import java.io.IOException;

import io.rollout.rox.server.RoxOptions;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Optional;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class SimpleXKCDClient {

    private Flags flags;

    private String REST_URI = "https://xkcd.com/386/info.0.json";

    OkHttpClient httpClient = new OkHttpClient();

    public static void main(String args[]) {
        SimpleXKCDClient simpleXKCDClient = new SimpleXKCDClient();
        XKCDComic comic = simpleXKCDClient.getComic();
        System.err.println("Got " + comic.getTitle());
    }


    SimpleXKCDClient() {

        // Uncomment to use properties file
        //InputStream props = new FileInputStream("src/test/resources/application.properties");
        //System.getProperties().load(props);
        //holidaySeason = Boolean.parseBoolean(Optional.of(System.getProperty("holidaySeason")).orElse("false"));

        initializeRox();
    }


    private void initializeRox() {

        CountDownLatch roxFirstFetch = new CountDownLatch(1);

        try {

            // Create Rollout container
            flags = new Flags();

            // Register container with Rollout
            Rox.register("Flags", flags);

            RoxOptions options = new RoxOptions.Builder()

                .withConfigurationFetchedHandler(new ConfigurationFetchedHandler() {

                    @Override
                    public void onConfigurationFetched(FetcherResults arg0) {
                        if (roxFirstFetch.getCount() > 0) {
                            roxFirstFetch.countDown();
                            System.err.println("Got Rollout configuration");
                        }

                    }

                }).build();

            // Initialize Rollout
            Rox.setup("5ab7cac73827af14484e440b", options);


            roxFirstFetch.await(10, TimeUnit.SECONDS);
        } catch (InterruptedException ie) {
            System.err.println("Interrupted waiting for rollout data.");
        }
    }

    public XKCDComic getComic() {

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








